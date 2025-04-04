package com.av.movie.presentation.screen.categoryDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.av.movie.data.model.Movie
import com.av.movie.domain.MoviePagingSource
import com.av.movie.domain.repository.movie.IMoviePreviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

sealed class CategoryUIState {
    data object Initial: CategoryUIState()
    data object Loading : CategoryUIState()
    data class Success(val movies: List<Movie>) : CategoryUIState()
    data object Error : CategoryUIState()
}

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val moviePreviewRepository: IMoviePreviewRepository,
): ViewModel() {
    private val _category = savedStateHandle.getStateFlow<Category?>("category", null)

    val flow = _category.filterNotNull()
        .flatMapLatest {
            Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = { MoviePagingSource(moviePreviewRepository, it) }
            )
                .flow
                .cachedIn(viewModelScope)
        }
}