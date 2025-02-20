package com.av.movie.presentation.screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.av.movie.data.api.model.Movie
import com.av.movie.data.api.model.ResultData
import com.av.movie.domain.repository.movie.IMoviePreviewRepository
import com.av.movie.data.api.model.Genre
import com.av.movie.domain.repository.movie.IGenreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeData(
    val nowPlayingMovies: List<Movie> = emptyList(),
    val popularMovies: List<Movie> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList()
)

sealed class HomeUiState {
    data object Initial : HomeUiState()
    data object Loading : HomeUiState()
    data class Success(val data: HomeData) : HomeUiState()
    data object Error : HomeUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieListRepository: IMoviePreviewRepository,
    private val genreRepository: IGenreRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Initial)
    val uiState = _uiState
        .map {
            if (it !is HomeUiState.Success) return@map it

            val currentHomeUi = it.data
            val simplifiedPlayingMovies = currentHomeUi.nowPlayingMovies.take(5)
            return@map it.copy(data = currentHomeUi.copy(nowPlayingMovies = simplifiedPlayingMovies))
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            HomeUiState.Initial
        )

    fun getInitialData() {
        _uiState.value = HomeUiState.Loading

        val popularMoviesDeferred = viewModelScope.async {
            movieListRepository.getPopularMovies()
        }
        val nowPlayingMoviesDeferred = viewModelScope.async { movieListRepository.getNowPlayingMovies() }
        val genreDeferred = viewModelScope.async { genreRepository.getAllGenres() }
        val topRatedMoviesDeferred = viewModelScope.async { movieListRepository.getTopRatedMovies() }

        viewModelScope.launch {
            val nowPlayingMoviesResult = nowPlayingMoviesDeferred.await()
            val popularMoviesResult = popularMoviesDeferred.await()
            val genreResult = genreDeferred.await()
            val topRatedMoviesResult = topRatedMoviesDeferred.await()

            val listOfResults = listOf(
                nowPlayingMoviesResult,
                popularMoviesResult,
                genreResult,
                topRatedMoviesResult
            )

            if (listOfResults.any { it is ResultData.Error }) {
                _uiState.value = HomeUiState.Error
                return@launch
            }

            val nowPlayingMovies = (nowPlayingMoviesResult as ResultData.Success).data
            val popularMovies = (popularMoviesResult as ResultData.Success).data
            val genres = (genreResult as ResultData.Success).data
            val topRatedMovies = (topRatedMoviesResult as ResultData.Success).data

            _uiState.value = HomeUiState.Success(
                HomeData(
                    nowPlayingMovies = nowPlayingMovies,
                    popularMovies = popularMovies,
                    genres = genres.genres,
                    topRatedMovies = topRatedMovies
                )
            )
        }
    }
}