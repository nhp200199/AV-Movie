package com.av.movie.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.av.movie.domain.model.Movie
import com.av.movie.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
): ViewModel() {

    private val _movieDetail: MutableStateFlow<Movie?> = MutableStateFlow(null)
    val movieDetail: StateFlow<Movie?> = _movieDetail

//    val latestMovie: Flow<List<Movie>> = movieUseCase.getLatestMovieUseCase.invoke()

    fun getMovie(movieId: Int) {
        viewModelScope.launch {
//            movieUseCase.getMovieUseCase.invoke(movieId).collectLatest {
//                _movieDetail.emit(it)
//            }
        }
    }
}