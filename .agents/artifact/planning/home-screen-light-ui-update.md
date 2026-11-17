# Plan: Update HomeScreen UI theo Figma `21_Light_home` (`node-id=1-742`)

## 1. Goal & Scope
Cập nhật toàn diện màn hình `HomeScreen.kt` (cùng `MainScreen.kt`, `MovieItem.kt`, `RatingChip.kt` liên quan) theo thiết kế Figma `21_Light_home` (`node-id=1-742`) và các quyết định đã thống nhất xuyên suốt cuộc hội thoại:
- Ghi nhận đầy đủ đặc tả cho phần **Hero section** (từ các trao đổi trước đó + bỏ điểm IMDb khỏi Hero vì Figma không hiển thị điểm số ở Hero).
- Hỗ trợ đầy đủ **Light / Dark theme** động theo `MaterialTheme.colorScheme`.
- Cập nhật các section bên dưới Hero (`Popular`, `Top Rated`), đổi rating badge trên card phim sang dạng pill gọn giống Figma, và giữ nguyên phần **Genre list** (`Genres` / `GenreItem`).

## 2. Requirements (`R1..R8`)

### A. Phần Hero section (`R1` — Tổng hợp đặc tả đã thống nhất + cập nhật mới)
- **Layout & Thành phần hiển thị:**
  - **Không thêm** logo Movix, icon Search, icon Notification ở top bar.
  - **Không thêm** dòng Genre bên dưới tên phim.
  - **Không hiển thị** thanh/chip điểm số IMDb (`RatingChip`) trên Hero banner (cập nhật theo Figma `21_Light_home`).
  - Hero banner cao `430.dp`, `ContentScale.Crop` (`Alignment.TopCenter`), tràn lên dưới Status Bar (edge-to-edge với Status Bar trong suốt, kèm top scrim tối nhẹ `Grey10.copy(alpha = 0.45f)` ở 22% trên cùng để icon hệ thống luôn rõ, và bottom scrim tối phía dưới để chữ trắng & nút luôn nổi bật trên cả Light và Dark Mode).
  - Overlay phía dưới hiển thị: tên phim (`24.sp`, `FontWeight.Bold`, tối đa 2 dòng ellipsis) + cụm 2 nút pill cao bằng nhau `40.dp`:
    - **Play**: nền `BrandGradient` (`Cyan90 -> Blue90`), icon play trong vòng tròn trắng, nhấn để mở chi tiết phim.
    - **+ My List**: viền trắng `1.5.dp`, nền tối mờ, icon `+` / `✓` và text `"My List"`, toggle trạng thái.
- **Carousel & Animation (`MovieCarousel`):**
  - Giữ nguyên cấu trúc `HorizontalPager` + dãy thumbnail `LazyRow` phía dưới.
  - Virtual infinite pager (`Int.MAX_VALUE` khi `movies.size > 1`) để chuyển từ phim cuối sang phim đầu mượt mà, không bị tua ngược toàn bộ danh sách; `beyondViewportPageCount = 1`; `tween(650ms, FastOutSlowInEasing)`.
  - Dãy thumbnail đồng bộ theo `pagerState.targetPage` bằng `collectLatest` + `animateScrollBy` (`650ms`, `FastOutSlowInEasing`); **chỉ scroll nếu phần content của item được focus hiển thị < 70% chiều rộng** trong vùng content của viewport; `alpha` thumbnail chuyển mượt bằng `animateFloatAsState`.
  - Nhấn giữ banner (`interactionSource`) hoặc kéo pager tạm dừng auto-advance; nhấn vào thumbnail sẽ **reset lại bộ đếm `3000ms`** auto-scroll (sau khi animation trượt trang kết thúc).
  - Gắn lifecycle `repeatOnLifecycle(Lifecycle.State.RESUMED)`: hủy toàn bộ animation & auto-advance ngay khi rời màn hình `Home` (đổi tab, mở `MovieDetail`, xuống nền); khi mới vào hoặc quay lại `Home`, luôn **đợi `500ms`** trước khi bắt đầu sync scroll và chu kỳ auto-advance. Không reload lại API khi quay lại tab nếu `uiState` đã có dữ liệu.

### B. Phần Genre list (`R2`)
- Giữ nguyên `Genres` (`LazyHorizontalGrid` 3 hàng) và `GenreItem` (19 ảnh `genre_*` decode async qua Coil + bảng màu fallback không quá tối).
- Chỉ đồng bộ typography & màu tiêu đề section `"Categories"` với các section header khác.

