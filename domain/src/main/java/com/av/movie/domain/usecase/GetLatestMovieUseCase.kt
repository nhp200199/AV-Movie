package com.av.movie.domain.usecase

import com.av.movie.domain.model.Movie
import com.av.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetLatestMovieUseCase(private val repository: MovieRepository) {
    operator fun invoke(): Flow<List<Movie>> = repository.getLatest()
}