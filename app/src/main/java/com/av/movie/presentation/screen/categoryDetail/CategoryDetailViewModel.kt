package com.av.movie.presentation.screen.categoryDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.av.movie.data.model.Movie
import com.av.movie.data.model.ResultData
import com.av.movie.domain.repository.movie.IMoviePreviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CategoryUIState {
    data object Initial: CategoryUIState()
    data object Loading : CategoryUIState()
    data class Success(val movies: List<Movie>) : CategoryUIState()
    data object Error : CategoryUIState()
}

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val moviePreviewRepository: IMoviePreviewRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<CategoryUIState>(CategoryUIState.Initial)
    val uiState = _uiState.asStateFlow()

    fun getCategoryDetail(category: Category) {
        _uiState.value = CategoryUIState.Loading

        viewModelScope.launch {
            val result = when(category) {
                Category.POPULAR -> moviePreviewRepository.getPopularMovies()
                Category.TOP_RATED -> moviePreviewRepository.getTopRatedMovies()
            }

            when(result) {
                is ResultData.Error -> _uiState.value = CategoryUIState.Error
                is ResultData.Success -> _uiState.value = CategoryUIState.Success(result.data)
            }
        }
    }
}