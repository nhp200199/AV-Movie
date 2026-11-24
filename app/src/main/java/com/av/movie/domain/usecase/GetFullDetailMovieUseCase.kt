package com.av.movie.domain.usecase

import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Cast
import com.av.movie.domain.model.Movie
import com.av.movie.domain.model.MovieDetail
import com.av.movie.domain.model.Video
import com.av.movie.domain.repository.MovieRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

data class FullDetailMovie(
    val movieDetail: MovieDetail,
    val casts: List<Cast>,
    val videos: List<Video>,
    val recommendations: List<Movie>
)

class GetFullDetailMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(id: Int): ResultData<FullDetailMovie, String> {
        return coroutineScope {
            val movieDetailResult = async { movieRepository.getMovieDetail(id) }
            val castsResult = async { movieRepository.getCastForMovie(id) }
            val videosResult = async { movieRepository.getVideoForMovie(id) }
            val recommendationsResult = async { movieRepository.getRecommendationsForMovie(id) }

            val movieDetail = movieDetailResult.await()
            val casts = castsResult.await()
            val videos = videosResult.await()
            val recommendations = recommendationsResult.await()

            when (movieDetail) {
                is ResultData.Success -> ResultData.Success(
                    FullDetailMovie(
                        movieDetail = movieDetail.data,
                        casts = (casts as? ResultData.Success)?.data ?: emptyList(),
                        videos = (videos as? ResultData.Success)?.data ?: emptyList(),
                        recommendations = (recommendations as? ResultData.Success)?.data ?: emptyList()
                    )
                )

                is ResultData.ApiError -> ResultData.ApiError(movieDetail.body)
                is ResultData.OperationError -> ResultData.OperationError(movieDetail.exception)
            }
        }
    }
}