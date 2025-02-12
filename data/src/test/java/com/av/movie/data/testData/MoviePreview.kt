package com.av.movie.data.testData

import com.av.movie.data.api.model.MoviePreviewDTO
import com.av.movie.data.api.model.Movie

// >>>>>> DTO
val SONIC_THE_HEDGEHOG_3 = MoviePreviewDTO(
    adult = false,
    backdropPath = "/zOpe0eHsq0A2NvNyBbtT6sj53qV.jpg",
    genreIds = listOf(28, 878, 35, 10751),
    id = 939243,
    originalLanguage = "en",
    originalTitle = "Sonic the Hedgehog 3",
    overview = "Sonic, Knuckles, and Tails reunite against a powerful new adversary, Shadow, a mysterious villain with powers unlike anything they have faced before. With their abilities outmatched in every way, Team Sonic must seek out an unlikely alliance in hopes of stopping Shadow and protecting the planet.",
    popularity = 4471.466,
    posterPath = "/d8Ryb8AunYAuycVKDp5HpdWPKgC.jpg",
    releaseDate = "2024-12-19",
    title = "Sonic the Hedgehog 3",
    video = false,
    voteAverage = 7.7,
    voteCount = 397
)

val GLADIATOR_II = MoviePreviewDTO(
    adult = false,
    backdropPath = "/euYIwmwkmz95mnXvufEmbL6ovhZ.jpg",
    genreIds = listOf(28, 12, 18),
    id = 558449,
    originalLanguage = "en",
    originalTitle = "Gladiator II",
    overview = "Years after witnessing the death of the revered hero Maximus at the hands of his uncle, Lucius is forced to enter the Colosseum after his home is conquered by the tyrannical Emperors who now lead Rome with an iron fist. With rage in his heart and the future of the Empire at stake, Lucius must look to his past to find strength and honor to return the glory of Rome to its people.",
    popularity = 3935.606,
    posterPath = "/2cxhvwyEwRlysAmRH4iodkvo0z5.jpg",
    releaseDate = "2024-11-05",
    title = "Gladiator II",
    video = false,
    voteAverage = 6.778,
    voteCount = 2136
)

val CULPA_TUYA = MoviePreviewDTO(
    adult = false,
    backdropPath = "/k24eZq5I3jyz4htPkZCRpnUmBzE.jpg",
    genreIds = listOf(10749, 18),
    id = 1156593,
    originalLanguage = "es",
    originalTitle = "Culpa tuya",
    overview = "The love between Noah and Nick seems unwavering despite their parents' attempts to separate them. But his job and her entry into college open up their lives to new relationships that will shake the foundations of both their relationship and the Leister family itself.",
    popularity = 2623.666,
    posterPath = "/1sQA7lfcF9yUyoLYC0e6Zo3jmxE.jpg",
    releaseDate = "2024-12-26",
    title = "Your Fault",
    video = false,
    voteAverage = 7.1,
    voteCount = 750
)

val MUFASA_THE_LION_KING = MoviePreviewDTO(
    adult = false,
    backdropPath = "/oHPoF0Gzu8xwK4CtdXDaWdcuZxZ.jpg",
    genreIds = listOf(12, 10751, 16),
    id = 762509,
    originalLanguage = "en",
    originalTitle = "Mufasa: The Lion King",
    overview = "Mufasa, a cub lost and alone, meets a sympathetic lion named Taka, the heir to a royal bloodline. The chance meeting sets in motion an expansive journey of a group of misfits searching for their destiny.",
    popularity = 2928.339,
    posterPath = "/iBqXjFkAozQ1z2sfAiWwBimbiJX.jpg",
    releaseDate = "2024-12-18",
    title = "Mufasa: The Lion King",
    video = false,
    voteAverage = 7.5,
    voteCount = 517
)

val VENOM_THE_LAST_DANCE = MoviePreviewDTO(
    adult = false,
    backdropPath = "/vZG7PrX9HmdgL5qfZRjhJsFYEIA.jpg",
    genreIds = listOf(28, 878, 12),
    id = 912649,
    originalLanguage = "en",
    originalTitle = "Venom: The Last Dance",
    overview = "Eddie and Venom are on the run. Hunted by both of their worlds and with the net closing in, the duo are forced into a devastating decision that will bring the curtains down on Venom and Eddie's last dance.",
    popularity = 3038.094,
    posterPath = "/aosm8NMQ3UyoBVpSxyimorCQykC.jpg",
    releaseDate = "2024-10-22",
    title = "Venom: The Last Dance",
    video = false,
    voteAverage = 6.813,
    voteCount = 2213
)

val WICKED = MoviePreviewDTO(
    adult = false,
    backdropPath = "/uKb22E0nlzr914bA9KyA5CVCOlV.jpg",
    genreIds = listOf(18, 10749, 14),
    id = 402431,
    originalLanguage = "en",
    originalTitle = "Wicked",
    overview = "In the land of Oz, ostracized and misunderstood green-skinned Elphaba is forced to share a room with the popular aristocrat Glinda at Shiz University, and the two's unlikely friendship is tested as they begin to fulfill their respective destinies as Glinda the Good and the Wicked Witch of the West.",
    popularity = 2274.665,
    posterPath = "/2E1x1qcHqGZcYuYi4PzVZjzg8IV.jpg",
    releaseDate = "2024-11-20",
    title = "Wicked",
    video = false,
    voteAverage = 7.327,
    voteCount = 1095
)

val DTO_POPULAR_MOVIES = listOf(
    SONIC_THE_HEDGEHOG_3,
    CULPA_TUYA,
    WICKED,
    VENOM_THE_LAST_DANCE,
    MUFASA_THE_LION_KING,
    GLADIATOR_II
)

