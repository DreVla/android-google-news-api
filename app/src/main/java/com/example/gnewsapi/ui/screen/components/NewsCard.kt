package com.example.gnewsapi.ui.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.example.gnewsapi.R
import com.example.gnewsapi.model.Article
import com.example.gnewsapi.ui.theme.GNewsApiTheme


@Composable
fun NewsCard(
    article: Article,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(3f / 5f),
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                model = article.urlToImage,
                contentDescription = stringResource(R.string.full_article_image_button_content_description),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                loading = {
                    CircularProgressIndicator()
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(
                    modifier = Modifier.height(4.dp),
                )

                Text(
                    text = "By ${article.author}",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = article.sourceName,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }

}

@PreviewLightDark
@Composable
fun NewsCardPreview() {
    GNewsApiTheme {
        NewsCard(
            article = Article(
                sourceName = stringResource(R.string.lorem_ipsum),
                author = stringResource(R.string.lorem_ipsum),
                title = stringResource(R.string.lorem_ipsum),
                description = stringResource(R.string.lorem_ipsum),
                url = "test url",
                urlToImage = "test url to image",
                content = stringResource(R.string.lorem_ipsum),
            ),
            modifier = Modifier,
            onClick = {},
        )
    }
}