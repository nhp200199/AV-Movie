package com.av.movie.presentation.screen.detail

import androidx.lifecycle.ViewModel
import com.av.movie.oldClass.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {

}