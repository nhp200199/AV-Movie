package com.av.movie.presentation.screen.movieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.av.movie.data.model.ResultData
import com.av.movie.domain.model.Movie
import com.av.movie.domain.usecase.FullDetailMovie
import com.av.movie.domain.usecase.GetFullDetailMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MovieDetailUiState(val data: FullDetailMovie?) {
    data object Initial : MovieDetailUiState(null)
    class Loading(d: FullDetailMovie? = null) : MovieDetailUiState(d)
    class Success(successData: FullDetailMovie) : MovieDetailUiState(successData)
    class Error(d: FullDetailMovie? = null) : MovieDetailUiState(d)
}

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getFullDetailMovieUseCase: GetFullDetailMovieUseCase
) : ViewModel() {

    private val _detailMovieState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Initial)
    val detailMovieState = _detailMovieState.asStateFlow()

    private var loadedMovieId: Int? = null
    private var currentMovie: Movie? = null
    private var savedOverride: Boolean? = null
    private var loadJob: Job? = null

    fun loadMovieDetail(movie: Movie, forceRefresh: Boolean = false) {
        if (!forceRefresh && loadedMovieId == movie.id && _detailMovieState.value is MovieDetailUiState.Success) {
            return
        }

        if (loadedMovieId != movie.id) {
            savedOverride = null
        }
        loadedMovieId = movie.id
        currentMovie = movie

        val previewData = applySavedOverride(movie.toPreviewFullDetail())
        _detailMovieState.value = MovieDetailUiState.Loading(previewData)

        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            when (val result = getFullDetailMovieUseCase(movie.id)) {
                is ResultData.Success -> {
                    val fetched = result.data
                    val mergedDetail = fetched.movieDetail.copy(
                        genres = fetched.movieDetail.genres.ifEmpty { previewData.movieDetail.genres },
                        overview = fetched.movieDetail.overview.ifBlank { previewData.movieDetail.overview },
                        posterPath = fetched.movieDetail.posterPath.ifBlank { previewData.movieDetail.posterPath },
                        backdropPath = fetched.movieDetail.backdropPath.ifBlank { previewData.movieDetail.backdropPath },
                        isFavorite = savedOverride ?: fetched.movieDetail.isFavorite
                    )
                    _detailMovieState.value = MovieDetailUiState.Success(
                        fetched.copy(movieDetail = mergedDetail)
                    )
                }

                else -> {
                    _detailMovieState.value = MovieDetailUiState.Error(
                        applySavedOverride(previewData)
                    )
                }
            }
        }
    }

    fun retry() {
        currentMovie?.let { loadMovieDetail(it, forceRefresh = true) }
    }

    fun toggleSave() {
        val currentData = _detailMovieState.value.data ?: return
        val nextSaved = !(savedOverride ?: currentData.movieDetail.isFavorite)
        savedOverride = nextSaved

        val updatedData = currentData.copy(
            movieDetail = currentData.movieDetail.copy(isFavorite = nextSaved)
        )
        _detailMovieState.value = when (_detailMovieState.value) {
            is MovieDetailUiState.Success -> MovieDetailUiState.Success(updatedData)
            is MovieDetailUiState.Loading -> MovieDetailUiState.Loading(updatedData)
            is MovieDetailUiState.Error -> MovieDetailUiState.Error(updatedData)
            MovieDetailUiState.Initial -> MovieDetailUiState.Initial
        }
    }

    private fun applySavedOverride(fullDetail: FullDetailMovie): FullDetailMovie {
        val override = savedOverride ?: return fullDetail
        return fullDetail.copy(
            movieDetail = fullDetail.movieDetail.copy(isFavorite = override)
        )
    }
}
