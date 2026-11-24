# Plan: Update `MovieDetailScreen` theo Figma `32_Dark_movie details, trailers` (`node-id=1-903`) & Truyền trực tiếp `Movie`

## 1. Goal & Scope
Cập nhật toàn diện màn hình chi tiết phim (`MovieDetailScreen.kt`, `MovieDetailViewModel.kt`) và luồng điều hướng / dữ liệu liên quan để:
1. Áp dụng bố cục Figma `32_Dark_movie details, trailers` (`node-id=1-903`) + `33_Dark_movie details, more like this` (`node-id=1-901`) cùng toàn bộ quyết định đã chốt của người dùng, thích ứng Light/Dark theo `AVMovieTheme`.
2. Xây dựng hiệu ứng cuộn thu gọn phần Hero & Thông tin phim: khi cuộn ở phần Tab, các phần phía trên thu gọn dần và cuối cùng **ghim cố định Toolbar chứa nút Back + tên phim ở trên cùng và thanh 3 Tab dock ngay phía dưới**.
3. **Loại bỏ cơ chế cache `moviePreviews` khi fetch list** trong `MovieRepositoryImp` / `MovieRepository`, thay bằng cơ chế **truyền trực tiếp object `Movie`** qua Navigation Compose vào `MovieDetail` để hiển thị trước toàn bộ thông tin sẵn có trong lúc gọi API chi tiết.

---

## 2. Báo cáo Inspect API hiện tại cho `MovieDetail` (`R1`)

Hiện tại khi mở `MovieDetailScreenVM(movieId)`, `MovieDetailViewModel.loadMovieDetail(id)` gọi `GetFullDetailMovieUseCase(id)`, trong đó thực thi **4 API TMDB v3 song song** (`async` trong `coroutineScope`) thông qua `MovieApi`:

| # | Endpoint (`MovieApi`) | Repository / Mapper | Dữ liệu trả về & Mục đích sử dụng | Ghi chú / Khoảng trống phát hiện |
|---|---|---|---|---|
| 1 | `GET movie/{movie_id}` (`getDetail`) | `MovieRepositoryImp.getMovieDetail` -> `MovieDetailDTO2Movie` | `MovieDetail`: `title`, `overview`, `posterPath`, `backdropPath`, `voteAverage`, `releaseDate`, `runtime` (`duration`), `genres`, `originalLanguage` | `MovieDetailDTO` có `productionCountries` & `originCountry` nhưng chưa map vào `MovieDetail`, nên tab `About` đang hiển thị nhầm `originalLanguage` cho mục `"Country"`. |
| 2 | `GET movie/{movie_id}/credits` (`getCasts`) | `MovieRepositoryImp.getCastForMovie` -> `CastDTO2Cast` | `List<Cast>`: `id`, `name`, `character`, `profilePath`, `knownForDepartment`, `order` | Hiển thị danh sách diễn viên trong tab `About`. |
| 3 | `GET movie/{movie_id}/videos` (`getVideosForMovie`) | `MovieRepositoryImp.getVideoForMovie` -> `VideoDTO2Video` | `List<Video>`: `id`, `name`, `key`, `site`, `size` (độ phân giải như `1080`), `type` (`Trailer`, `Teaser`, ...), `official`, `publishedAt` | TMDB `/videos` không trả về duration hay thumbnail URL mà trả về `published_at` (created/published time). Hiện tại `TrailerItem` đã dùng `publishedAt` và đang hardcode thumbnail `GLADIATOR_II.backdropPath` (cần sửa sang `movie.backdropPath`). |
| 4 | `GET movie/{movie_id}/recommendations` (`getRecommendations`) | `MovieRepositoryImp.getRecommendationsForMovie` -> `MoviePreviewDTO2Movie` | `List<Movie>` | Hiển thị ở tab `More like this`. Hiện tại các item trong tab này chưa gắn `onNavigateToMovieDetail` và `fetchAndCacheMovies` đang cache kết quả vào `moviePreviews`. |

