package com.example.gnewsapi.data.repository

import com.example.gnewsapi.data.api.NewsResponse

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