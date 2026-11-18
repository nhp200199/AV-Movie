# Plan: Search Screen từ ExploreScreen (`SearchScreen`)

## 1. Goal & Approach
Xây dựng màn hình **Search (`SearchScreen`)** mở ra khi người dùng nhấn vào ô tìm kiếm trong `ExploreScreen.kt`, hỗ trợ đầy đủ 2 trạng thái tìm kiếm, lưu tối đa 5 từ khóa gần nhất qua `SharedPreferences`, debounce `500ms` / Enter search ngay lập tức, `ModalBottomSheet` chứa 5 section Filter & Sort tham chiếu từ TMDB `discover/movie` (dữ liệu lấy động từ kết quả API search), hàng filter chips có nút `X` xóa nhanh, và hỗ trợ Light/Dark theme theo `AVMovieTheme`.

### Approach Summary
- **Navigation & Theme (`A4`):** Đặt `@Serializable object Search` trong `AVMovieNavGraph.kt` (cùng cấp `Main` và `MovieDetail`) để tự động ẩn `BottomNavigation` của `MainScreen`, giữ nguyên back stack tab `Explore`, hỗ trợ Light/Dark theme theo `MaterialTheme.colorScheme` + `BrandPrimary` / `BrandGradient`, và thêm nút Back ở header ô tìm kiếm.
- **State 1 — Empty Input & Recent Searchs (`A2`):** Tạo `SearchHistoryRepository` (`domain`) + `SearchHistoryRepositoryImp` (`data`, lưu JSON bằng `kotlinx.serialization.json` vào `SharedPreferences`, không thêm dependency mới). Nếu danh sách lịch sử rỗng thì **không hiển thị** section `"Recent Searchs"`. Khi có dữ liệu, lưu tối đa 5 từ khóa mới nhất lên đầu khi người dùng nhấn **Enter / Search**, nhấn vào **một movie kết quả**, hoặc chọn lại từ khóa từ **Recent Searchs**.
- **State 2 — Search, Debounce & Not Found:** `SearchViewModel` debounce `500ms` khi người dùng đang nhập và hủy debounce để gọi ngay khi nhấn Enter hoặc chọn từ khóa lịch sử. Hiển thị lưới 2 cột (`MovieItem`), hoặc chỉ hiển thị text `"Not Found"` theo Figma `1-913` (không dùng ảnh 404).
- **Filter & Sort (`A1`, `A3`):**
  - **5 section trên Bottom Sheet (tham chiếu `GET /3/discover/movie`):**
    1. **Genre** (`with_genres`) — multi-select, lấy từ `genreIds` trong kết quả search và map sang tên thể loại qua `MovieRepository.getMovieGenres()`.
    2. **Language** (`with_original_language`) — single-select, lấy từ `originalLanguage` trong kết quả search (hiển thị tên ngôn ngữ dễ đọc qua `Locale`).
    3. **Release Year** (`primary_release_year`) — single-select, trích xuất năm từ `releaseDate` trong kết quả search (giảm dần).
    4. **Minimum Rating** (`vote_average.gte`) — single-select (`9.0+`, `8.0+`, `7.0+`, `6.0+`, `5.0+` lọc theo các ngưỡng thực sự tồn tại trong kết quả search).
    5. **Sort By** (`sort_by`) — single-select (`Default`, `Popularity`, `Latest Release`, `Top Rated`, `Most Voted`).
  - **Cơ chế Apply Filter & Sort (Local trên kết quả Search API):** Giữ nguyên API `GET /3/search/movie` (`MovieApi.searchMovie(query)`) hiện tại và **không dùng** endpoint `discover/movie`. `SearchViewModel` giữ kết quả gốc của `query` hiện tại (`rawSearchMovies`) và kết hợp với `activeFilter` thông qua `FilterAndSortSearchMoviesUseCase` để lọc và sắp xếp **ở local** ngay lập tức khi user bấm **Apply** hoặc bấm **`X`** xóa nhanh chip (không gọi lại network dư thừa cho cùng từ khóa; khi tìm từ khóa mới sẽ tự reset `activeFilter` về mặc định). `AvailableSearchFilters` cho 5 section luôn trích xuất từ `rawSearchMovies` nên giữ đầy đủ mọi lựa chọn khi mở lại Bottom Sheet.
  - **Icon Filter & Filter Chips (`A3`):** Icon Filter ở header **chỉ hiển thị khi kết quả API gốc có `> 2` phim**, và **vẫn giữ hiển thị khi đang áp dụng filter** kể cả lúc danh sách sau lọc chỉ còn 1 phim (hoặc <= 2 phim) để user chỉnh lại filter dễ dàng; dùng `AnimatedVisibility` trượt ngang ra/vào. Khi đang có filter active, hiển thị `LazyRow` các filter chip có icon `X` để bỏ nhanh từng filter và cập nhật lại danh sách kết quả ở local ngay lập tức. Khi Apply hoặc bỏ filter/sort, danh sách kết quả dùng `Crossfade` fade out list cũ / fade in list mới và reset scroll về vị trí đầu tiên.

