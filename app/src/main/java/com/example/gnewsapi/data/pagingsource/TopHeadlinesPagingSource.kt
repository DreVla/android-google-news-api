package com.example.gnewsapi.data.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gnewsapi.BuildConfig
import com.example.gnewsapi.data.dto.toDomain
import com.example.gnewsapi.data.repository.NewsRepository
import com.example.gnewsapi.model.Article

private const val STARTING_PAGE_INDEX = 1

/**
 * I wanted to learn how to use the Paging 3 library because I only heard bad things about it.
 * How bad could it be?
 *
 * The [TopHeadlinesPagingSource] implementation defines the source of data and how to retrieve data
 * from that source. The PagingData object queries data from the [TopHeadlinesPagingSource] in
 * response to loading hints that are generated as the user scrolls.
 *
 * [source](https://developer.android.com/topic/libraries/architecture/paging/v3-paged-data)
 * [source](https://developer.android.com/codelabs/android-paging#4)
 */
class TopHeadlinesPagingSource(
    private val newsRepository: NewsRepository,
) : PagingSource<Int, Article>() {

    /**
     * The load() function will be called by the Paging library to asynchronously fetch more data
     * to be displayed as the user scrolls around.
     *
     * Returns a [LoadResult]. This will replace the usage of RepoSearchResult in our app, as
     * LoadResult can take one of the following types:
     *
     *     LoadResult.Page, if the result was successful.
     *     LoadResult.Error, in case of error.
     *
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: STARTING_PAGE_INDEX
        Log.d("drevla: ", "load: ${params.loadSize}")
        return try {
            val response = newsRepository.getTopHeadlines(
                page = page,
                pageSize = params.loadSize,
                apiKey = BuildConfig.NEWS_API_KEY
            )

            val articles = response.articles.map { articleDTO -> articleDTO.toDomain() }

            LoadResult.Page(
                data = articles,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (articles.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    /**
     * The refresh key is used for subsequent refresh calls to PagingSource.load after the initial
     * load.
     */
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}