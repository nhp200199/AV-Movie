package com.av.movie.data.datasource.remote

import com.av.movie.data.api.model.NetworkResponse
import com.av.movie.data.api.model.PagingDTO
import com.av.movie.data.api.model.ResultData
import com.av.movie.data.common.exception.NoNetworkConnectionException
import com.av.movie.data.common.exception.UnknownException
import com.av.movie.data.mapper.Mapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// Dummy data classes for testing
data class TestData(val id: Int, val name: String)
data class MappedTestData(val id: Int, val fullName: String)

// Dummy Mapper for testing
class TestMapper : Mapper<TestData, MappedTestData> {
    override fun map(input: TestData): MappedTestData {
        return MappedTestData(input.id, "${input.name} Full")
    }
}

class BaseRemoteDataSourceTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private val mockMapper: TestMapper = mockk()

    private lateinit var baseRemoteDataSource: BaseRemoteDataSource<TestData, MappedTestData>

    @Before
    fun setup() {
        baseRemoteDataSource = BaseRemoteDataSource(mockMapper)
    }

    @Test
    fun `getRemoteDataList returns Success`() = runTest {
        // Arrange
        val testDataList = listOf(TestData(1, "Test1"), TestData(2, "Test2"))
        val mappedDataList = listOf(MappedTestData(1, "Test1 Full"), MappedTestData(2, "Test2 Full"))
        val mockResponse = NetworkResponse.Success(
            PagingDTO<TestData>(1, testDataList, 2, 34)
        )

        every { mockMapper.map(testDataList[0]) } returns MappedTestData(1, "Test1 Full")
        every { mockMapper.map(testDataList[1]) } returns MappedTestData(2, "Test2 Full")

        // Act
        val result = baseRemoteDataSource.getRemoteDataPaging(
            networkCall = { mockResponse },
        )

        // Assert
        assertThat(result, instanceOf(ResultData.Success::class.java))
        assertThat((result as ResultData.Success).data, `is`(mappedDataList))
    }

    @Test
    fun `getRemoteDataList returns ApiError`() = runBlocking {
        // Arrange
        val mockResponse = NetworkResponse.ApiError<String>("Api Error", 400)

        // Act
        val result = baseRemoteDataSource.getRemoteDataPaging(
            networkCall = { mockResponse },
        )

        // Assert
        assertThat(result, instanceOf(ResultData.Error::class.java))
        assertThat((result as ResultData.Error).exception, instanceOf(Exception::class.java))
    }

    @Test
    fun `getRemoteDataList returns NetworkError`() = runTest {
        // Arrange
        val mockResponse = NetworkResponse.NetworkError

        // Act
        val result = baseRemoteDataSource.getRemoteDataPaging(
            networkCall = { mockResponse },
        )

        // Assert
        assertThat(result, instanceOf(ResultData.Error::class.java))
        assertThat((result as ResultData.Error).exception, instanceOf(NoNetworkConnectionException::class.java) )
    }

    @Test
    fun `getRemoteDataList returns UnknownError`() = runBlocking {
        // Arrange
        val mockResponse = NetworkResponse.UnknownError

        // Act
        val result = baseRemoteDataSource.getRemoteDataPaging(
            networkCall = { mockResponse },
        )

        // Assert
        assertThat(result, instanceOf(ResultData.Error::class.java))
        assertThat((result as ResultData.Error).exception, instanceOf(UnknownException::class.java))
    }
}