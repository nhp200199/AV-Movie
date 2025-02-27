package com.av.movie.domain.usecase

import android.util.Log
import com.av.movie.data.model.CastListDTO
import com.av.movie.data.model.Movie
import com.av.movie.data.model.MovieDetail
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.Video
import com.av.movie.data.model.VideoListDTO
import com.av.movie.domain.repository.movie.IMovieDetailRepository
import com.av.movie.domain.repository.movie.IMoviePreviewRepository
import com.av.movie.domain.repository.movie.cast.IMovieCastRepository
import com.av.movie.domain.repository.movie.video.IVideoRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

data class FullDetailMovie(
    val movieDetail: MovieDetail,
    val casts: CastListDTO,
    val videos: VideoListDTO,
    val recommendations: List<Movie>
)

class GetFullDetailMovieUseCase @Inject constructor(
    private val movieCastRepository: IMovieCastRepository,
    private val videoRepository: IVideoRepository,
    private val moviePreviewRepository: IMoviePreviewRepository,
    private val movieDetailRepository: IMovieDetailRepository
) {
    suspend operator fun invoke(id: Int): ResultData<FullDetailMovie> {
        return coroutineScope {
            val movieDetailResult = async { movieDetailRepository.getDetail(id) }
            val castsResult = async { movieCastRepository.getCastForMovie(id) }
            val videosResult = async { videoRepository.getVideoForMovie(id) }
            val recommendationsResult = async { moviePreviewRepository.getRecommendationsForMovie(id) }

            val movieDetail = movieDetailResult.await()
            val casts = castsResult.await()
            val videos = videosResult.await()
            val recommendations = recommendationsResult.await()

            Log.d("MovieDetail", "videos = $videos")
            Log.d("MovieDetail", "recommendation = $recommendations")
            Log.d("MovieDetail", "detail = $movieDetail")
            Log.d("MovieDetail", "casts = $casts")

            val results = listOf(
                movieDetail,
                casts,
                videos,
                recommendations
            )

            if (results.any { it is ResultData.Error }) {
                return@coroutineScope ResultData.Error(Exception("Error"))
            }

            return@coroutineScope ResultData.Success(
                FullDetailMovie(
                    movieDetail = (movieDetail as ResultData.Success).data,
                    casts = (casts as ResultData.Success).data,
                    videos = (videos as ResultData.Success).data,
                    recommendations = (recommendations as ResultData.Success).data
                )
            )
        }
    }
}