- **Lưu ý về xử lý lỗi hiện tại:** `GetFullDetailMovieUseCase` đang yêu cầu **cả 4 API đều phải `Success`** (`results.any { it !is ResultData.Success }`), và khi lỗi thì `MovieDetailViewModel` gán `MovieDetailUiState.Error` với `data = null` (làm mất cả dữ liệu preview đã hiển thị trước đó). Khi cập nhật, chỉ xem `getMovieDetail` là bắt buộc; nếu `casts`/`videos`/`recommendations` lỗi thì fallback `emptyList()`, đồng thời giữ `data` preview trên `MovieDetailUiState.Error` nếu có.

---

## 3. Requirements (`R1..R7`)

| ID | Requirement | Source |
|---|---|---|
| **R1** | Báo cáo các API gọi cho Movie Detail; bổ sung map `country` (`productionCountries` / `originCountry`) cho `MovieDetail` và giữ dữ liệu preview khi API lỗi hoặc tab phụ lỗi. | User inspect request + `MovieApi.kt` / `MovieDetailDTO.kt` |
| **R2** | **Bỏ cache movie khi lấy list:** Xóa `moviePreviews` cache, `fetchAndCacheMovies`, và `getMoviePreview(id)` khỏi `MovieRepository` / `MovieRepositoryImp`. Khi click vào item phim ở mọi màn hình (`Home`, `CategoryDetail`, `Explore`, `Search`, `Archive`, và tab `More like this`), truyền trực tiếp object `Movie` vào `MovieDetail` để hiển thị trước các thông tin sẵn có (poster/backdrop, title, rating, release year, overview, genre chips map từ `genreIds`, trạng thái `isFavorite`). | User request |
| **R3** | **Hero section:** Edge-to-edge tràn dưới status bar, **không có streaming/cast icon** góc phải; **nút Back đặt cao hơn** so với mockup Figma (nằm gọn trong vùng `statusBarsPadding()` + `56.dp` top bar để thẳng hàng với toolbar khi thu gọn). | User request + Figma `1:903` |
| **R4** | **Movie Info section:** Dòng title chỉ hiện tên phim (**không có 2 icon share và bookmark**); dòng rating **chỉ giữ** icon sao + điểm rating + năm phát hành (**không có chip `13+`, `United States`, `Subtitle`, ... và không thêm thông tin phụ khác**); cùng 1 hàng có 2 nút **Play** (`Modifier.weight(1f)` giãn match parent theo Option A) và **Save** gọn bên phải; **giữ `GenreChip` hiện tại của app**; Overview có nút `"View More"` / `"Show less"`; **không hiển thị list actor ở phía trên tab** (để trong tab `About`). | User request + Quyết định 1 (Option A) & 3 + Figma `1:903` |
| **R5** | **3 Tabs (`Trailers`, `More like this`, `About`):** Tab `Trailers` giữ nguyên cơ chế background/thumbnail hiện tại (`getFullBackdropPath(movie.backdropPath)`, sửa bỏ hardcode `GLADIATOR_II.backdropPath`, kèm icon Play overlay) + hiển thị **tên trailer (`video.name`)** và **created time (`video.publishedAt` từ TMDB `/videos`, format ngày giờ sạch sẽ)** thay vì duration; tab `More like this` hiển thị lưới 2 cột `MovieItem` click mở `MovieDetail(movie)`; tab `About` hiển thị thông tin (`Country`, `Year`, `Duration`, `Language`) + danh sách `Casts`. | User request + Quyết định 2 + Figma `1:903`, `1:901` |
| **R6** | **Collapsing Header + Sticky Toolbar & Docked TabRow:** Khi scroll trong tab (hoặc kéo trên phần header), phần Hero + Movie Info + Genre + Overview thu gọn mượt mà lên trên; đến khi thu gọn hoàn toàn thì **chỉ còn Top Toolbar (nút Back + tên phim) ở trên cùng và thanh 3 Tab dock ngay phía dưới**, nội dung tab cuộn phía dưới phần dock đó. | User request |
| **R7** | **Theme, Clean Architecture & Tests:** Hỗ trợ Light/Dark theo `AVMovieTheme` (`MaterialTheme.colorScheme` + `BrandGradient` / `BrandPrimary`), giữ `domain/` thuần Kotlin, không sửa `libs.versions.toml` / `build.gradle`, có unit tests dưới `app/src/test/` và pass `./gradlew testDebugUnitTest --no-daemon` + `./gradlew :app:compileDebugKotlin --no-daemon`. | `AGENTS.md` + `planning/SKILL.md` |