---

## 2. Requirements (`R1..R8`)

| ID | Requirement | Source |
|---|---|---|
| **R1** | Nhấn vào ô tìm kiếm trong `ExploreScreen.kt` điều hướng sang `SearchScreen`; ẩn Bottom Bar và có nút Back quay lại `ExploreScreen`. | User request + `A4` |
| **R2** | **State 1 (ô nhập trống):** Nếu chưa có lịch sử tìm kiếm thì **không hiển thị** section `"Recent Searchs"`. Khi có từ khóa, hiển thị section `"Recent Searchs"` tối đa 5 từ khóa, mới nhất ở trên cùng; vượt quá 5 từ thì thay thế từ cũ nhất; trùng từ thì đưa lên đầu. Lưu bền vững vào `SharedPreferences` khi nhấn Enter, nhấn vào item phim kết quả, hoặc chọn từ khóa trong `"Recent Searchs"`. | User request + `A2` + Review note |
| **R3** | **State 2 (đang nhập):** Debounce `500ms` sau khi dừng gõ trước khi gọi API search; nhấn Enter (IME Search) gọi API ngay lập tức (hủy debounce chờ). | User request |
| **R4** | **State 2 UI:** Hiển thị lưới poster 2 cột (`MovieItem` + `RatingBadge` giống Figma `1-911`); nếu không có kết quả thì chỉ hiện text `"Not Found"` + mô tả như Figma `1-913` (không có ảnh 404). Nhấn vào một item chuyển sang `MovieDetail(movie.id)`. | User request + Figma `1-911`, `1-913` |
| **R5** | **Icon Filter:** Hiện khi kết quả tìm kiếm gốc `> 2` phim; nếu sau khi áp dụng filter danh sách còn ít phim hơn (kể cả chỉ còn 1 phim), vẫn giữ icon Filter hiển thị để user điều chỉnh tiếp, kèm animation trượt ra/vào theo chiều ngang mượt mà. | User request + `A3` + Review note + Spec update |
| **R6** | **Filter Bottom Sheet (5 sections):** Tham chiếu TMDB `discover/movie` gồm `Genre`, `Language`, `Release Year`, `Minimum Rating`, `Sort By`; dữ liệu các option được trích xuất động từ kết quả API search của `query`. Có nút `Reset` và `Apply`. | User request + `A1` + Figma `1-909` |
| **R7** | **Apply & Filter Chips:** Khi bấm `Apply`, áp dụng lọc & sắp xếp **local** trên danh sách kết quả search hiện tại (giữ nguyên API `search/movie`), cuộn danh sách về đầu trang kèm hiệu ứng fade in/out giữa list cũ và mới, và hiển thị các chip filter (Figma `1-907`) có icon `X` để xóa nhanh từng filter. | User request + `A3` + Figma `1-907` + Follow-up |
| **R8** | **Theme, Clean Architecture & Tests:** Hỗ trợ Light/Dark theo `AVMovieTheme` (`MaterialTheme.colorScheme` + `BrandGradient`), tuân thủ ranh giới `domain`/`data`/`presentation`/`di` trong `AGENTS.md`, không sửa `gradle/libs.versions.toml` hay `app/build.gradle`, và có unit tests cho mọi requirement logic trong `app/src/test/`. | `AGENTS.md` + `A4` + `planning/SKILL.md` |

---

## 3. Existing Code & Blast Radius

