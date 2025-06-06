package com.example.gnewsapi

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.room.Room
import com.example.gnewsapi.data.api.NewsApiService
import com.example.gnewsapi.data.api.RetrofitClient
import com.example.gnewsapi.data.local.NewsDatabase
import com.example.gnewsapi.data.repository.NewsRepositoryImpl
import com.example.gnewsapi.ui.navigation.NavigationHost
import com.example.gnewsapi.ui.screen.NewsViewModel
import com.example.gnewsapi.ui.screen.NewsViewModelFactory
import com.example.gnewsapi.ui.theme.GNewsApiTheme
import com.example.gnewsapi.util.ConnectivityObserver

class MainActivity : ComponentActivity() {

    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val newsApiService = RetrofitClient.instance.create(NewsApiService::class.java)

        val newsDatabase = Room.databaseBuilder(
            applicationContext,
            NewsDatabase::class.java, "news-database"
        ).build()

        val repository = NewsRepositoryImpl(newsApiService, newsDatabase.articleDao())
        val viewModelFactory = NewsViewModelFactory(repository, applicationContext, ConnectivityObserver(applicationContext))
        newsViewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]

        setContent {
            val isConnected by newsViewModel.isConnected.collectAsStateWithLifecycle()
            val articles = newsViewModel.topHeadlinesFlow.collectAsLazyPagingItems()
            val isArticleSavedState = newsViewModel.isArticleSaved.collectAsState(initial = false)
            val configuration = LocalConfiguration.current
            val columns = when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> 3
                else -> 2
            }

            val navController = rememberNavController()

            LaunchedEffect(Unit) {
                newsViewModel.saveEvent.collect {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                }
            }

            GNewsApiTheme {
                NavigationHost(
                    navController = navController,
                    articles = articles,
                    columns = columns,
                    isConnected = isConnected,
                    isSaved = isArticleSavedState.value,
                    onCheckIfSaved = { article ->
                        newsViewModel.checkIfArticleIsSaved(article)
                    },
                    onSaveArticle = { article ->
                        newsViewModel.toggleSaveArticle(article)
                    },
                )
            }
        }
    }
}