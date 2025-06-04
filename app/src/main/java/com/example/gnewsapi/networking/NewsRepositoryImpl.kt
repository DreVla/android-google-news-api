package com.example.gnewsapi.networking

import com.example.gnewsapi.model.NewsResponse

/**
 * Repository design pattern used to abstract the data source from the rest of the app.
 */
class NewsRepositoryImpl(private val newsApiService: NewsApiService): NewsRepository {

    override suspend fun getTopHeadlines(
        country: String,
        category: String?,
        sources: String?,
        query: String?,
        pageSize: Int,
        page: Int,
        apiKey: String,
    ): NewsResponse {
        val response = newsApiService.getTopHeadlines(
            country = country,
            category = category,
            sources = sources,
            query = query,
            pageSize = pageSize,
            page = page,
            apiKey = apiKey
        )
        if (response.status == "ok") {
            return response
        } else {
            throw Exception("Error fetching top headlines: ${response.status}")
        }
    }
}