### Files to Create
- `app/src/main/java/com/av/movie/domain/model/SearchFilter.kt` — Pure Kotlin models: `SearchMovieFilter`, `SearchSortOption`, `AvailableSearchFilters`, `LanguageOption`.
- `app/src/main/java/com/av/movie/domain/repository/SearchHistoryRepository.kt` — Pure Kotlin repository interface quản lý lịch sử tìm kiếm.
- `app/src/main/java/com/av/movie/domain/usecase/ManageRecentSearchesUseCase.kt` — Pure Kotlin UseCase xử lý quy tắc tối đa 5 từ khóa, deduplicate, mới nhất lên đầu, trim & xóa item.
- `app/src/main/java/com/av/movie/domain/usecase/FilterAndSortSearchMoviesUseCase.kt` — Pure Kotlin UseCase trích xuất 5 section filter từ `List<Movie>` + lọc và sắp xếp kết quả theo `SearchMovieFilter`.
- `app/src/main/java/com/av/movie/data/repository/SearchHistoryRepositoryImp.kt` — Implementation dùng `SharedPreferences` + `kotlinx.serialization.json.Json` + `MutableStateFlow`.
- `app/src/main/java/com/av/movie/presentation/screen/search/SearchViewModel.kt` — `@HiltViewModel` quản lý `query`, debounce `500ms`, Enter search, cache `baseMoviesForQuery`, `SearchUiState`, `SearchMovieFilter`, `AvailableSearchFilters`, `recentSearches`.
- `app/src/main/java/com/av/movie/presentation/screen/search/SearchScreen.kt` — UI State 1 (`Recent Searchs`), State 2 (Grid 2 cột, Not Found text-only, Loading/Error), Search Header (Back + Input + conditional Filter icon), Active Filter Chips row với icon `X`, và Previews Light/Dark.
- `app/src/main/java/com/av/movie/presentation/screen/search/SearchFilterBottomSheet.kt` — `ModalBottomSheet` chứa 5 section (`Genre`, `Language`, `Release Year`, `Minimum Rating`, `Sort By`), `Reset` và `Apply`.
- **Unit Tests (JVM, JUnit 4 — không thêm dependency):**
  - `app/src/test/java/com/av/movie/domain/usecase/ManageRecentSearchesUseCaseTest.kt`
  - `app/src/test/java/com/av/movie/domain/usecase/FilterAndSortSearchMoviesUseCaseTest.kt`
  - `app/src/test/java/com/av/movie/presentation/screen/search/SearchFilterUiRuleTest.kt`

### Files to Modify
- `app/src/main/java/com/av/movie/di/RepositoryModule.kt` — Bind `SearchHistoryRepositoryImp` cho `SearchHistoryRepository`.
- `app/src/main/java/com/av/movie/presentation/navigation/Routes.kt` — Thêm `@Serializable object Search`.
- `app/src/main/java/com/av/movie/presentation/navigation/AVMovieNavGraph.kt` — Thêm `composable<Search>` và truyền callback `onNavigateToSearch` vào `MainScreen`.
- `app/src/main/java/com/av/movie/presentation/screen/main/MainScreen.kt` — Nhận `onNavigateToSearch: () -> Unit` và truyền vào `ExploreScreenVM`.
- `app/src/main/java/com/av/movie/presentation/screen/explore/ExploreScreen.kt` — Thêm callback `onNavigateToSearchScreen: () -> Unit`; chuyển ô tìm kiếm `ExploreToolBar` thành vùng bấm điều hướng sang `SearchScreen` thay vì nhập text trực tiếp tại `ExploreScreen`.

### What Must Stay Untouched
- `gradle/libs.versions.toml` và `app/build.gradle`: Không thêm bất kỳ dependency nào.
- `MovieApi.kt`, `MovieRepository.kt`, và `MovieRepositoryImp.kt`: Giữ nguyên `searchMovie(query: String)` hiện có (đã tự động cache `moviePreviews` trong `fetchAndCacheMovies` cho `MovieDetailViewModel`).
- `ExploreFilterScreen.kt`, `GenreFilterScreen.kt`, `YearFilterScreen.kt`, `CountryFilterScreen.kt`, và `ExploreViewModel.kt`: Giữ nguyên không làm hỏng luồng `ExploreFilter` hiện tại của `ExploreScreen`.
- `MovieItem.kt` và `RatingChip.kt`: Tái sử dụng trực tiếp `MovieItem` (đã có `3:4` poster, `12.dp` corner, `RatingBadge`).
- `MovieDetailScreen.kt` & `MovieDetailViewModel.kt`: Chỉ nhận `movieId` từ `MovieDetail(movieId)` như hiện tại.

---

