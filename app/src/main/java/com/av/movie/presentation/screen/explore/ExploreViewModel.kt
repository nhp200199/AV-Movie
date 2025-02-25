package com.av.movie.presentation.screen.explore

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.av.movie.data.api.model.Genre
import com.av.movie.data.api.model.Movie
import com.av.movie.data.api.model.ResultData
import com.av.movie.domain.repository.movie.IMoviePreviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

    val genres = searchUiState
        .map {
            when (it) {
                is SearchUiState.Success -> it.movies.map { movie -> movie.genreIds }.flatten().distinct()
                else -> emptyList<Int>()
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val countries = searchUiState
        .map {
            when (it) {
                is SearchUiState.Success -> it.movies.map { movie -> movie.originalLanguage }.distinct()
                else -> emptyList<String>()
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

//    private val _years = searchUiState
//        .map {
//            when (it) {
//                is SearchUiState.Success -> it.movies.map { movie -> movie.releaseDate }.distinct()
//                else -> emptyList<String>()
//            }
//        }

    private val _selectedGenres = MutableStateFlow<List<Int>?>(null)
    private val _selectedCountry = MutableStateFlow<String?>(null)
    private val _selectedSortOption = MutableStateFlow<SortOption?>(null)

    val mergedSortFilterData = combine(
        _selectedGenres,
        _selectedCountry,
        _selectedSortOption
    ) { genres, country, sortOption ->
        SortFilterData(
            genre = genres?.map { Genre(it, it.toString()) },
            country = country,
            sort = sortOption
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SortFilterData.EMPTY
    )

    val filteredSearchUiState = combine(
        searchUiState,
        mergedSortFilterData
    ) { searchUiState, sortFilterData ->
        if (searchUiState !is SearchUiState.Success) return@combine searchUiState

        val value = searchUiState
        val filteredValue = value.movies
            .filter { movie -> movie.genreIds.any { sortFilterData.genre?.contains(Genre(it, it.toString())) ?: true }}
            .filter { movie ->
                sortFilterData.country?.let { it == movie.originalLanguage } ?: true
            }

        val sortFilteredValue = filteredValue.sortedByDescending {
            return@sortedByDescending when (sortFilterData.sort) {
                SortOption.POPULAR -> it.popularity
                SortOption.NEW -> it.popularity
                SortOption.RATING -> it.voteAverage
                null -> null
            }
        }

        return@combine searchUiState.copy(movies = sortFilteredValue)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SearchUiState.Initial
    )

    init {
        viewModelScope.launch {
            genres.collect {
                _selectedGenres.value = null
            }
        }

        viewModelScope.launch {
            countries.collect {
                _selectedCountry.value = null
            }
        }
    }

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
        _selectedSortOption.value = sort
    }

    fun filterByYear(year: Int?) {
        _uiState.value = _uiState.value.copy(year = year)
    }

    fun toggleGenre(id: Int) {
        val currentGenre = _selectedGenres.value
        if (currentGenre == null) {
            _selectedGenres.value = listOf(id)
            return
        }

        _selectedGenres.value = if (currentGenre.contains(id)) currentGenre.minus(id)
            else currentGenre.plus(id)
    }

    fun resetGenreFilter() {
        _selectedGenres.value = null
    }

    fun filterByCountry(country: String?) {
        _selectedCountry.value = country
    }

    fun resetAllSortFilter() {
        _selectedGenres.value = null
        _selectedCountry.value = null
        _selectedSortOption.value = null
    }
}