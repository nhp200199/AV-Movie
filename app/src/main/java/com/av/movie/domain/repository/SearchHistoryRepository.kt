package com.av.movie.domain.repository

import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    fun observeRecentSearches(): Flow<List<String>>
    suspend fun saveQuery(query: String)
    suspend fun removeQuery(query: String)
}
