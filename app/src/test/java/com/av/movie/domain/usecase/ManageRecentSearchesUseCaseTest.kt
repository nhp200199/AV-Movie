package com.av.movie.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Test

class ManageRecentSearchesUseCaseTest {

    private val useCase = ManageRecentSearchesUseCase()

    @Test
    fun addQuery_prependsNewestToTop() {
        val result = useCase.addQuery(listOf("Avatar", "Inception"), "Interstellar")
        assertEquals(listOf("Interstellar", "Avatar", "Inception"), result)
    }

    @Test
    fun addQuery_ignoresBlankAndTrimsWhitespace() {
        val initial = listOf("Avatar")
        assertEquals(initial, useCase.addQuery(initial, "   "))
        assertEquals(listOf("Dune", "Avatar"), useCase.addQuery(initial, "  Dune  "))
    }

    @Test
    fun addQuery_movesExistingKeywordToTopWithoutDuplicating() {
        val initial = listOf("Interstellar", "Avatar", "Dune")
        val result = useCase.addQuery(initial, "avatar")
        assertEquals(listOf("avatar", "Interstellar", "Dune"), result)
    }

    @Test
    fun addQuery_evictsOldestWhenExceedingFiveKeywords() {
        val five = listOf("One", "Two", "Three", "Four", "Five")
        val result = useCase.addQuery(five, "Six")
        assertEquals(listOf("Six", "One", "Two", "Three", "Four"), result)
        assertEquals(5, result.size)
    }

    @Test
    fun removeQuery_removesMatchingEntryCaseInsensitively() {
        val initial = listOf("Avatar", "Dune", "Matrix")
        val result = useCase.removeQuery(initial, "dune")
        assertEquals(listOf("Avatar", "Matrix"), result)
    }
}