---

## 4. Các điểm thiết kế đã chốt

1. **Bố cục nút `Play` & `Save` (Option A):** Đặt trên cùng 1 hàng (`Row`), nút **Play** dùng `Modifier.weight(1f)` (giãn match phần chiều ngang còn lại của parent, thay cho `150.dp` cố định ở `MovieAction.kt` cũ) với nền `BrandGradient`, icon Play trong vòng tròn trắng và text `"Play"`; nút **Save** đặt gọn bên phải cùng hàng với icon bookmark + text `"Save"`/`"Saved"`.
2. **Thông tin item Tab `Trailers` (Theo TMDB `/movie/{movie_id}/videos`):**
   - Dùng **created time (`publishedAt`)** từ TMDB `/videos` thay cho duration (không thêm field duration giả vào `Video`), format hiển thị qua `formatDate` (`dd-MM-yyyy`).
   - **Background / thumbnail:** Giữ nguyên cơ chế hiện tại dùng `getFullBackdropPath(...)` và nền row trong suốt của `MovieDetailScreen.kt` (chỉ sửa việc hardcode `GLADIATOR_II.backdropPath` sang truyền `movie.backdropPath` của phim hiện tại).
3. **Dòng Rating:** Chỉ giữ icon sao + điểm rating + năm phát hành (vd `★ 9.8   2022`), không giữ các chip `13+`, `United States`, `Subtitle` và không thêm thông tin nào khác.

---

## 5. Approach & Architecture

### 5.1. Cơ chế truyền `Movie` qua Navigation (không cache list)
- Giữ `domain/model/Movie.kt` thuần Kotlin (không phụ thuộc `android.*` / `androidx.*`).
- Tạo `MovieNavPayload` (`@Serializable` trong `presentation/navigation/`) + mapper `Movie <-> MovieNavPayload` cùng `MovieNavType : NavType<MovieNavPayload>` sử dụng `kotlinx.serialization.json.Json` + **URL-safe Base64 (`java.util.Base64.getUrlEncoder().withoutPadding()`)** để đóng gói `Movie` thành tham số route `MovieDetail(val movie: MovieNavPayload)` an toàn tuyệt đối với mọi ký tự đặc biệt (`/`, `?`, `&`, `#`, `\n`) trong `posterPath`, `overview`, `title`, đồng thời sống sót qua process death / back-stack đa tầng (vd mở `Detail` từ tab `More like this`).
- Cập nhật callback `onNavigateToMovieDetail: (Movie) -> Unit` xuyên suốt:
  - `MovieItem.kt` -> `(Movie) -> Unit`
  - `HomeScreen.kt`, `CategoryDetailScreen.kt`, `ExploreScreen.kt`, `SearchScreen.kt`, `ArchiveScreen.kt`, `MainScreen.kt`, `AVMovieNavGraph.kt`, và `MoreLikeThisSection` trong `MovieDetailScreen.kt`.

