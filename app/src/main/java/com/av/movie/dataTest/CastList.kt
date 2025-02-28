package com.av.movie.dataTest

import com.av.movie.data.model.CastDTO
import com.av.movie.data.model.CastListDTO

val MODEL_CASTS = CastListDTO(
    id = 2133,
    cast = listOf(
        CastDTO(
            adult = false,
            gender = 2,
            id = 1461,
            knownForDepartment = "Acting",
            name = "George Clooney",
            originalName = "George Clooney",
            popularity = 22.472,
            profilePath = "/4s3wI0bqOP7K3hhcmKqV6m3GYiQ.jpg",
            castId = 16,
            character = "Capt. Billy Tyne",
            creditId = "52fe4339c3a36847f8043acb",
            order = 0
        ),
        CastDTO(
            adult = false,
            gender = 2,
            id = 13240,
            knownForDepartment = "Acting",
            name = "Mark Wahlberg",
            originalName = "Mark Wahlberg",
            popularity = 38.098,
            profilePath = "/bTEFpaWd7A6AZVWOqKKBWzKEUe8.jpg",
            castId = 17,
            character = "Bobby Shatford",
            creditId = "52fe4339c3a36847f8043acf",
            order = 1
        ),
        CastDTO(
            adult = false,
            gender = 1,
            id = 2882,
            knownForDepartment = "Acting",
            name = "Diane Lane",
            originalName = "Diane Lane",
            popularity = 21.08,
            profilePath = "/o9pPS52JrleScO4Qq6q1nYlaxWz.jpg",
            castId = 18,
            character = "Christina \"Chris\" Cotter",
            creditId = "52fe4339c3a36847f8043ad3",
            order = 2
        ),
        CastDTO(
            adult = false,
            gender = 2,
            id = 4764,
            knownForDepartment = "Acting",
            name = "John C. Reilly",
            originalName = "John C. Reilly",
            popularity = 7.959,
            profilePath = "/iDQ8w4qcxU3le5ZWoCKpHs1QNok.jpg",
            castId = 19,
            character = "Dale \"Murp\" Murphy",
            creditId = "52fe4339c3a36847f8043ad7",
            order = 3
        ),
        CastDTO(
            adult = false,
            gender = 2,
            id = 886,
            knownForDepartment = "Acting",
            name = "William Fichtner",
            originalName = "William Fichtner",
            popularity = 16.058,
            profilePath = "/wWtJOoaNAwPparpzMNuRBjvxwEf.jpg",
            castId = 20,
            character = "David \"Sully\" Sullivan",
            creditId = "52fe4339c3a36847f8043adb",
            order = 4
        )
    )
)

fun getCastProfilePath(path: String?) : String
    = if (path == null) "" else "https://media.themoviedb.org/t/p/w138_and_h175_face/$path"