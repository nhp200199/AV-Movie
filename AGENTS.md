# AI Agent Guidelines & Engineering Standards for AV-Movie

This document serves as the authoritative guide and rulebook for AI agents (including Antigravity, Claude, Cursor, and other coding assistants) when reading, refactoring, generating, or reviewing code in the **AV-Movie** codebase.

---

## 1. Project Overview & Tech Stack Baseline

- **Application Name:** AV Movie (`com.av.movie` / `com.av.avmovie`)
- **SDK Target:**
  - `compileSdk`: **34**
  - `targetSdk`: **34**
  - `minSdk`: **28**
  - `jvmTarget`: **1.8** (`JavaVersion.VERSION_1_8`)
- **Core Technologies:**
  - **Build & Dependency Catalog:** Gradle Version Catalog ([`gradle/libs.versions.toml`](file:///gradle/libs.versions.toml))
  - **UI Toolkit:** Jetpack Compose + Material 3 + Navigation Compose
  - **UI System:** Accompanist System UI Controller
  - **Dependency Injection:** Dagger Hilt
  - **Networking:** Retrofit + Gson converter
  - **Asynchronous / Reactive:** Kotlin Coroutines & StateFlow
  - **Testing:** JUnit 4, AndroidX JUnit, Espresso Core

> [!IMPORTANT]
> **Single Source of Truth for Dependencies & Versions:** 
> Do not define or hardcode library versions in documentation or `build.gradle` files. **Always refer directly to [`gradle/libs.versions.toml`](file:///gradle/libs.versions.toml)** for exact library aliases and versions. Do not introduce new external libraries without explicit user confirmation.

---

## 2. Layered Clean Architecture (Single Module)

The codebase is organized as a single `:app` module using package-by-layer Clean Architecture principles under `com.av.movie.*`:

```
app/src/main/java/com/av/movie/
├── presentation/   # UI Screens, Reusable Components, Navigation, ViewModels
├── domain/         # Pure Kotlin business rules (Models, Repository Interfaces, UseCases)
├── data/           # Data layer (Feature APIs, Repository Implementations, Data Sources, DTOs)
├── core/           # Infrastructure setup (Network client, DB setup, Dispatchers, Common Utils)
├── di/             # Dagger Hilt Dependency Injection Modules
└── AVMovieApplication.kt / MainActivity.kt  # App initialization and entry point
```

### Layer Boundaries & Responsibilities

#### 1. `domain` (Pure Kotlin / Business Core)
- **STRICT RESTRICTION:** Must remain pure Kotlin. Do not import Android framework packages (`android.*`, `androidx.*`) into this layer.
- Contains:
  - Domain models: `domain.model.*` (e.g., `Movie.kt`)
  - Repository interfaces: `domain.repository.*` (e.g., `MovieRepository.kt`)
  - UseCases: `domain.usecase.*`

#### 2. `data` (Data & Implementation)
- Implements repository interfaces defined in `domain`.
- Contains:
  - Feature-specific Retrofit API interfaces: `data.api.*` (e.g., `MoviePreviewApi.kt`)
  - Repository Implementations: `data.repository.*` (e.g., `MoviePreviewRepositoryImp.kt`)
  - Mappers: `data.mapper.*`
  - DTOs and Data models: `data.model.*`
- **Direct API & Mapping:** Repositories directly inject Retrofit API interfaces and Mappers, avoiding redundant DataSource abstraction layers.
- **Data Mapping Rule:** All network response DTOs must be mapped into clean domain models before emitting data back to the callers.

#### 3. `core` (Infrastructure Setup & Utilities)
- Contains foundational setup and infrastructure configurations:
  - Base network clients (e.g., `OkHttpClient`, generic Retrofit builder, network interceptors, authenticators).
  - Base database configuration (e.g., Room database builder, migrations).
  - Threading & Coroutine primitives (e.g., CoroutineDispatchers provider).
  - Common foundational utilities used across the application.
- **CRITICAL RESTRICTION:** `core` must ONLY contain infrastructure and foundational setup code. Feature-specific services, endpoints, or data access objects (such as `MovieApi` to fetch movie data, `MovieDao`, etc.) do **NOT** belong here; they strictly belong in the `data` layer.

#### 4. `presentation` & `di` (UI, State & Dependency Injection)
- Contains:
  - Presentation layer: `presentation.screen.*`, `presentation.navigation.*`, `presentation.components.*`
  - DI Modules: `di.*` (`NetworkModule`, `RepositoryModule`, `MapperModule`, `UseCaseModule`)
  - Application class (`AVMovieApplication.kt`) and root Activity (`MainActivity.kt`).

---

## 3. Presentation Layer & UI Architecture Rules

### 3.1. Pragmatic UseCase Policy
- **Do not create unnecessary UseCases for simple pass-through operations.**
- **When to create/use a UseCase in `domain`:**
  1. The business logic is complex (e.g., requires coordination/composition across multiple Repositories or non-trivial domain transformations).
  2. The exact same business logic is reused across multiple screens or ViewModels.
- **Direct Repository Access:** For straightforward operations (e.g., fetching a movie list, simple CRUD from a single repository), ViewModels are permitted and encouraged to directly inject and call the `Repository` interface from `domain`.

### 3.2. Reusable UI Components Placement
- **Shared Components:** Any Composable UI component designed or identified to be reusable across multiple screens must reside in:
  `com.av.movie.presentation.components/`
- **Screen-Specific Components:** Components unique to a single feature or screen stay inside that screen's package (e.g., `presentation.screen.home.HomeTopBar`, `presentation.screen.home.MovieList`).

---

## 4. MVVM Presentation Pattern & State Management

All screens must strictly adhere to the **MVVM (Model-View-ViewModel)** architectural pattern with **Unidirectional Data Flow (UDF)**.

### 4.1. ViewModel Standards
- Annotate with `@HiltViewModel` and use `@Inject constructor(...)`.
- State must be exposed as an immutable `StateFlow<T>` backed by a private `MutableStateFlow<T>`.
- Use `viewModelScope` for coroutine launches:
  ```kotlin
  @HiltViewModel
  class MovieDetailViewModel @Inject constructor(
      private val movieRepository: MovieRepository // or movieUseCase if complex/reused
  ) : ViewModel() {

      private val _movieDetail = MutableStateFlow<Movie?>(null)
      val movieDetail: StateFlow<Movie?> = _movieDetail.asStateFlow()

      fun getMovie(movieId: Int) {
          viewModelScope.launch {
              // Coroutine logic
          }
      }
  }
  ```

### 4.2. Jetpack Compose UI Standards
- **State Hoisting:** Make UI composables stateless by hoisting state up to the screen container or ViewModel.
- **Event Handling:** Expose callbacks (e.g., `onMovieClick: (Int) -> Unit`, `onBackClick: () -> Unit`) instead of passing ViewModels down into leaf composables.
- **Modifier Rule:** Composable components should accept a `modifier: Modifier = Modifier` parameter and apply it to their outermost layout element.
- **Lifecycle-aware State Collection:** Collect flows in Composables using lifecycle-aware collectors (e.g., `collectAsState()` or `collectAsStateWithLifecycle()`).

---

## 5. Result & Error Handling

- All operation results, status wrappers, and errors must utilize:
  `com.av.movie.data.model.ResultData`
- Standardize on wrapping asynchronous and network calls with `ResultData` (e.g., handling Success, Error, and Loading states).
- Never catch exceptions silently without logging or translating them into appropriate UI-friendly states.

---

## 6. Naming Conventions & Code Style

- **Screens:** `[Feature]Screen.kt` (e.g., `HomeScreen.kt`, `MovieDetailScreen.kt`)
- **ViewModels:** `[Feature]ViewModel.kt` (e.g., `HomeViewModel.kt`, `MovieDetailViewModel.kt`)
- **Shared Components:** `presentation/components/[ComponentName].kt`
- **Feature Sub-components:** `presentation/screen/[feature]/[Feature][Component].kt` (e.g., `HomeTopBar.kt`)
- **Navigation:** Under `presentation/navigation/` (e.g., `NavGraph.kt`, `Screen.kt`)
- **Repository Interface:** `[Entity]Repository.kt` (located in `domain.repository`)
- **Repository Implementation:** `[Entity]RepositoryImp.kt` (located in `data.repository`)

---

## 7. AI Agent Guardrails & Workflow Expectations

1. **Verify Before Modifying:** Always inspect existing interfaces, DTOs, and DI bindings in `domain` and `data` before introducing duplicate abstractions.
2. **Preserve Comments & Formatting:** Do not delete existing comments, TODOs, or license headers unrelated to the specific task.
3. **No Unrequested Dependencies:** Never modify `libs.versions.toml` or `build.gradle` to add dependencies unless specifically instructed.
4. **Compile-Check Safety:** Ensure all imports and package statements are accurate before concluding any task.
