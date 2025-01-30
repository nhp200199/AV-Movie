package com.av.movie.presentation.screen.explore

import androidx.lifecycle.ViewModel
import com.av.movie.presentation.screen.home.Genre
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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


class ExploreViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(SortFilterData())
    val uiState = _uiState.asStateFlow()

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