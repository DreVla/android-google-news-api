package com.example.gnewsapi.model

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