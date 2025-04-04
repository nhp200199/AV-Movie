package com.av.movie.dataTest

import com.av.movie.data.model.VideoDTO
import com.av.movie.data.model.VideoListDTO

val video1 = VideoDTO(
    id = "67771f4515521f83d9667cb8",
    name = "20 Years of Making a Sequel - Exclusive Behind the Scenes",
    key = "_ZBYN01c7OM",
    site = "YouTube",
    size = 1080,
    type = "Behind the Scenes",
    official = true,
    publishedAt = "2024-12-31T15:59:55.000Z",
    iso31661 = "en",
    iso6391 = "en"
)

val video2 = VideoDTO(
    id = "676a46d21bb91310c064b1e0",
    name = "Screenwriting of Gladiator II",
    key = "gGmaZCZz48g",
    site = "YouTube",
    size = 1080,
    type = "Behind the Scenes",
    official = true,
    publishedAt = "2024-12-23T18:00:06.000Z",
    iso31661 = "en",
    iso6391 = "en"
)

val video3 = VideoDTO(
    id = "676a46c99348daa54ba9e899",
    name = "Cinematography of Gladiator II",
    key = "L5HZw0tGAv8",
    site = "YouTube",
    size = 1080,
    type = "Behind the Scenes",
    official = true,
    publishedAt = "2024-12-23T18:00:02.000Z",
    iso31661 = "en",
    iso6391 = "en"
)

val video4 = VideoDTO(
    id = "676a3b8bfdaa7c2549a9d803",
    name = "Extended Clip - The Gateway to Rome",
    key = "eEFRXtV5LTE",
    site = "YouTube",
    size = 1080,
    type = "Clip",
    official = true,
    publishedAt = "2024-12-23T12:59:51.000Z",
    iso31661 = "en",
    iso6391 = "en"
)

val video5 = VideoDTO(
    id = "6766b63641db2b4b9c74861a",
    name = "Costume Design of Gladiator II",
    key = "ICvbuugHjUg",
    site = "YouTube",
    size = 1080,
    type = "Behind the Scenes",
    official = true,
    publishedAt = "2024-12-19T20:08:52.000Z",
    iso31661 = "en",
    iso6391 = "en"
)

val video6 = VideoDTO(
    id = "6764d3f68f10c2746b90bf7d",
    name = "A Conversation with Director Ridley Scott and Christopher Nolan",
    key = "uwd0uX0h1Mc",
    site = "YouTube",
    size = 1080,
    type = "Featurette",
    official = true,
    publishedAt = "2024-12-19T20:05:30.000Z",
    iso31661 = "en",
    iso6391 = "en"
)

val video7 = VideoDTO(
    id = "6752262a0e8c237986a8f72f",
    name = "It's time to enter the arena",
    key = "abP1EYHvGvc",
    site = "YouTube",
    size = 1080,
    type = "Teaser",
    official = true,
    publishedAt = "2024-12-05T01:00:02.000Z",
    iso31661 = "en",
    iso6391 = "en"
)

val video8 = VideoDTO(
    id = "675326b4802bad16091abcfc",
    name = "'Gladiator II' With Paul Mescal, Denzel Washington, Ridley Scott, and More | Academy Conversations",
    key = "lLl6Bx8coJ4",
    site = "YouTube",
    size = 1080,
    type = "Featurette",
    official = true,
    publishedAt = "2024-12-04T16:00:06.000Z",
    iso31661 = "en",
    iso6391 = "en"
)

val MODEL_GLADIATOR_VIDEO = VideoListDTO(
    id = 2,
    results = listOf(
        video1,
        video2,
        video3,
        video4,
        video5,
        video6,
        video7,
        video8,
    )
)