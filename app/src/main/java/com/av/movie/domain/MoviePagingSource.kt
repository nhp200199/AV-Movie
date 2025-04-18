package com.av.movie.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.av.movie.data.model.Movie
import com.av.movie.data.model.ResultData
import com.av.movie.domain.repository.movie.IMoviePreviewRepository
import com.av.movie.presentation.screen.categoryDetail.Category

class MoviePagingSource(
    private val movieRepository: IMoviePreviewRepository,
    private val _category: Category = Category.POPULAR
): PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val nextPageNumber = params.key ?: 1

        val response = when (_category) {
            Category.POPULAR -> movieRepository.getPopularMovies(nextPageNumber)
            Category.TOP_RATED -> movieRepository.getTopRatedMovies(nextPageNumber)
        }

        return when(response) {
            is ResultData.ApiError -> LoadResult.Error(Exception("API ERROR"))
            is ResultData.OperationError -> LoadResult.Error(response.exception)
            is ResultData.Success -> LoadResult.Page(
                data = response.data,
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        }
    }
}