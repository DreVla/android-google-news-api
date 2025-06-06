package com.example.gnewsapi.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gnewsapi.model.Article

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