### 5.2. Pre-populate & Load Detail (`MovieDetailViewModel` + `GetFullDetailMovieUseCase`)
- `MovieRepository` / `MovieRepositoryImp`: xóa `moviePreviews`, `fetchAndCacheMovies`, `getMoviePreview(id)`. Đổi `fetchAndCacheMovies` thành `fetchMovies` không lưu state.
- `MovieDetailDTO2Movie`: map thêm `country` từ `productionCountries.firstOrNull()?.name ?: originCountry.firstOrNull() ?: originalLanguage` vào `MovieDetail`.
- `MovieDetailViewModel.loadMovieDetail(movie: Movie)`:
  1. Nếu chưa load hoặc khác `movie.id`, convert `movie` -> `FullDetailMovie` sơ bộ (`title`, `posterPath`, `backdropPath`, `voteAverage`, `releaseDate`, `overview`, `originalLanguage`, `isFavorite`, map `genreIds` sang `List<Genre>` bằng `ALL_GENRES` + `getMovieGenres()` nếu cần) và emit `MovieDetailUiState.Loading(previewData)`.
  2. Gọi `GetFullDetailMovieUseCase(movie.id)` chạy song song 4 API. `movieDetail` là bắt buộc; `casts`, `videos`, `recommendations` fallback `emptyList()` nếu request phụ lỗi.
  3. Khi `Success`, cập nhật `MovieDetailUiState.Success(...)` (giữ trạng thái save local nếu người dùng đã bấm `Save` trong lúc đang tải). Khi lỗi, giữ `previewData` trong `MovieDetailUiState.Error(previewData)` kèm khả năng Retry thay vì xóa trắng màn hình.
  4. Bổ sung action `toggleSave()` trong `MovieDetailViewModel` để nút `Save` hoạt động tương tác mượt mà trên UI.

### 5.3. Collapsing Header + Pinned Toolbar & Docked Tabs (`MovieDetailScreen.kt`)
- Xây dựng cấu trúc `CollapsingMovieDetailLayout`:
  - **Collapsible Header (`Hero` + `Title` + `Rating/Year` + `Play/Save` + `GenreChips` + `Overview`)** đo chiều cao tự nhiên động (nhờ vậy mở rộng `"View More"` hay wrap genre `FlowRow` không bị lệch offset).
  - **Shared Scroll / `NestedScrollConnection` (`exitUntilCollapsed`):**
    - Khi kéo lên trong bất kỳ tab nào (`Trailers`, `More like this`, `About`) hoặc kéo trực tiếp trên phần header: `onPreScroll` tiêu thụ phần cuộn âm để thu gọn header từ `0f` đến `-maxCollapsePx` (chỉ chừa lại chiều cao của `PinnedToolbar`), sau đó mới cuộn danh sách bên trong tab.
    - Khi kéo xuống: danh sách tab cuộn về đỉnh (`firstVisibleItemIndex == 0 && offset == 0`) rồi `onPostScroll` mở rộng header trở lại.
    - Dùng `Modifier.layout` giảm chiều cao layout thực tế của header theo `collapseOffset`, kết hợp `graphicsLayer { translationY }` / `clipToBounds()` để `TabRow` trượt lên dính sát dưới `PinnedToolbar` và `HorizontalPager` (`Modifier.weight(1f)`) tự động giãn đầy phần không gian còn lại, không bị khoảng trống ở đáy màn hình.
  - **Pinned Top Toolbar (Overlay trên cùng):**
    - Luôn đặt ở vùng `statusBarsPadding()` + `height(56.dp)` (nút Back cao hơn Figma mockup).
    - Nền toolbar (`MaterialTheme.colorScheme.background`), tiêu đề phim (`maxLines = 1, TextOverflow.Ellipsis`), và màu icon Back nội suy mượt mà theo `collapseFraction` (`0f` -> `1f`): trong suốt + icon Back trắng (trên nền hero có scrim nhẹ) khi mở rộng, chuyển dần sang nền toolbar + chữ & icon theo `onBackground` khi thu gọn hoàn toàn.

---

## 6. Files to Modify & Create

