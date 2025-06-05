package com.example.gnewsapi

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gnewsapi.data.api.NewsApiService
import com.example.gnewsapi.data.api.RetrofitClient
import com.example.gnewsapi.data.repository.NewsRepositoryImpl
import com.example.gnewsapi.ui.navigation.NavigationHost
import com.example.gnewsapi.ui.screen.NewsViewModel
import com.example.gnewsapi.ui.screen.NewsViewModelFactory
import com.example.gnewsapi.ui.theme.GNewsApiTheme

class MainActivity : ComponentActivity() {

    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val newsApiService = RetrofitClient.instance.create(NewsApiService::class.java)
        val repository = NewsRepositoryImpl(newsApiService)
        val viewModelFactory = NewsViewModelFactory(repository)
        newsViewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]

        setContent {
            val articles = newsViewModel.topHeadlinesFlow.collectAsLazyPagingItems()

            val configuration = LocalConfiguration.current
            val columns = when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> 3
                else -> 2
            }

            val navController = rememberNavController()

            GNewsApiTheme {
                NavigationHost(
                    navController = navController,
                    articles = articles,
                    columns = columns,
                )
            }
        }
    }
}