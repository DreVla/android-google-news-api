package com.example.gnewsapi.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gnewsapi.model.Article

/**
 * Entity class representing an article in the local Room database.
 *
 * @property uid Auto-generated unique identifier for the article.
 * @property sourceName The name of the source.
 * @property author The author of the article.
 * @property title The title of the article.
 * @property description A brief description from the article.
 * @property url The direct URL to the article.
 * @property urlToImage The URL to an image associated with the article.
 * @property content The full content of the article.
 */
@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,

    @ColumnInfo(name = "source_name") val sourceName: String,

    @ColumnInfo(name = "author") val author: String,

    @ColumnInfo(name = "title") val title: String,

    @ColumnInfo(name = "description") val description: String,

    @ColumnInfo(name = "url") val url: String,

    @ColumnInfo(name = "urlToImage") val urlToImage: String,

    @ColumnInfo(name = "content") val content: String,
)

fun ArticleEntity.toArticle(): Article = Article(
    sourceName = sourceName,
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    content = content
)

fun Article.toEntity(): ArticleEntity = ArticleEntity(
    sourceName = sourceName,
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    content = content
)