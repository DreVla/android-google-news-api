package com.example.gnewsapi.ui.screen.fullarticle

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.example.gnewsapi.R
import com.example.gnewsapi.model.Article
import com.example.gnewsapi.model.toProtobuf

@Composable
fun FullArticleScreen(
    article: Article,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    BackHandler {
        onBackClick()
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Icon(
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() },
                    imageVector = androidx.compose.material.icons.Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.full_article_back_button_content_description),
                    tint = MaterialTheme.colorScheme.primary,
                )
                Icon(
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            val protobufArticle = article.toProtobuf()
                            Log.d("drevla", "FullArticleScreen: $protobufArticle")
                            onSaveClick()
                        },
                    painter = painterResource(id = R.drawable.outline_bookmark_add_24),
                    contentDescription = stringResource(R.string.full_article_save_button_content_description),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(256.dp),
                model = article.urlToImage,
                contentDescription = stringResource(R.string.full_article_image_button_content_description),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                loading = {
                    CircularProgressIndicator()
                }
            )

            Text(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                text = article.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                text = article.description,
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(
                modifier = Modifier.height(4.dp),
            )

            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                text = "By ${article.author}",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = article.sourceName,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@PreviewLightDark
@Composable
fun FullArticleScreenPreview() {
    val context = LocalContext.current
    FullArticleScreen(
        article = Article(
            sourceName = "Source name",
            author = "Unknown author",
            title = "Big Title",
            description = "Short description",
            url = "url",
            urlToImage = "urlToImage",
            content = stringResource(R.string.lorem_ipsum),
        ),
        onBackClick = {},
        onSaveClick = {},
    )
}