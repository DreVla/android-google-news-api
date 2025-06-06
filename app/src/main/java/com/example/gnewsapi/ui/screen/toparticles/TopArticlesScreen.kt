package com.example.gnewsapi.ui.screen.toparticles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gnewsapi.R
import com.example.gnewsapi.model.Article
import com.example.gnewsapi.ui.screen.components.NewsCard
import kotlinx.coroutines.flow.flowOf

@Composable
fun TopArticlesScreen(
    articles: LazyPagingItems<Article>,
    columns: Int,
    isConnected: Boolean,
    noSavedArticles: Boolean,
    onArticleClick: (Article) -> Unit,
) {
    val gridState = rememberLazyGridState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
        ) { innerPadding ->
            if (!isConnected) NoInternetConnection()

            if (noSavedArticles) NoSavedArticles()

            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = if (!isConnected) 32.dp else 0.dp),
                state = gridState,
                contentPadding = innerPadding,
            ) {
                items(articles.itemCount) { index ->
                    articles[index]?.let { article ->
                        NewsCard(
                            article = article,
                            modifier = Modifier,
                            onClick = { onArticleClick(article) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NoInternetConnection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red),
    ) {
        Text(
            text = stringResource(R.string.no_internet_connection),
            modifier = Modifier
                .align(Alignment.Center),
            color = Color.White,
        )
    }
}

@Composable
fun NoSavedArticles() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(
            text = stringResource(R.string.top_articles_message_no_saved_articles),
            modifier = Modifier
                .align(Alignment.Center),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@PreviewLightDark
@Composable
fun TopArticlesScreenPreview() {
    val dummyArticle = Article(
        sourceName = "test source name",
        author = "test author",
        title = stringResource(R.string.lorem_ipsum),
        description = "test description",
        url = "test url",
        urlToImage = "test url to image",
        content = "test content",
    )
    val articles = listOf(dummyArticle, dummyArticle, dummyArticle)
    TopArticlesScreen(
        articles = flowOf(
            PagingData.from(
                articles
            )
        ).collectAsLazyPagingItems(),
        columns = 2,
        isConnected = true,
        noSavedArticles = false,
        onArticleClick = {},
    )
}