// >>>>>> MODEL
val MODEL_SONIC_THE_HEDGEHOG_3 = Movie(
    adult = false,
    backdropPath = "/zOpe0eHsq0A2NvNyBbtT6sj53qV.jpg",
    genreIds = listOf(28, 878, 35, 10751),
    id = 939243,
    originalLanguage = "en",
    originalTitle = "Sonic the Hedgehog 3",
    overview = "Sonic, Knuckles, and Tails reunite against a powerful new adversary, Shadow, a mysterious villain with powers unlike anything they have faced before. With their abilities outmatched in every way, Team Sonic must seek out an unlikely alliance in hopes of stopping Shadow and protecting the planet.",
    popularity = 4471.466,
    posterPath = "/d8Ryb8AunYAuycVKDp5HpdWPKgC.jpg",
    releaseDate = "2024-12-19",
    title = "Sonic the Hedgehog 3",
    video = false,
    voteAverage = 7.7,
    voteCount = 397
)

val MODEL_GLADIATOR_II = Movie(
    adult = false,
    backdropPath = "/euYIwmwkmz95mnXvufEmbL6ovhZ.jpg",
    genreIds = listOf(28, 12, 18),
    id = 558449,
    originalLanguage = "en",
    originalTitle = "Gladiator II",
    overview = "Years after witnessing the death of the revered hero Maximus at the hands of his uncle, Lucius is forced to enter the Colosseum after his home is conquered by the tyrannical Emperors who now lead Rome with an iron fist. With rage in his heart and the future of the Empire at stake, Lucius must look to his past to find strength and honor to return the glory of Rome to its people.",
    popularity = 3935.606,
    posterPath = "/2cxhvwyEwRlysAmRH4iodkvo0z5.jpg",
    releaseDate = "2024-11-05",
    title = "Gladiator II",
    video = false,
    voteAverage = 6.778,
    voteCount = 2136
)

val MODEL_CULPA_TUYA = Movie(
    adult = false,
    backdropPath = "/k24eZq5I3jyz4htPkZCRpnUmBzE.jpg",
    genreIds = listOf(10749, 18),
    id = 1156593,
    originalLanguage = "es",
    originalTitle = "Culpa tuya",
    overview = "The love between Noah and Nick seems unwavering despite their parents' attempts to separate them. But his job and her entry into college open up their lives to new relationships that will shake the foundations of both their relationship and the Leister family itself.",
    popularity = 2623.666,
    posterPath = "/1sQA7lfcF9yUyoLYC0e6Zo3jmxE.jpg",
    releaseDate = "2024-12-26",
    title = "Your Fault",
    video = false,
    voteAverage = 7.1,
    voteCount = 750
)

val MODEL_MUFASA_THE_LION_KING = Movie(
    adult = false,
    backdropPath = "/oHPoF0Gzu8xwK4CtdXDaWdcuZxZ.jpg",
    genreIds = listOf(12, 10751, 16),
    id = 762509,
    originalLanguage = "en",
    originalTitle = "Mufasa: The Lion King",
    overview = "Mufasa, a cub lost and alone, meets a sympathetic lion named Taka, the heir to a royal bloodline. The chance meeting sets in motion an expansive journey of a group of misfits searching for their destiny.",
    popularity = 2928.339,
    posterPath = "/iBqXjFkAozQ1z2sfAiWwBimbiJX.jpg",
    releaseDate = "2024-12-18",
    title = "Mufasa: The Lion King",
    video = false,
    voteAverage = 7.5,
    voteCount = 517
)

val MODEL_VENOM_THE_LAST_DANCE = Movie(
    adult = false,
    backdropPath = "/vZG7PrX9HmdgL5qfZRjhJsFYEIA.jpg",
    genreIds = listOf(28, 878, 12),
    id = 912649,
    originalLanguage = "en",
    originalTitle = "Venom: The Last Dance",
    overview = "Eddie and Venom are on the run. Hunted by both of their worlds and with the net closing in, the duo are forced into a devastating decision that will bring the curtains down on Venom and Eddie's last dance.",
    popularity = 3038.094,
    posterPath = "/aosm8NMQ3UyoBVpSxyimorCQykC.jpg",
    releaseDate = "2024-10-22",
    title = "Venom: The Last Dance",
    video = false,
    voteAverage = 6.813,
    voteCount = 2213
)

val MODEL_WICKED = Movie(
    adult = false,
    backdropPath = "/uKb22E0nlzr914bA9KyA5CVCOlV.jpg",
    genreIds = listOf(18, 10749, 14),
    id = 402431,
    originalLanguage = "en",
    originalTitle = "Wicked",
    overview = "In the land of Oz, ostracized and misunderstood green-skinned Elphaba is forced to share a room with the popular aristocrat Glinda at Shiz University, and the two's unlikely friendship is tested as they begin to fulfill their respective destinies as Glinda the Good and the Wicked Witch of the West.",
    popularity = 2274.665,
    posterPath = "/2E1x1qcHqGZcYuYi4PzVZjzg8IV.jpg",
    releaseDate = "2024-11-20",
    title = "Wicked",
    video = false,
    voteAverage = 7.327,
    voteCount = 1095
)

val MODEL_POPULAR_MOVIES = listOf(
    MODEL_SONIC_THE_HEDGEHOG_3,
    MODEL_CULPA_TUYA,
    MODEL_WICKED,
    MODEL_VENOM_THE_LAST_DANCE,
    MODEL_MUFASA_THE_LION_KING,
    MODEL_GLADIATOR_II
)