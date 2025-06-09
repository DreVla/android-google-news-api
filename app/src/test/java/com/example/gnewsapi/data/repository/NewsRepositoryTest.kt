package com.example.gnewsapi.data.repository

import com.example.gnewsapi.data.api.NewsApiService
import com.example.gnewsapi.data.api.NewsResponse
import com.example.gnewsapi.data.local.ArticleDao
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for the [NewsRepository] implementation.
 * This class tests the behavior of the repository methods under different conditions, such as
 * successful API responses and failure scenarios.
 */
class NewsRepositoryTest {

    @MockK
    private lateinit var newsApiService: NewsApiService

    @MockK
    private lateinit var articleDao: ArticleDao

    private lateinit var newsRepository: NewsRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        newsRepository = NewsRepositoryImpl(newsApiService, articleDao)
    }

    /**
     * Test case for verifying the behavior of [NewsRepository.getTopHeadlines] when the API
     * response is successful. Ensures that the repository returns the expected [NewsResponse]
     * object.
     */
    @Test
    fun `GIVEN getTopHeadlines() WHEN status 'ok' THEN return NewsResponse`() = runTest {
        // GIVEN
        val mockResponse = NewsResponse(
            status = "ok",
            totalResults = 10,
            articles = emptyList(),
        )
        coEvery { newsApiService.getTopHeadlines(page = 1, apiKey = "test") } returns mockResponse

        // WHEN
        val result = newsRepository.getTopHeadlines(page = 1, apiKey = "test")

        // THEN
        assert(result == mockResponse)
    }

    /**
     * Test case for verifying the behavior of [NewsRepository.getTopHeadlines] when the API
     * response indicates failure.Ensures that the repository throws an exception in case of an
     * error response.
     */
    @Test
    fun `GIVEN getTopHeadlines() WHEN failure THEN throw Exception`() = runTest {
        // GIVEN
        val mockResponse = NewsResponse(
            status = "not ok :(",
            totalResults = 0,
            articles = emptyList(),
        )
        coEvery { newsApiService.getTopHeadlines(page = 1, apiKey = "test") } returns mockResponse

        // WHEN
        var exception: Exception? = null
        try {
            newsRepository.getTopHeadlines(page = 1, apiKey = "test")
        } catch (e: Exception) {
            exception = e
        }

        // THEN
        assert(exception != null)
    }
}