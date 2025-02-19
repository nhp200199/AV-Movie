package com.av.movie.oldClass

data class MovieUseCase(
    val getMovieUseCase: GetMovieUseCase,
    val getLatestMovieUseCase: GetLatestMovieUseCase
)
