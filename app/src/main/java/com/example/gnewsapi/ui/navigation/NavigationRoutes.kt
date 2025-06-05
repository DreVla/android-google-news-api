package com.example.gnewsapi.ui.navigation

import com.example.gnewsapi.model.Article
import kotlinx.serialization.Serializable

/**
 * Custom navigation routes for TopArticles Screen and FullArticle Screen.
 *
 * [source](https://www.youtube.com/watch?v=qBxaZ071N0c)
 * [source](https://developer.android.com/reference/androidx/navigation/NavType)
 */
@Serializable
data class TopArticlesRoute(val articles: List<Article>, val columns: Int)
@Serializable
data class FullArticleRoute(val article: Article)
