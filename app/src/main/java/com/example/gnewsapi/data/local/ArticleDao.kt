package com.example.gnewsapi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ArticleDao {
    @Insert
    suspend fun insertArticle(article: ArticleEntity)
    @Query("SELECT * FROM articles")
    suspend fun getAllArticles(): List<ArticleEntity>
}