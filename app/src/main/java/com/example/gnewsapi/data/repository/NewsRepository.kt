package com.example.gnewsapi.data.repository

import com.example.gnewsapi.data.api.NewsResponse
import com.example.gnewsapi.model.Article

interface NewsRepository {

    /**
     * NewsApi operations
     */
    suspend fun getTopHeadlines(
        country: String = "us",
        category: String? = null,
        sources: String? = null,
        query: String? = null,
        pageSize: Int = 21,
        page: Int = 0,
        apiKey: String,
    ): NewsResponse

    /**
     * Room database operations
     */
    suspend fun getSavedArticles(): List<Article>

    suspend fun saveArticle(article: Article)

    suspend fun deleteArticle(article: Article)

    suspend fun isArticleSaved(url: String): Boolean
}