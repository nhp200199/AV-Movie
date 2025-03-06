package com.av.movie.presentation.navigation

import com.av.movie.presentation.screen.categoryDetail.Category
import kotlinx.serialization.Serializable

@Serializable
object Authentication

@Serializable
object Onboarding

@Serializable
object Main

@Serializable
object Home
@Serializable
object Explore
@Serializable
object ExploreFilter
@Serializable
object GenreFilter
@Serializable
object YearFilter
@Serializable
object CountryFilter
@Serializable
object Profile
@Serializable
object Favourites
@Serializable
data class CategoryDetail(val category: Category)
@Serializable
object NestedHome
@Serializable
object ExploreNested

@Serializable
data class MovieDetail(val id: Int)

