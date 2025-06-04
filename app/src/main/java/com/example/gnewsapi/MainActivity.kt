package com.example.gnewsapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.gnewsapi.networking.NewsApiService
import com.example.gnewsapi.networking.NewsRepositoryImpl
import com.example.gnewsapi.networking.RetrofitClient
import com.example.gnewsapi.ui.theme.GNewsApiTheme

class MainActivity : ComponentActivity() {

    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val newsApiService = RetrofitClient.instance.create(NewsApiService::class.java)
        val repository = NewsRepositoryImpl(newsApiService)
        val viewModelFactory = NewsViewModelFactory(repository)
        newsViewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]

        newsViewModel.getTopHeadlines()

        setContent {
            GNewsApiTheme {
                val articles by newsViewModel.articles.observeAsState(emptyList())
                for (article in articles) {
                    println("drevla: " + article.title)
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GNewsApiTheme {
        Greeting("Android")
    }
}