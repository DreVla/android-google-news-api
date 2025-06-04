package com.example.gnewsapi

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.ViewModelProvider
import com.example.gnewsapi.data.api.NewsApiService
import com.example.gnewsapi.data.api.RetrofitClient
import com.example.gnewsapi.data.repository.NewsRepositoryImpl
import com.example.gnewsapi.ui.screen.NewsViewModel
import com.example.gnewsapi.ui.screen.NewsViewModelFactory
import com.example.gnewsapi.ui.screen.components.LoadingOverlay
import com.example.gnewsapi.ui.screen.fullarticle.FullArticleScreen
import com.example.gnewsapi.ui.screen.toparticles.TopArticlesScreen
import com.example.gnewsapi.ui.theme.GNewsApiTheme

class MainActivity : ComponentActivity() {

    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val newsApiService = RetrofitClient.instance.create(NewsApiService::class.java)
        val repository = NewsRepositoryImpl(newsApiService)
        val viewModelFactory = NewsViewModelFactory(repository)
        newsViewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]

        newsViewModel.getTopHeadlines()

        setContent {
            GNewsApiTheme {
                val articles by newsViewModel.articles.observeAsState(emptyList())
                val selectedArticle by newsViewModel.selectedArticle.observeAsState()

                val configuration = LocalConfiguration.current
                val columns = when (configuration.orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> 3
                    else -> 2
                }

                // Simple navigation for now
                if (selectedArticle != null) {
                    FullArticleScreen(
                        article = selectedArticle!!,
                        onBackClick = { newsViewModel.clearSelectedArticle() },
                    )
                } else {
                    LoadingOverlay(isLoading = newsViewModel.isLoading) {
                        TopArticlesScreen(
                            articles = articles,
                            columns = columns,
                            onArticleClick = { article ->
                                newsViewModel.setSelectedArticle(article)
                            },
                        )
                    }
                }
            }
        }
    }
}