package com.example.gnewsapi.data.repository

import com.example.gnewsapi.data.api.NewsApiService
import com.example.gnewsapi.data.api.NewsResponse
import com.example.gnewsapi.data.dto.toDomain
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
            // cache articles
            response.articles.forEach {
                articleDao.insertArticle(it.toDomain().toEntity())
            }
            return response
        } else {
            throw Exception("Error fetching top headlines: ${response.status}")
        }
    }

    override suspend fun getCachedArticles(): List<Article> {
        return articleDao.getAllArticles().map { it.toArticle() }
    }
}