### Files to Modify
- `app/src/main/java/com/av/movie/domain/repository/MovieRepository.kt` — Xóa `getMoviePreview(id: Int)`.
- `app/src/main/java/com/av/movie/data/repository/MovieRepositoryImp.kt` — Xóa `moviePreviews`, `getMoviePreview`, đổi `fetchAndCacheMovies` thành `fetchMovies`.
- `app/src/main/java/com/av/movie/domain/model/MovieDetail.kt` — Thêm `val country: String = ""`.
- `app/src/main/java/com/av/movie/data/mapper/MovieDetailDTO2Movie.kt` — Map `country` từ `productionCountries` / `originCountry`.
- `app/src/main/java/com/av/movie/domain/usecase/GetFullDetailMovieUseCase.kt` — Giữ `movieDetail` bắt buộc, fallback `emptyList()` khi API phụ (`casts`/`videos`/`recommendations`) lỗi.
- `app/src/main/java/com/av/movie/presentation/navigation/Routes.kt` & `AVMovieNavGraph.kt` — Thêm `MovieDetail(val movie: MovieNavPayload)` với `MovieNavType`.
- `app/src/main/java/com/av/movie/presentation/components/MovieItem.kt` — Đổi callback `onNavigateToMovieDetail: (Movie) -> Unit = {}`.
- `app/src/main/java/com/av/movie/presentation/components/MovieAction.kt` & `MovieInfo.kt` — Cập nhật nút `Play` (`weight(1f)`) + `Save` gọn cùng hàng (Option A) và dòng Rating chỉ giữ sao + điểm + năm phát hành.
- `app/src/main/java/com/av/movie/presentation/screen/home/HomeScreen.kt`, `categoryDetail/CategoryDetailScreen.kt`, `explore/ExploreScreen.kt`, `search/SearchScreen.kt`, `archive/ArchiveScreen.kt`, `main/MainScreen.kt` — Truyền `Movie` qua callback điều hướng `MovieDetail`.
- `app/src/main/java/com/av/movie/presentation/screen/movieDetail/MovieDetailViewModel.kt` — Nhận `Movie`, pre-populate state từ `Movie` + map `genreIds`, giữ preview khi lỗi, thêm `toggleSave()`, bỏ `getMoviePreview`.
- `app/src/main/java/com/av/movie/presentation/screen/movieDetail/MovieDetailScreen.kt` — Thiết kế lại theo Figma `1:903` + các điểm đã chốt: Hero (không streaming icon, Back cao hơn), Title (không share/bookmark), Rating (chỉ giữ sao + điểm + năm), `Play` match parent + `Save` (Option A), giữ `GenreChip`, `View More`, Collapsing header + Pinned toolbar + Docked 3 tabs (`Trailers` dùng `movie.backdropPath` + `publishedAt`, `More like this` click mở `MovieDetail`, `About` chứa metadata + `Casts`), hỗ trợ Light/Dark + Preview.

### Files to Create
- `app/src/main/res/drawable/ic_bookmark_outline.xml` & `ic_bookmark_filled.xml` — Icon Save/Bookmark cho nút `Save` (không thêm dependency `material-icons-extended`).
- `app/src/main/java/com/av/movie/presentation/navigation/MovieNavType.kt` — Custom `NavType` + `MovieNavPayload` mapper cho Navigation Compose.
- `app/src/main/java/com/av/movie/presentation/screen/movieDetail/MovieDetailPreviewMapper.kt` — Chuyển `Movie` -> `FullDetailMovie` sơ bộ + format trailer created time (`publishedAt`) để unit test thuần JVM.
- **Unit Tests (`app/src/test/`):**
  - `app/src/test/java/com/av/movie/data/repository/MovieRepositoryImpTest.kt` — Verify list APIs không cache `moviePreviews` và `MovieDetailDTO2Movie` map `country`.
  - `app/src/test/java/com/av/movie/domain/usecase/GetFullDetailMovieUseCaseTest.kt` — Verify gọi song song 4 API và fallback graceful khi secondary API lỗi.
  - `app/src/test/java/com/av/movie/presentation/navigation/MovieNavPayloadTest.kt` — Verify encode/decode URL-safe Base64 JSON với ký tự đặc biệt (`title`, `posterPath`, `overview`).
  - `app/src/test/java/com/av/movie/presentation/screen/movieDetail/MovieDetailHelpersTest.kt` — Verify pre-populate `Movie -> FullDetailMovie`, genre resolution, và format trailer `publishedAt`.

