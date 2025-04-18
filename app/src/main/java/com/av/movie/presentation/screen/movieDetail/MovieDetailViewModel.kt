package com.av.movie.presentation.screen.movieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.av.movie.data.model.CastListDTO
import com.av.movie.data.model.Movie
import com.av.movie.data.model.MovieDetail
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.VideoListDTO
import com.av.movie.domain.repository.movie.IMoviePreviewRepository
import com.av.movie.domain.usecase.FullDetailMovie
import com.av.movie.domain.usecase.GetFullDetailMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MovieDetailUiState(val data: FullDetailMovie?) {
    data object Initial : MovieDetailUiState(null)
    class Loading(d: FullDetailMovie? = null) : MovieDetailUiState(d)
    class Success(successData: FullDetailMovie) : MovieDetailUiState(successData)
    data object Error : MovieDetailUiState(null)
}

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getFullDetailMovieUseCase: GetFullDetailMovieUseCase,
    private val moviePreviewRepository: IMoviePreviewRepository
) : ViewModel() {
    private val _detailMovieState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Initial)
    val detailMovieState = _detailMovieState.asStateFlow()

    fun loadMovieDetail(id: Int) {
        val cacheMovie = moviePreviewRepository.getMoviePreview(id)?.let {
            convertToFullDetailMovie(it)
        }
        _detailMovieState.value = MovieDetailUiState.Loading(cacheMovie)
        viewModelScope.launch {
            val fullDetailMovieResult = getFullDetailMovieUseCase(id)
            when (fullDetailMovieResult) {
                is ResultData.Success -> _detailMovieState.value = MovieDetailUiState.Success(fullDetailMovieResult.data)
                else -> _detailMovieState.value = MovieDetailUiState.Error
            }
        }
    }
    
    private fun convertToFullDetailMovie(movie: Movie): FullDetailMovie = 
        FullDetailMovie(
            movieDetail = MovieDetail(
                adult = movie.adult,
                backdropPath = movie.backdropPath,
                genres = emptyList(),
                id = movie.id,
                originalLanguage = movie.originalLanguage,
                originalTitle = movie.originalTitle,
                overview = movie.overview,
                popularity = movie.popularity,
                posterPath = movie.posterPath,
                releaseDate = movie.releaseDate,
                title = movie.title,
                video = false,
                voteAverage = movie.voteAverage,
                voteCount = movie.voteCount,
                duration = 0,
                isFavorite = false
            ),
            casts = CastListDTO.DEFAULT,
            videos = VideoListDTO.DEFAULT,
            recommendations = emptyList()
        )
}