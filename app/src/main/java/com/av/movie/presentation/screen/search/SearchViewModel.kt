package com.av.movie.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.av.movie.data.model.ResultData
import com.av.movie.dataTest.ALL_GENRES
import com.av.movie.domain.model.AvailableSearchFilters
import com.av.movie.domain.model.SearchMovieFilter
import com.av.movie.domain.repository.MovieRepository
import com.av.movie.domain.repository.SearchHistoryRepository
import com.av.movie.domain.usecase.FilterAndSortSearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val recentSearches: List<String> = emptyList(),
    val resultState: SearchResultState = SearchResultState.Idle,
    val activeFilter: SearchMovieFilter = SearchMovieFilter.EMPTY,
    val availableFilters: AvailableSearchFilters = AvailableSearchFilters.EMPTY,
    val activeFilterChips: List<ActiveFilterChip> = emptyList(),
    val showFilterIcon: Boolean = false,
    val filterRevision: Int = 0
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val searchHistoryRepository: SearchHistoryRepository,
    private val filterAndSortSearchMoviesUseCase: FilterAndSortSearchMoviesUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _rawSearchState = MutableStateFlow<SearchResultState>(SearchResultState.Idle)
    private val _activeFilter = MutableStateFlow(SearchMovieFilter.EMPTY)
    private val _filterRevision = MutableStateFlow(0)
    private val _genreNameById = MutableStateFlow(ALL_GENRES.associate { it.id to it.name })

    private var debounceJob: Job? = null
    private var searchJob: Job? = null
    private var lastCompletedQuery: String? = null

    val uiState: StateFlow<SearchUiState> = combine(
        searchHistoryRepository.observeRecentSearches(),
        _rawSearchState,
        _activeFilter,
        _filterRevision,
        _genreNameById
    ) { recentSearches, rawState, filter, filterRevision, genreMap ->
        val availableFilters = when (rawState) {
            is SearchResultState.Success -> filterAndSortSearchMoviesUseCase.extractAvailableFilters(
                movies = rawState.movies,
                genreNameById = genreMap
            )
            else -> AvailableSearchFilters.EMPTY
        }

        val filteredResultState = when (rawState) {
            is SearchResultState.Success -> SearchResultState.Success(
                filterAndSortSearchMoviesUseCase.applyFilterAndSort(rawState.movies, filter)
            )
            else -> rawState
        }

        val chips = buildActiveFilterChips(filter, availableFilters)
        val showFilter = rawState is SearchResultState.Success &&
            filteredResultState is SearchResultState.Success &&
            shouldShowFilterIcon(
                rawMovieCount = rawState.movies.size,
                displayedMovieCount = filteredResultState.movies.size,
                hasActiveFilter = !filter.isEmpty
            )

        SearchUiState(
            recentSearches = recentSearches,
            resultState = filteredResultState,
            activeFilter = filter,
            availableFilters = availableFilters,
            activeFilterChips = chips,
            showFilterIcon = showFilter,
            filterRevision = filterRevision
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = SearchUiState()
    )

    init {
        loadGenres()
    }

    private fun loadGenres() {
        viewModelScope.launch {
            val result = movieRepository.getMovieGenres()
            if (result is ResultData.Success && result.data.isNotEmpty()) {
                _genreNameById.value = _genreNameById.value + result.data.associate { it.id to it.name }
            }
        }
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
        debounceJob?.cancel()

        val trimmed = newQuery.trim()
        if (trimmed.isEmpty()) {
            searchJob?.cancel()
            lastCompletedQuery = null
            _activeFilter.value = SearchMovieFilter.EMPTY
            _filterRevision.value = 0
            _rawSearchState.value = SearchResultState.Idle
            return
        }

        debounceJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_MS)
            executeSearch(trimmed, forceRefresh = false)
        }
    }

    fun onSearchSubmit() {
        val trimmed = _query.value.trim()
        if (trimmed.isEmpty()) return

        debounceJob?.cancel()
        viewModelScope.launch {
            searchHistoryRepository.saveQuery(trimmed)
        }
        executeSearch(trimmed, forceRefresh = false)
    }

    fun onRecentSearchClick(keyword: String) {
        val trimmed = keyword.trim()
        if (trimmed.isEmpty()) return

        debounceJob?.cancel()
        _query.value = trimmed
        viewModelScope.launch {
            searchHistoryRepository.saveQuery(trimmed)
        }
        executeSearch(trimmed, forceRefresh = true)
    }

    fun onRemoveRecentSearch(keyword: String) {
        viewModelScope.launch {
            searchHistoryRepository.removeQuery(keyword)
        }
    }

    fun onClearQuery() {
        onQueryChange("")
    }

    fun onMovieClick() {
        val trimmed = _query.value.trim()
        if (trimmed.isEmpty()) return
        viewModelScope.launch {
            searchHistoryRepository.saveQuery(trimmed)
        }
    }

    fun onRetry() {
        val trimmed = _query.value.trim()
        if (trimmed.isEmpty()) return
        debounceJob?.cancel()
        executeSearch(trimmed, forceRefresh = true)
    }

    fun onApplyFilter(filter: SearchMovieFilter) {
        if (filter == _activeFilter.value) return
        _activeFilter.value = filter
        _filterRevision.value += 1
    }

    fun onRemoveFilterChip(chip: ActiveFilterChip) {
        val updated = removeFilterChip(_activeFilter.value, chip)
        if (updated == _activeFilter.value) return
        _activeFilter.value = updated
        _filterRevision.value += 1
    }

    private fun executeSearch(query: String, forceRefresh: Boolean) {
        if (!forceRefresh && query == lastCompletedQuery && _rawSearchState.value is SearchResultState.Success) {
            return
        }

        searchJob?.cancel()
        _activeFilter.value = SearchMovieFilter.EMPTY
        _filterRevision.value = 0
        _rawSearchState.value = SearchResultState.Loading

        searchJob = viewModelScope.launch {
            when (val result = movieRepository.searchMovie(query)) {
                is ResultData.Success -> {
                    lastCompletedQuery = query
                    _rawSearchState.value = SearchResultState.Success(result.data)
                }
                else -> {
                    lastCompletedQuery = null
                    _rawSearchState.value = SearchResultState.Error
                }
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_MS = 500L
    }
}
