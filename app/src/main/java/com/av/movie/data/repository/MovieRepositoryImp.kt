package com.av.movie.data.repository

import com.av.movie.data.api.MovieApi
import com.av.movie.data.mapper.Mapper
import com.av.movie.data.model.CastListDTO
import com.av.movie.data.model.GenreDTO
import com.av.movie.data.model.MovieDetailDTO
import com.av.movie.data.model.MoviePreviewDTO
import com.av.movie.data.model.PagingDTO
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.VideoListDTO
import com.av.movie.data.model.map
import com.av.movie.domain.model.Cast
import com.av.movie.domain.model.Genre
import com.av.movie.domain.model.Movie
import com.av.movie.domain.model.MovieDetail
import com.av.movie.domain.model.Video
import com.av.movie.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val movieApi: MovieApi,
    private val moviePreviewMapper: Mapper<MoviePreviewDTO, Movie>,
    private val movieDetailMapper: Mapper<MovieDetailDTO, MovieDetail>,
    private val castMapper: Mapper<CastListDTO, @JvmSuppressWildcards List<Cast>>,
    private val videoMapper: Mapper<VideoListDTO, @JvmSuppressWildcards List<Video>>,
    private val genreMapper: Mapper<GenreDTO, @JvmSuppressWildcards List<Genre>>
) : MovieRepository {

    override suspend fun getNowPlayingMovies(page: Int): ResultData<List<Movie>, String> =
        fetchMovies { movieApi.getNowPlayingMovies(page) }

    override suspend fun getPopularMovies(page: Int): ResultData<List<Movie>, String> =
        fetchMovies { movieApi.getPopularMovies(page) }

    override suspend fun getTopRatedMovies(page: Int): ResultData<List<Movie>, String> =
        fetchMovies { movieApi.getTopRatedMovies(page) }

    override suspend fun getUpcomingMovies(page: Int): ResultData<List<Movie>, String> =
        fetchMovies { movieApi.getUpcomingMovies(page) }

    override suspend fun searchMovie(query: String): ResultData<List<Movie>, String> =
        fetchMovies { movieApi.searchMovie(query) }

    override suspend fun getRecommendationsForMovie(id: Int): ResultData<List<Movie>, String> =
        fetchMovies { movieApi.getRecommendations(id) }

    override suspend fun getMovieDetail(id: Int): ResultData<MovieDetail, String> =
        movieApi.getDetail(id).map(movieDetailMapper::map)

    override suspend fun getCastForMovie(id: Int): ResultData<List<Cast>, String> =
        movieApi.getCasts(id).map(castMapper::map)

    override suspend fun getVideoForMovie(id: Int): ResultData<List<Video>, String> =
        movieApi.getVideosForMovie(id).map(videoMapper::map)

    override suspend fun getMovieGenres(): ResultData<List<Genre>, String> =
        movieApi.getAllGenres().map(genreMapper::map)

    private suspend fun fetchMovies(
        apiCall: suspend () -> ResultData<PagingDTO<MoviePreviewDTO>, String>
    ): ResultData<List<Movie>, String> {
        return apiCall().map { paging ->
            paging.results.map(moviePreviewMapper::map)
        }
    }
}
