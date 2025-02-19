package com.av.movie.oldClass

import kotlinx.coroutines.flow.Flow

class GetMovieUseCase(private val repository: com.av.movie.oldClass.MovieRepository) {
    operator fun invoke(movieId: Int): Flow<com.av.movie.oldClass.OldMovie> = repository.getMovie(movieId)
}