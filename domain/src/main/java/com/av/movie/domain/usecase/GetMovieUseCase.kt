package com.av.movie.domain.usecase

import com.av.movie.domain.model.OldMovie
import com.av.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetMovieUseCase(private val repository: MovieRepository) {
    operator fun invoke(movieId: Int): Flow<OldMovie> = repository.getMovie(movieId)
}