package com.example.gnewsapi.data.repository

import com.example.gnewsapi.data.api.NewsApiService
import com.example.gnewsapi.data.api.NewsResponse
import com.example.gnewsapi.data.local.ArticleDao
import com.example.gnewsapi.data.local.toArticle
import com.example.gnewsapi.data.local.toEntity
import com.example.gnewsapi.model.Article

/**
 * Repository design pattern used to abstract the data source from the rest of the app.
 */
class NewsRepositoryImpl(
    private val newsApiService: NewsApiService,
    private val articleDao: ArticleDao,
) : NewsRepository {

    /**
     * NewsApi operations.
     */
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

    /**
     * Room database operations.
     */
    override suspend fun getSavedArticles(): List<Article> {
        return articleDao.getAllArticles().map { it.toArticle() }
    }

    override suspend fun saveArticle(article: Article) {
        articleDao.insertArticle(article.toEntity())
    }

    override suspend fun deleteArticle(article: Article) {
        articleDao.deleteArticle(article.url)
    }

    override suspend fun isArticleSaved(url: String): Boolean {
        return articleDao.isArticleSaved(url)
    }
}