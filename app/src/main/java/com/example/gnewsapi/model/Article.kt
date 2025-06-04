package com.example.gnewsapi.model

data class Article(
    val sourceName: String,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val content: String,
)