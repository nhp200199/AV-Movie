package com.av.movie.oldClass

import kotlinx.coroutines.flow.Flow

class GetLatestMovieUseCase(private val repository: com.av.movie.oldClass.MovieRepository) {
    operator fun invoke(): Flow<List<com.av.movie.oldClass.OldMovie>> = repository.getLatest()
}