package com.av.movie.presentation.screen.explore

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.av.movie.data.api.model.Genre
import com.av.movie.data.api.model.Movie
import com.av.movie.data.api.model.ResultData
import com.av.movie.domain.repository.movie.IMoviePreviewRepository
import com.av.movie.oldClass.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SortFilterData(
    val sort: SortOption? = null,
    val year: Int? = null,
    val genre: List<Genre>? = null,
    val country: String? = null
) {
    companion object {
        val EMPTY = SortFilterData()
    }
}

sealed class SearchUiState {
    data object Initial: SearchUiState()
    data object Loading: SearchUiState()
    data class Success(val movies: List<Movie>): SearchUiState()
    data object Error: SearchUiState()
}

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val movieRepository: IMoviePreviewRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(SortFilterData.EMPTY)
    val uiState = _uiState.asStateFlow()

    private val _searchUiState = MutableStateFlow<SearchUiState>(SearchUiState.Initial)
    val searchUiState = _searchUiState.asStateFlow()

    fun onSearchMovie(query: String) {
        Log.d("PhucNguyen", "onSearchMovie()")
        _searchUiState.value = SearchUiState.Loading
        viewModelScope.launch {
            val result = movieRepository.searchMovie(query)
            Log.d("PhucNguyen", "onSearchMovie() - result = ${result.toString()}")
            when (result) {
                is ResultData.Success -> {
                    _searchUiState.value = SearchUiState.Success(result.data)
                }
                is ResultData.Error -> {
                    _searchUiState.value = SearchUiState.Error
                }
            }
        }
    }

    fun sortBy(sort: SortOption) {
        _uiState.value = _uiState.value.copy(sort = sort)
    }

    fun filterByYear(year: Int?) {
        _uiState.value = _uiState.value.copy(year = year)
    }

    fun toggleGenre(genre: Genre) {
        val currentGenre = _uiState.value.genre
        if (currentGenre == null) {
            _uiState.value = _uiState.value.copy(genre = listOf(genre))
            return
        }

        _uiState.value = _uiState.value.copy(
            genre = if (currentGenre.contains(genre)) currentGenre.minus(genre)
                else currentGenre.plus(genre)
        )
    }

    fun resetGenreFilter() {
        _uiState.value = _uiState.value.copy(genre = null)
    }

    fun filterByCountry(country: String?) {
        _uiState.value = _uiState.value.copy(country = country)
    }

    fun resetAllSortFilter() {
        _uiState.value = SortFilterData.EMPTY
    }
}