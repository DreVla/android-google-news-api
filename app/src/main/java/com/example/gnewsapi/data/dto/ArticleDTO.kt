package com.example.gnewsapi.data.dto

import com.example.gnewsapi.model.Article

/**
 * Created using the example response endpoint from the Google News Api and the help of online
 * json to kotlin transformer at https://transform.tools/json-to-kotlin
 *
 * Article object from the Google News Api
 * @param source The identifier id and a display name name for the source this article came from.
 * @param author The author of the article
 * @param title The headline or title of the article.
 * @param description A description or snippet from the article.
 * @param url The direct URL to the article.
 * @param urlToImage The URL to a relevant image for the article.
 * @param publishedAt The date and time that the article was published, in UTC (+000)
 * @param content The unformatted content of the article, where available. This is truncated to
 * 200 chars.
 */
data class ArticleDTO(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String,
)

fun ArticleDTO.toDomain(): Article {
    return Article(
        sourceName = if (source.name.isNullOrBlank()) "Unknown Source" else source.name,
        author = if (author.isNullOrBlank()) "Unknown Author" else author,
        title = if (title.isNullOrBlank()) "Missing Title" else title,
        description = if (description.isNullOrBlank()) "Missing Description" else description,
        urlToImage = if (urlToImage.isNullOrBlank()) "" else urlToImage,
        url = if (url.isNullOrBlank()) "" else url,
        content = if (content.isNullOrBlank()) "Missing Content" else content,
    )
}

/**
 * Source object from the Google News Api
 *
 * @param id The identifier id for the source.
 * @param name The display name for the source.
 */
data class Source(
    val id: String,
    val name: String,
)