### C. Hỗ trợ Light / Dark Theme (`R3` — Đã chốt)
- App hỗ trợ **Light / Dark theme** theo `AVMovieTheme` (`isSystemInDarkTheme()`):
  - `HomeScreen.kt`: thay nền hardcode `Grey10` bằng `MaterialTheme.colorScheme.background`; các tiêu đề section và tên phim dùng `MaterialTheme.colorScheme.onBackground`; chuyển tiếp từ đáy Hero banner (tối) sang nền `LazyColumn` (`LightBackground` trắng ở Light Mode / `DarkBackground` ở Dark Mode) sạch sẽ giống `21_Light_home` (hoặc fade về `MaterialTheme.colorScheme.background` ở Dark Mode và `Color.Black.copy(alpha = 0.85f)` ở Light Mode để Hero kết thúc gọn gàng).
  - `MainScreen.kt`: thay `Grey10` / `Color.White` ở `Scaffold`, `BottomNavigation` (`backgroundColor = MaterialTheme.colorScheme.surface`, `selectedContentColor` / `unselectedContentColor` theo `onSurface` / `onSurfaceVariant`), `setNavigationBarColor` và `setStatusBarColor(..., darkIcons = !isDarkTheme)` khi không ở `Home` (khi ở `Home` giữ Status Bar trong suốt với `darkIcons = false` vì phía trên có Hero tối).
  - `MovieItem.kt`: thêm tham số `titleColor: Color = Color.White` (hoặc cập nhật các màn hình tối khác không bị vỡ tương phản), `HomeScreen` truyền `MaterialTheme.colorScheme.onBackground`.

### D. Section Headers, Spacing & Movie Cards (`R4`, `R5`)
- **R4 — Section Header & Spacing:**
  - Tiêu đề (`"Popular"`, `"Categories"`, `"Top Rated"`): `MaterialTheme.typography.titleLarge` (`20.sp`, `FontWeight.Bold`), màu `MaterialTheme.colorScheme.onBackground`.
  - Nút `"See all"`: giữ `BrandGradient`, dùng `ContentPadding` gọn để căn thẳng mép phải `16.dp`, font `14.sp` `FontWeight.SemiBold`.
  - Padding ngang chuẩn `16.dp`; `LazyRow` dùng `contentPadding = PaddingValues(horizontal = 16.dp)` với `spacedBy(12.dp)`; khoảng cách dọc giữa các section `20.dp`–`24.dp`.
- **R5 — Movie Cards & Rating Badge kiểu Figma:**
  - Section `Popular` (`isBackdrop = false`): tăng kích thước card lên `width(145.dp)`–`150.dp`, bo góc `12.dp`, tỷ lệ `aspectRatio(3f / 4f)` với `ContentScale.Crop` giống `21_Light_home`.
  - Section `Top Rated` (`isBackdrop = true`): bo góc `12.dp`, `aspectRatio(16f / 9f)`, `ContentScale.Crop`, tiêu đề `maxLines = 1`, `TextOverflow.Ellipsis`, `FontWeight.SemiBold`.
  - **Rating Badge:** Đổi badge trên `MovieItem` sang dạng pill gọn giống Figma `21_Light_home` (chỉ hiển thị điểm số làm tròn 1 chữ số thập phân như `"9.8"`, chữ trắng `12.sp` `FontWeight.SemiBold`, bo góc `6.dp`, padding `horizontal = 8.dp, vertical = 4.dp`, đặt ở `TopStart` với padding `8.dp`, nền `BrandGradient` đồng bộ màu thương hiệu AV-Movie — hoặc tham số giữ `RatingChip` cũ cho `MovieInfo` ở `MovieDetailScreen`).

### E. Clean Architecture & Kiểm thử (`R6`, `R7`)
- **R6:** Gộp `Category` và `MovieCategory` đang trùng lặp trong `HomeScreen.kt` thành một composable dùng chung `MovieCategorySection`. Không thêm dependency mới vào `libs.versions.toml` / `build.gradle.kts`.
- **R7:** Bổ sung `HomeViewModelTest.kt` trong `app/src/test/java/com/av/movie/presentation/screen/home/HomeViewModelTest.kt` để kiểm tra `HomeUiState` (`nowPlayingMovies.take(5)`, `Success`, `Error`, `Loading`); chạy `./gradlew testDebugUnitTest` và `./gradlew :app:compileDebugKotlin`.

## 3. Existing Code & Blast Radius
- `app/src/main/java/com/av/movie/ui/theme/Theme.kt` & `Color.kt`: Đã có sẵn `LightColorScheme` và `DarkColorScheme`.
- `app/src/main/java/com/av/movie/presentation/components/RatingChip.kt`: Được dùng bởi `MovieInfo.kt` (`MovieDetailScreen`) và `MovieItem.kt`. Giữ `RatingChip` cho `MovieInfo` (hoặc thêm `RatingBadge` mới trong `RatingChip.kt` dùng cho `MovieItem` và có thể tái sử dụng).
- `app/src/main/java/com/av/movie/presentation/components/MovieItem.kt`: Được dùng bởi `HomeScreen`, `ArchiveScreen`, `CategoryDetailScreen`, `ExploreScreen`, `MovieDetailScreen`. Giữ mặc định `titleColor: Color = Color.White` để các màn hình tối khác không bị mất tương phản.
- `app/src/main/java/com/av/movie/presentation/screen/main/MainScreen.kt`: Điều chỉnh `Scaffold`, `BottomNavigation`, và `SystemUiController` hỗ trợ Light/Dark.
- `app/src/main/java/com/av/movie/presentation/screen/home/HomeScreen.kt`: Bỏ `RatingChip` khỏi `PopularMovie`, đồng bộ Light/Dark theme, chuẩn hóa section headers & `MovieCategorySection`.

