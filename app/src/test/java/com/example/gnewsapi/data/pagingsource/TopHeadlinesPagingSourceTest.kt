package com.example.gnewsapi.data.pagingsource

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gnewsapi.BuildConfig
import com.example.gnewsapi.data.api.NewsResponse
import com.example.gnewsapi.data.dto.ArticleDTO
import com.example.gnewsapi.data.dto.Source
import com.example.gnewsapi.data.dto.toDomain
import com.example.gnewsapi.data.repository.NewsRepository
import com.example.gnewsapi.model.Article
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

const val PAGE_SIZE = 21
const val FIRST_PAGE = 1

/**
 * Unit tests for the [TopHeadlinesPagingSource] class.
 * This class tests the behavior of the paging source under different conditions,
 * such as online/offline mode, network errors, and refresh key retrieval.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TopHeadlinesPagingSourceTest {

    @MockK
    private lateinit var newsRepository: NewsRepository

    private lateinit var pagingSource: TopHeadlinesPagingSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    /**
     * Test case for verifying that when the device is offline, the [TopHeadlinesPagingSource.load]
     * function returns local data.
     */
    @Test
    fun `GIVEN offline WHEN load() THEN return local data`() = runTest {
        // GIVEN
        val articles = listOf(
            Article(
                sourceName = "test source",
                author = "test author",
                title = "test title",
                description = "test description",
                url = "test url",
                urlToImage = "test image url",
                content = "test content"
            )
        )
        val expectedResult = PagingSource.LoadResult.Page(
            data = articles,
            prevKey = null,
            nextKey = null
        )
        coEvery { newsRepository.getSavedArticles() } returns articles

        // WHEN
        pagingSource = TopHeadlinesPagingSource(newsRepository, false)
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = PAGE_SIZE,
                placeholdersEnabled = false
            )
        )

        // THEN
        assertEquals(expectedResult, result)
    }

    /**
     * Test case for verifying that when the device is online, the [TopHeadlinesPagingSource.load]
     * function returns remote data.
     */
    @Test
    fun `GIVEN online WHEN load() THEN return remote data`() = runTest {
        // GIVEN
        val dtoArticles = listOf(
            ArticleDTO(
                source = Source(
                    id = "remote id",
                    name = "remote name"
                ),
                author = "remote author",
                title = "remote title",
                description = "remote description",
                url = "remote url",
                urlToImage = "remote url image",
                publishedAt = "remote date",
                content = "remote content"
            )
        )
        val expectedArticles = dtoArticles.map {
            it.toDomain()
        }
        val expectedResult = PagingSource.LoadResult.Page(
            data = expectedArticles,
            prevKey = null,
            nextKey = 2,
        )
        coEvery {
            newsRepository.getTopHeadlines(
                page = FIRST_PAGE,
                pageSize = PAGE_SIZE,
                apiKey = BuildConfig.NEWS_API_KEY
            )
        } returns (
                NewsResponse(
                    status = "ok",
                    totalResults = 1,
                    articles = dtoArticles
                )
                )

        // WHEN
        pagingSource = TopHeadlinesPagingSource(newsRepository, true)
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = FIRST_PAGE,
                loadSize = PAGE_SIZE,
                placeholdersEnabled = false
            )
        )

        // THEN
        assertEquals(expectedResult, result)
    }

    /**
     * Test case for verifying that when an exception occurs during data loading,
     * the [TopHeadlinesPagingSource.load] function returns a LoadResult.Error.
     */
    @Test
    fun `GIVEN exception WHEN load() THEN return LoadResult Error`() = runTest {
        // GIVEN
        coEvery {
            newsRepository.getTopHeadlines(
                page = FIRST_PAGE,
                pageSize = PAGE_SIZE,
                apiKey = BuildConfig.NEWS_API_KEY
            )
        } throws RuntimeException("Network failure")

        pagingSource = TopHeadlinesPagingSource(newsRepository, true)

        // WHEN
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 21,
                placeholdersEnabled = false
            )
        )

        // THEN
        assert(result is PagingSource.LoadResult.Error)
    }

    /**
     * Test case for verifying the refresh key retrieval logic in the
     * [TopHeadlinesPagingSource.getRefreshKey] function.
     */
    @Test
    fun `GIVEN paging state WHEN getRefreshKey() THEN return correct key`() {
        // GIVEN
        pagingSource = TopHeadlinesPagingSource(newsRepository, true)
        val pagingState = PagingState(
            pages = listOf(
                PagingSource.LoadResult.Page(
                    data = listOf(
                        Article(
                            sourceName = "source",
                            author = "author",
                            title = "title",
                            description = "description",
                            url = "url",
                            urlToImage = "image url",
                            content = "content"
                        )
                    ),
                    prevKey = 1,
                    nextKey = 3
                )
            ),
            anchorPosition = 0,
            config = PagingConfig(20),
            leadingPlaceholderCount = 0
        )

        // WHEN
        val refreshKey = pagingSource.getRefreshKey(pagingState)

        // THEN
        assertNotNull(refreshKey)
        assertEquals(2, refreshKey)
    }
}