## 4. Behavior vs `develop/phucnguyen/main`
- **Added:**
  - Màn hình `SearchScreen` tách biệt (`Search` route ngoài `MainScreen`, ẩn Bottom Bar, có nút Back, auto-focus input) `[R1]`.
  - Section `"Recent Searchs"` lưu tối đa 5 từ khóa mới nhất trong `SharedPreferences` (ẩn hoàn toàn section khi chưa có lịch sử; thêm mới/đẩy lên đầu khi Enter, click phim, hoặc click item lịch sử; xóa từng item) `[R2]`.
  - Debounce `500ms` khi gõ & Enter search ngay lập tức `[R3]`.
  - Grid 2 cột kết quả tìm kiếm + trạng thái `"Not Found"` chỉ gồm text (không ảnh 404) + điều hướng `MovieDetail` `[R4]`.
  - Logic ẩn/hiện icon Filter (`displayedMovies.size > 2`) với animation trượt ngang ra/vào `[R5]`.
  - `ModalBottomSheet` 5 section (`Genre`, `Language`, `Release Year`, `Minimum Rating`, `Sort By`) lấy dữ liệu từ kết quả API search `[R6]`.
  - Lọc & sắp xếp local ngay lập tức khi `Apply` + hàng filter chip có icon `X` xóa nhanh `[R7]`.
  - Hỗ trợ Light/Dark theme + unit tests cho toàn bộ domain/UI rule logic `[R8]`.
- **Changed:**
  - `ExploreScreen.kt` / `MainScreen.kt` / `AVMovieNavGraph.kt`: Nhấn vào ô tìm kiếm trên `ExploreScreen` điều hướng sang `SearchScreen` thay vì gõ trực tiếp trong `ExploreToolBar` `[R1]`.
- **Removed:** Không xóa code màn hình nào (giữ nguyên các màn hình Explore filter cũ để tránh hồi quy).

---

## 5. Implementation Steps & Checks

1. **Step 1 — Viết Unit Tests trước cho Domain & Search Rules (`R2`, `R5`, `R6`, `R7`, `R8`)**
   - Tạo `ManageRecentSearchesUseCaseTest.kt`, `FilterAndSortSearchMoviesUseCaseTest.kt`, và `SearchFilterUiRuleTest.kt` mô tả đầy đủ các assertion theo requirement (ban đầu fail vì chưa có class/method mới).
   - Check: `./gradlew testDebugUnitTest` (xác nhận fail đúng chỗ thiếu symbol trước khi code).

2. **Step 2 — Domain Models, UseCases & Repository Interface (`R2`, `R6`, `R7`, `R8`)**
   - Tạo `SearchFilter.kt`, `SearchHistoryRepository.kt`, `ManageRecentSearchesUseCase.kt`, `FilterAndSortSearchMoviesUseCase.kt` (thuần Kotlin, không import `android.*`/`androidx.*`).
   - Check: `./gradlew :app:compileDebugKotlin`.

3. **Step 3 — Data Layer & DI (`R2`, `R8`)**
   - Tạo `SearchHistoryRepositoryImp.kt` dùng `SharedPreferences` + `kotlinx.serialization.json` và bind trong `RepositoryModule.kt`.
   - Check: `./gradlew testDebugUnitTest` (các test domain và UI rule pass hoàn toàn).

4. **Step 4 — `SearchViewModel` (`R2`..`R7`)**
   - Tạo `SearchViewModel.kt` quản lý:
     - `query`, `recentSearches` (`StateFlow` từ `SearchHistoryRepository`),
     - debounce `500ms` (hủy job cũ khi gõ tiếp hoặc khi Enter),
     - lưu từ khóa vào `SearchHistoryRepository` đúng 3 thời điểm (`onSubmitSearch`, `onMovieClick`, `onRecentSearchClick`),
     - tải & cache genre map từ `movieRepository.getMovieGenres()` (fallback `ALL_GENRES` nếu lỗi),
     - gọi `movieRepository.searchMovie(query)` khi search từ khóa mới (reset `activeFilter` về `EMPTY`), giữ `rawSearchMovies` để dựng `AvailableSearchFilters` cho 5 section,
     - kết hợp `rawSearchMovies` + `activeFilter` bằng `FilterAndSortSearchMoviesUseCase` ở local khi `Apply` hoặc bấm `X` trên chip filter, và tính `shouldShowFilterIcon = displayedMovies.size > 2`.
   - Check: `./gradlew :app:compileDebugKotlin`.

