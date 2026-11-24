package com.av.movie.data.repository

import com.av.movie.data.api.MovieApi
import com.av.movie.data.mapper.CastDTO2Cast
import com.av.movie.data.mapper.GenreDTO2Genre
import com.av.movie.data.mapper.MovieDetailDTO2Movie
import com.av.movie.data.mapper.MoviePreviewDTO2Movie
import com.av.movie.data.mapper.VideoDTO2Video
import com.av.movie.data.model.CastListDTO
import com.av.movie.data.model.GenreDTO
import com.av.movie.data.model.GenreItemDTO
import com.av.movie.data.model.MovieDetailDTO
import com.av.movie.data.model.MoviePreviewDTO
import com.av.movie.data.model.PagingDTO
import com.av.movie.data.model.ProductionCountry
import com.av.movie.data.model.ResultData
import com.av.movie.data.model.VideoListDTO
import com.av.movie.domain.repository.MovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MovieRepositoryImpTest {

    @Test
    fun movieRepository_doesNotExposeMoviePreviewCacheMethod() {
        val methodNames = MovieRepository::class.java.declaredMethods.map { it.name }
        assertFalse(methodNames.contains("getMoviePreview"))
    }

    @Test
    fun movieRepositoryImp_hasNoInMemoryPreviewCacheFieldAndMapsListCallsDirectly() = runBlocking {
        val repository = createRepository()

        val hasPreviewCacheField = MovieRepositoryImp::class.java.declaredFields.any {
            it.name.contains("moviePreviews", ignoreCase = true)
        }
        assertFalse(hasPreviewCacheField)

        val popularResult = repository.getPopularMovies(page = 1)
        assertTrue(popularResult is ResultData.Success)
        assertEquals(listOf(42), (popularResult as ResultData.Success).data.map { it.id })
    }

    @Test
    fun getMovieDetail_mapsCountryFromProductionCountriesThenOriginCountry() = runBlocking {
        val repositoryWithProductionCountry = createRepository(
            detailDto = sampleDetailDto(
                productionCountries = listOf(ProductionCountry(iso31661 = "US", name = "United States of America")),
                originCountry = listOf("US"),
                originalLanguage = "en"
            )
        )
        val detail1 = (repositoryWithProductionCountry.getMovieDetail(1) as ResultData.Success).data
        assertEquals("United States of America", detail1.country)

        val repositoryWithOriginCountryFallback = createRepository(
            detailDto = sampleDetailDto(
                productionCountries = emptyList(),
                originCountry = listOf("KR"),
                originalLanguage = "ko"
            )
        )
        val detail2 = (repositoryWithOriginCountryFallback.getMovieDetail(1) as ResultData.Success).data
        assertEquals("KR", detail2.country)
    }

    private fun createRepository(
        detailDto: MovieDetailDTO = sampleDetailDto()
    ): MovieRepositoryImp {
        val fakeApi = object : MovieApi {
            override suspend fun getNowPlayingMovies(page: Int) = ResultData.Success(samplePagingDto())
            override suspend fun getPopularMovies(page: Int) = ResultData.Success(samplePagingDto())
            override suspend fun getTopRatedMovies(page: Int) = ResultData.Success(samplePagingDto())
            override suspend fun getUpcomingMovies(page: Int) = ResultData.Success(samplePagingDto())
            override suspend fun searchMovie(query: String) = ResultData.Success(samplePagingDto())
            override suspend fun getRecommendations(id: Int) = ResultData.Success(samplePagingDto())
            override suspend fun getDetail(id: Int) = ResultData.Success(detailDto)
            override suspend fun getCasts(id: Int) = ResultData.Success(CastListDTO.DEFAULT)
            override suspend fun getVideosForMovie(id: Int) = ResultData.Success(VideoListDTO.DEFAULT)
            override suspend fun getAllGenres() = ResultData.Success(GenreDTO(emptyList()))
        }
        return MovieRepositoryImp(
            movieApi = fakeApi,
            moviePreviewMapper = MoviePreviewDTO2Movie(),
            movieDetailMapper = MovieDetailDTO2Movie(),
            castMapper = CastDTO2Cast(),
            videoMapper = VideoDTO2Video(),
            genreMapper = GenreDTO2Genre()
        )
    }

    private fun samplePagingDto() = PagingDTO(
        page = 1,
        results = listOf(
            MoviePreviewDTO(
                id = 42,
                adult = false,
                backdropPath = "/backdrop.jpg",
                genreIds = listOf(28),
                originalLanguage = "en",
                originalTitle = "Sample Movie",
                overview = "Overview",
                popularity = 100.0,
                posterPath = "/poster.jpg",
                releaseDate = "2024-01-01",
                title = "Sample Movie",
                video = false,
                voteAverage = 8.4,
                voteCount = 200
            )
        ),
        totalPages = 1,
        totalResults = 1
    )

    private fun sampleDetailDto(
        productionCountries: List<ProductionCountry> = listOf(ProductionCountry("US", "United States")),
        originCountry: List<String> = listOf("US"),
        originalLanguage: String = "en"
    ) = MovieDetailDTO(
        adult = false,
        backdropPath = "/backdrop.jpg",
        budget = 0,
        genres = listOf(GenreItemDTO(28, "Action")),
        homepage = null,
        id = 1,
        imdbId = null,
        originCountry = originCountry,
        originalLanguage = originalLanguage,
        originalTitle = "Sample",
        overview = "Overview",
        popularity = 10.0,
        posterPath = "/poster.jpg",
        productionCompanies = emptyList(),
        productionCountries = productionCountries,
        releaseDate = "2024-01-01",
        revenue = 0,
        runtime = 120,
        spokenLanguages = emptyList(),
        status = "Released",
        tagline = null,
        title = "Sample",
        video = false,
        voteAverage = 8.5,
        voteCount = 100
    )
}
