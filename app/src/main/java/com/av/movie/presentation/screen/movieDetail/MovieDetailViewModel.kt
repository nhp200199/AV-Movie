package com.av.movie.presentation.screen.movieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.av.movie.data.model.ResultData
import com.av.movie.domain.usecase.FullDetailMovie
import com.av.movie.domain.usecase.GetFullDetailMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MovieDetailUiState {
    data object Initial : MovieDetailUiState()
    data object Loading : MovieDetailUiState()
    data class Success(val data: FullDetailMovie) : MovieDetailUiState()
    data object Error : MovieDetailUiState()
}

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getFullDetailMovieUseCase: GetFullDetailMovieUseCase
) : ViewModel() {
    private val _detailMovieState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Initial)
    val detailMovieState = _detailMovieState.asStateFlow()

    fun loadMovieDetail(id: Int) {
        _detailMovieState.value = MovieDetailUiState.Loading
        viewModelScope.launch {
            val fullDetailMovieResult = getFullDetailMovieUseCase(id)
            when (fullDetailMovieResult) {
                is ResultData.Error -> _detailMovieState.value = MovieDetailUiState.Error
                is ResultData.Success -> _detailMovieState.value = MovieDetailUiState.Success(fullDetailMovieResult.data)
            }
        }
    }
}