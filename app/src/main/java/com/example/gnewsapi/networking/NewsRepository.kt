package com.example.gnewsapi.networking

import com.example.gnewsapi.model.NewsResponse

interface NewsRepository {

    suspend fun getTopHeadlines(
        country: String = "us",
        category: String? = null,
        sources: String? = null,
        query: String? = null,
        pageSize: Int = 21,
        page: Int = 0,
        apiKey: String,
    ): NewsResponse
}