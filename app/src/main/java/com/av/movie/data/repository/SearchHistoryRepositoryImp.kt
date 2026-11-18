package com.av.movie.data.repository

import android.content.Context
import com.av.movie.domain.repository.SearchHistoryRepository
import com.av.movie.domain.usecase.ManageRecentSearchesUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SearchHistoryRepositoryImp @Inject constructor(
    @ApplicationContext context: Context,
    private val manageRecentSearchesUseCase: ManageRecentSearchesUseCase
) : SearchHistoryRepository {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val mutex = Mutex()
    private val recentSearchesState = MutableStateFlow(readStoredQueries())

    override fun observeRecentSearches(): Flow<List<String>> = recentSearchesState.asStateFlow()

    override suspend fun saveQuery(query: String) {
        mutex.withLock {
            val updated = manageRecentSearchesUseCase.addQuery(recentSearchesState.value, query)
            if (updated == recentSearchesState.value) return
            persistQueries(updated)
            recentSearchesState.value = updated
        }
    }

    override suspend fun removeQuery(query: String) {
        mutex.withLock {
            val updated = manageRecentSearchesUseCase.removeQuery(recentSearchesState.value, query)
            if (updated == recentSearchesState.value) return
            persistQueries(updated)
            recentSearchesState.value = updated
        }
    }

    private fun readStoredQueries(): List<String> {
        val rawJson = prefs.getString(KEY_RECENT_QUERIES, null) ?: return emptyList()
        val decoded = runCatching { Json.decodeFromString<List<String>>(rawJson) }
            .getOrDefault(emptyList())
        return manageRecentSearchesUseCase.normalize(decoded)
    }

    private fun persistQueries(queries: List<String>) {
        prefs.edit()
            .putString(KEY_RECENT_QUERIES, Json.encodeToString(queries))
            .apply()
    }

    companion object {
        private const val PREFS_NAME = "av_movie_search_prefs"
        private const val KEY_RECENT_QUERIES = "recent_search_queries"
    }
}
