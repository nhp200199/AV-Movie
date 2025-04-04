package com.av.movie.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.av.movie.data.model.Movie
import com.av.movie.data.model.ResultData
import com.av.movie.domain.repository.movie.IMoviePreviewRepository
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val movieRepository: IMoviePreviewRepository
): PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val nextPageNumber = params.key ?: 1
        val response = movieRepository.getPopularMovies(nextPageNumber)

        return when(response) {
            is ResultData.Error -> LoadResult.Error(response.exception)
            is ResultData.Success -> LoadResult.Page(
                data = response.data,
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        }
    }
}