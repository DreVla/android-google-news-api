package com.example.gnewsapi.ui.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import com.example.gnewsapi.R

@Composable
fun LinkText(url: String) {
    val uriHandler = LocalUriHandler.current
    Text(
        modifier = Modifier
            .clickable {
                uriHandler.openUri(url)
            }
            .padding(16.dp),
        text = buildAnnotatedString {
            withLink(
                LinkAnnotation.Url(
                    url,
                    TextLinkStyles(style = SpanStyle(color = Color.Blue)),
                )
            ) {
                append(stringResource(R.string.read_full_article))
            }
        }
    )
}