package com.example.gnewsapi.data.repository

import com.example.gnewsapi.data.api.NewsResponse
import com.example.gnewsapi.model.Article

interface NewsRepository {

    /**
     * NewsApi operations
     */
    suspend fun getTopHeadlines(
        apiKey: String,
        country: String = "us",
        category: String? = null,
        sources: String? = null,
        query: String? = null,
        pageSize: Int = 21,
        page: Int = 0,
    ): NewsResponse

    suspend fun getEverything(
        apiKey: String,
        query: String = "sport", // hardcoded sport here just to test the api
        searchIn: String? = null,
        sources: String? = null,
        domains: String? = null,
        excludeDomains: String? = null,
        from: String? = null,
        to: String? = null,
        language: String? = null,
        sortBy: String? = null,
        pageSize: Int = 21,
        page: Int = 0,
    ): NewsResponse


    /**
     * Room database operations
     */
    suspend fun getSavedArticles(): List<Article>

    suspend fun saveArticle(article: Article)

    suspend fun deleteArticle(article: Article)

    suspend fun isArticleSaved(url: String): Boolean
}