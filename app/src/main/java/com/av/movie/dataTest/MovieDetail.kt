package com.av.movie.dataTest

import com.av.movie.data.model.Genre
import com.av.movie.data.model.MovieDetail

val MODEL_MOVIE_DETAIL = MovieDetail(
    adult = false,
    backdropPath = "/cmOWlfDvBSMyLaeZ85M87GmkH8v.jpg",
    genres = listOf(
        Genre(id = 28, name = "Action"),
        Genre(id = 12, name = "Adventure"),
        Genre(id = 18, name = "Drama"),
        Genre(id = 53, name = "Thriller")
    ),
    id = 2133,
    originalLanguage = "en",
    originalTitle = "The Perfect Storm",
    overview = "In October 1991, a confluence of weather conditions combined to form a killer storm in the North Atlantic. Caught in the storm was the sword-fishing boat Andrea Gail.",
    popularity = 17.756,
    posterPath = "/vJPoxqgpfFNbGi0HyoNsjFeLCio.jpg",
    releaseDate = "2000-06-29",
    title = "The Perfect Storm",
    video = false,
    voteAverage = 6.474,
    voteCount = 2349,
    duration = 130
)