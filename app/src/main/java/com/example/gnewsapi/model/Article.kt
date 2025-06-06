package com.example.gnewsapi.model

import com.example.gnewsapi.Article.ProtobufArticle
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val sourceName: String,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val content: String,
)

fun Article.toProtobuf(): ProtobufArticle {
    return ProtobufArticle.newBuilder()
        .setSourceName(sourceName)
        .setAuthor(author)
        .setTitle(title)
        .setDescription(description)
        .setUrl(url)
        .setUrlToImage(urlToImage)
        .setContent(content)
        .build()
}