### What Must Stay Untouched
- `gradle/libs.versions.toml` và `app/build.gradle`: Không thêm/sửa dependency.
- `domain/` layers: Không import `android.*` hoặc `androidx.*`.
- `Video.kt` và `ImageUtils.kt`: Giữ nguyên model `Video` (dùng `publishedAt` sẵn có) và `getFullBackdropPath(...)` hiện tại.

---

## 7. Implementation Steps & Checks

1. **Step 1 — Viết Unit Tests nền (`R1`, `R2`, `R5`)**
   - Tạo `MovieRepositoryImpTest.kt`, `GetFullDetailMovieUseCaseTest.kt`, `MovieNavPayloadTest.kt`, `MovieDetailHelpersTest.kt`.
   - Check: `./gradlew testDebugUnitTest --no-daemon` (xác nhận fail đúng ở các symbol/behavior mới chưa tạo).
2. **Step 2 — Domain, Data & Repository Updates (`R1`, `R2`)**
   - Bỏ `moviePreviews` cache & `getMoviePreview` khỏi `MovieRepository` / `MovieRepositoryImp`.
   - Cập nhật `MovieDetail`, `MovieDetailDTO2Movie`, `GetFullDetailMovieUseCase`.
   - Check: `./gradlew testDebugUnitTest --no-daemon`.
3. **Step 3 — Navigation Payload & Wiring All Callers (`R2`)**
   - Tạo `MovieNavType.kt` / `MovieNavPayload`, cập nhật `Routes.kt`, `AVMovieNavGraph.kt`, `MovieItem.kt`, `MainScreen.kt`, `HomeScreen.kt`, `CategoryDetailScreen.kt`, `ExploreScreen.kt`, `SearchScreen.kt`, `ArchiveScreen.kt`.
   - Check: `./gradlew :app:compileDebugKotlin --no-daemon` & `./gradlew testDebugUnitTest --no-daemon`.
4. **Step 4 — `MovieDetailViewModel` & `MovieDetailScreen` UI Redesign + Collapsing Scroll (`R3`..`R7`)**
   - Thêm icon `ic_bookmark_outline.xml` & `ic_bookmark_filled.xml`.
   - Tạo `MovieDetailPreviewMapper.kt`, cập nhật `MovieDetailViewModel.kt` (nhận `Movie`, pre-populate, `toggleSave`, resilience).
   - Cập nhật `MovieDetailScreen.kt`, `MovieAction.kt`, `MovieInfo.kt` theo toàn bộ spec đã chốt + Light/Dark theme + Preview.
   - Check: `./gradlew :app:compileDebugKotlin --no-daemon` & `./gradlew testDebugUnitTest --no-daemon`.
5. **Step 5 — Final Verification & Commits (`R7`)**
   - Chạy `./gradlew testDebugUnitTest --no-daemon` và `./gradlew :app:compileDebugKotlin --no-daemon`.
   - Kiểm tra tuân thủ `AGENTS.md` (layer boundaries, no `libs.versions.toml` / `build.gradle` edits, preserve unrelated comments).
   - Commit theo `.agents/skills/commit/SKILL.md`:
     - `[refactor] remove movie list cache and pass Movie object via navigation`
     - `[feat] redesign MovieDetailScreen with collapsing header and docked tabs`
     - `[test] add unit tests for MovieDetail usecase, navigation payload, and helpers`

---

## Version Changes
- **v1 (2026-11-18):** Initial plan proposal covering API inspection report, removal of `moviePreviews` cache in favor of passing `Movie` object via type-safe Compose Navigation, `MovieDetailScreen` UI redesign per Figma `1:903` with user overrides, and collapsing header with pinned toolbar + docked 3-tab layout.
- **v2 (2026-11-18):** Locked in user confirmations: Option A for `Play` (`weight(1f)`) + compact `Save` on one row; `Trailers` tab uses TMDB `publishedAt` (created time) instead of duration and preserves the existing `getFullBackdropPath` thumbnail/background mechanism (fixing `GLADIATOR_II.backdropPath` hardcode to `movie.backdropPath`, removing unused `Video`/`ImageUtils` edits); rating row keeps only star + rating score + release year with no extra metadata added.
