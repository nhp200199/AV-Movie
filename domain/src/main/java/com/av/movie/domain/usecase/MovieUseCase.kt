package com.av.movie.domain.usecase

data class MovieUseCase(
    val getMovieUseCase: GetMovieUseCase,
    val getLatestMovieUseCase: GetLatestMovieUseCase
)
