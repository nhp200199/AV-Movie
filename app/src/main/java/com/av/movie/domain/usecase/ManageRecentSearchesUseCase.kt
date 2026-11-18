package com.av.movie.domain.usecase

import javax.inject.Inject

/**
 * Enforces the recent search history policy: trimmed non-blank entries,
 * case-insensitive deduplication with newest first, and a 5-item cap.
 */
class ManageRecentSearchesUseCase @Inject constructor() {

    fun addQuery(
        current: List<String>,
        rawQuery: String,
        maxSize: Int = MAX_RECENT_SEARCHES
    ): List<String> {
        val trimmed = rawQuery.trim()
        if (trimmed.isEmpty()) return normalize(current, maxSize)

        val remaining = current
            .map { it.trim() }
            .filter { it.isNotEmpty() && !it.equals(trimmed, ignoreCase = true) }

        return (listOf(trimmed) + remaining).take(maxSize)
    }

    fun removeQuery(
        current: List<String>,
        target: String
    ): List<String> {
        val trimmedTarget = target.trim()
        if (trimmedTarget.isEmpty()) return current
        return current.filterNot { it.trim().equals(trimmedTarget, ignoreCase = true) }
    }

    fun normalize(
        queries: List<String>,
        maxSize: Int = MAX_RECENT_SEARCHES
    ): List<String> {
        val seen = LinkedHashSet<String>()
        val result = ArrayList<String>(maxSize)
        for (item in queries) {
            val trimmed = item.trim()
            if (trimmed.isEmpty()) continue
            val key = trimmed.lowercase()
            if (seen.add(key)) {
                result.add(trimmed)
                if (result.size >= maxSize) break
            }
        }
        return result
    }

    companion object {
        const val MAX_RECENT_SEARCHES = 5
    }
}
