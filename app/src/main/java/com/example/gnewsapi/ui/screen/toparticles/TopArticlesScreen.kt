package com.example.gnewsapi.ui.screen.toparticles

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.gnewsapi.model.Article
import com.example.gnewsapi.ui.screen.components.NewsCard

@Composable
fun TopArticlesScreen(
    articles: List<Article>,
    columns: Int,
    onArticleClick: (Article) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
        ) {
            items(articles) { article ->
                NewsCard(
                    article = article,
                    modifier = Modifier,
                    onClick = { onArticleClick(article) },
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun TopArticlesScreenPreview() {
    TopArticlesScreen(
        articles = listOf(
            Article(
                sourceName = "test source name",
                author = "test author",
                title = "test title",
                description = "test description",
                url = "test url",
                urlToImage = "test url to image",
                content = "test content",
            ),
            Article(
                sourceName = "test source name",
                author = "test author",
                title = "test title",
                description = "test description",
                url = "test url",
                urlToImage = "test url to image",
                content = "test content",
            ),
            Article(
                sourceName = "test source name",
                author = "test author",
                title = "test title",
                description = "test description",
                url = "test url",
                urlToImage = "test url to image",
                content = "test content",
            ),
        ),
        columns = 2,
        onArticleClick = {},
    )
}