## 4. Behavior vs Baseline (Bao gồm toàn bộ mục tiêu Hero + Light Home)
- **Hero Section:** Bỏ `RatingChip` khỏi `PopularMovie`; giữ thiết kế Hero edge-to-edge, bỏ logo/search/notification/genre, `Play` + `+ My List` 40dp ngang bằng nhau, animation & lifecycle-aware carousel mượt mà `[R1]`.
- **Genre Section:** Giữ nguyên `Genres` grid & `GenreItem`, chỉ đồng bộ style tiêu đề `"Categories"` `[R2]`.
- **Light/Dark & Cards:** `HomeScreen` + `MainScreen` thích ứng Light/Dark theo `MaterialTheme.colorScheme` `[R3]`; section headers & spacing chuẩn `16.dp` `[R4]`; `MovieItem` dùng `12.dp` corner, `aspectRatio` cố định, badge điểm gọn giống Figma `[R5]`; gộp `Category` + `MovieCategory` thành `MovieCategorySection` `[R6]`.
- **Tests:** Thêm `HomeViewModelTest.kt` `[R7]`.

## 5. Implementation Steps & Checks
1. **Step 1 — Thêm unit test nền cho `HomeViewModel` (`R7`)**
   - Tạo `HomeViewModelTest.kt` test `getInitialData()` (`Success` với `nowPlayingMovies` giới hạn 5 phần tử, `Error` khi repository trả lỗi).
   - Check: `./gradlew testDebugUnitTest`.
2. **Step 2 — Cập nhật `RatingChip.kt` (thêm `RatingBadge` kiểu Figma) & `MovieItem.kt` (`R5`, `R6`)**
   - Thêm composable `RatingBadge(rating: Double, modifier: Modifier = Modifier)` gọn giống Figma (nền `BrandGradient`, bo `6.dp`, số `9.8` trắng).
   - Cập nhật `MovieItem`: dùng `RatingBadge`, bo góc `12.dp`, `aspectRatio(3f / 4f)` / `aspectRatio(16f / 9f)` + `ContentScale.Crop`, thêm `titleColor: Color = Color.White`, title `maxLines = 1` + `TextOverflow.Ellipsis`.
   - Check: `./gradlew :app:compileDebugKotlin`.
3. **Step 3 — Cập nhật `HomeScreen.kt` (`R1`..`R6`)**
   - Bỏ `RatingChip` khỏi `PopularMovie` (và chỉnh bottom scrim Hero phù hợp cả Light/Dark).
   - Giữ nguyên `MovieCarousel`, `HomeHeroActions`, và `Genres` grid.
   - Đổi `LazyColumn` nền sang `MaterialTheme.colorScheme.background`; gộp `Category` + `MovieCategory` thành `MovieCategorySection`; chuẩn hóa typography (`titleLarge` / `onBackground`), `See all` alignment, `PaddingValues(horizontal = 16.dp)`, card `Popular` rộng `145.dp`.
   - Thêm `@Preview` cho cả Light và Dark mode.
   - Check: `./gradlew :app:compileDebugKotlin`.
4. **Step 4 — Cập nhật `MainScreen.kt` hỗ trợ Light/Dark Theme (`R3`)**
   - Đồng bộ `Scaffold`, `BottomNavigation`, icon/text tint và `systemUiController` (`darkIcons = !isDarkTheme` khi không ở `Home`, `navigationBarColor = MaterialTheme.colorScheme.surface`) theo `MaterialTheme.colorScheme` và `isSystemInDarkTheme()`.
   - Check: `./gradlew :app:compileDebugKotlin` & `./gradlew testDebugUnitTest`.
5. **Step 5 — Final Verification & Commit (`R7`)**
   - Chạy `./gradlew testDebugUnitTest` và `./gradlew :app:compileDebugKotlin`.
   - Kiểm tra tuân thủ `AGENTS.md` (không sửa `libs.versions.toml` / `build.gradle.kts`, không trailing newline).
   - Commit theo `.agents/skills/commit/SKILL.md`:
     - `[test] add HomeViewModel unit tests`
     - `[ui] support light-dark theme and update HomeScreen layout per Figma`

## Version Changes
- **v1 (2026-11-16):** Initial plan proposal for updating `HomeScreen.kt` below Hero section to match Figma `21_Light_home` (`node-id=1-742`) while preserving Hero section and `Genres` grid.
- **v2 (2026-11-16):** Finalized Light/Dark theme support across `HomeScreen` and `MainScreen`, switched `MovieItem` rating badge to Figma compact score pill, removed `RatingChip` from Hero section, and documented full Hero section specifications in the plan.