5. **Step 5 — `SearchScreen` & `SearchFilterBottomSheet` (`R1`..`R8`)**
   - Tạo `SearchFilterBottomSheet.kt`: `ModalBottomSheet` 5 section (`Genre`, `Language`, `Release Year`, `Minimum Rating`, `Sort By`), `Reset`, `Apply`, thích ứng `MaterialTheme.colorScheme` (Light/Dark) + `BrandGradient`.
   - Tạo `SearchScreen.kt`:
     - Header: Nút Back + Search input (`Modifier.weight(1f)`, auto-focus, nút Clear `X`, IME Search) + Icon Filter bọc trong `AnimatedVisibility` trượt ra/vào theo chiều ngang (`slideInHorizontally` + `expandHorizontally` + `fadeIn` / `slideOutHorizontally` + `shrinkHorizontally` + `fadeOut`, kèm spacing bên trong block animation) khi `displayedMovies.size > 2`.
     - State 1 (`query.isBlank()`): Nếu `recentSearches.isEmpty()` thì không render section; khi `recentSearches.isNotEmpty()` hiển thị section `"Recent Searchs"` (tối đa 5 từ khóa, mới nhất trên cùng, bấm để tìm ngay, bấm `X` để xóa item).
     - State 2 (`query.isNotBlank()`): Hàng Filter Chips có nút `X` khi có filter active; Loading indicator; Error + Retry; `"Not Found"` text-only (Figma `1-913`) khi rỗng; Grid 2 cột `MovieItem` (Figma `1-911`) bấm vào mở `MovieDetail`.
     - System bars (`statusBarColor` / `navigationBarColor`) đồng bộ Light/Dark + `statusBarsPadding()` / `navigationBarsPadding()` / `imePadding()`.
     - Thêm `@Preview` cho Light & Dark mode (State 1, State 2 Results, State 2 Not Found + Filter Chips).
   - Check: `./gradlew :app:compileDebugKotlin`.

6. **Step 6 — Wiring Navigation & `ExploreScreen.kt` (`R1`)**
   - Thêm `Search` vào `Routes.kt`.
   - Cập nhật `AVMovieNavGraph.kt`, `MainScreen.kt`, và `ExploreScreen.kt` (chuyển `ExploreToolBar` thành ô tìm kiếm read-only clickable để tránh `SearchBar` chặn sự kiện chạm) để nhấn vào ô tìm kiếm trong `ExploreScreen` điều hướng sang `SearchScreen`, và bấm item phim trong `SearchScreen` điều hướng sang `MovieDetail`.
   - Check: `./gradlew :app:compileDebugKotlin`.

7. **Step 7 — Final Verification & Commits (`R8`)**
   - Chạy `./gradlew testDebugUnitTest` và `./gradlew :app:compileDebugKotlin` (nếu môi trường sandbox chặn Gradle daemon IPC, ghi rõ cờ `--no-daemon` khi báo cáo kết quả).
   - Kiểm tra tuân thủ `AGENTS.md`: không import Android trong `domain/`, không sửa `libs.versions.toml` / `build.gradle`, giữ nguyên comment hiện có.
   - Kế hoạch commit theo `.agents/skills/commit/SKILL.md`:
     1. `[test] add unit tests for search history and movie filter rules`
     2. `[feat] add search history persistence and movie search filter domain/data`
     3. `[feat] implement SearchScreen with debounce, recent searches and 5-section filter`

---

## Version Changes
- **v1 (2026-11-17):** Initial plan proposal for `SearchScreen` navigated from `ExploreScreen`, incorporating TMDB `discover/movie`-inspired 5 filter/sort sections, `SharedPreferences` recent searches (max 5), `500ms` debounce / immediate Enter search, strict `> 2` filter icon visibility, removable `X` filter chips, and AV-Movie Light/Dark theme.
- **v2 (2026-11-18):** Updated `R2` to hide the `"Recent Searchs"` section completely when search history is empty, updated `R5` to add horizontal slide-in/out animation (`AnimatedVisibility`) for the Filter icon, and refined `ExploreToolBar` clickable navigation note in Step 6.
- **v3 (2026-11-18):** Kept `MovieApi.searchMovie(query)` and `MovieRepository.searchMovie(query)` unchanged (no `discover/movie` endpoint or extra API query params), switched `Apply` / chip `X` filtering and sorting (`R7`) to run purely locally via `FilterAndSortSearchMoviesUseCase` over `rawSearchMovies`, and dropped unnecessary `MovieApi`/`MovieRepository` edits and `MovieRepositorySearchTest`.
- **v4 (2026-11-18):** Adjusted `R5` so the Filter icon remains visible after applying filters even when narrowed to 1 item (or <= 2 items) as long as the raw search result count exceeded 2; updated `R7` to reset scroll position to index 0 and animate fade in/out (`Crossfade`) between old and new result lists when applying or clearing filters/sorts.
