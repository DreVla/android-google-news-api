package com.example.gnewsapi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Data Access Object (DAO) for the articles table.
 * Provides methods to insert, delete, check, and retrieve articles in the local Room database.
 */
@Dao
interface ArticleDao {
    /**
     * Inserts an article into the database.
     * @param article The article entity to insert.
     */
    @Insert
    suspend fun insertArticle(article: ArticleEntity)

    /**
     * Deletes an article from the database by its URL.
     * @param url The URL of the article to delete.
     */
    @Query("DELETE FROM articles WHERE url = :url")
    suspend fun deleteArticle(url: String)

    /**
     * Checks if an article exists in the database by its URL.
     * @param url The URL of the article to check.
     * @return True if the article exists, false otherwise.
     */
    @Query("SELECT EXISTS(SELECT * FROM articles WHERE url = :url)")
    suspend fun isArticleSaved(url: String): Boolean

    /**
     * Retrieves all articles from the database.
     * @return A list of all article entities.
     */
    @Query("SELECT * FROM articles")
    suspend fun getAllArticles(): List<ArticleEntity>

    /**
     * Deletes all articles from the database.
     */
    @Query("DELETE FROM articles")
    suspend fun deleteAllArticles()
}