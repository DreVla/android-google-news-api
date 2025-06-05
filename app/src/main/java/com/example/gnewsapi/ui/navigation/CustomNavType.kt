package com.example.gnewsapi.ui.navigation

import android.net.Uri
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import com.example.gnewsapi.model.Article
import kotlinx.serialization.json.Json

/**
 * Custom navigation type for [Article] and [List<Article>] needed in order to pass data through
 * routes.
 *
 * [source](https://www.youtube.com/watch?v=qBxaZ071N0c)
 * [source](https://developer.android.com/reference/androidx/navigation/NavType)
 */
object CustomNavType {

    val ArticleType = object : NavType<Article> (
        isNullableAllowed = false,
    ) {
        override fun put(bundle: SavedState, key: String, value: Article) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun get(bundle: SavedState, key: String): Article? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Article {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Article): String {
            return Uri.encode(Json.encodeToString(value))
        }
    }

    val ArticleListType = object : NavType<List<Article>> (
        isNullableAllowed = false,
    ) {
        override fun put(bundle: SavedState, key: String, value: List<Article>) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun get(bundle: SavedState, key: String): List<Article>? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): List<Article> {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: List<Article>): String {
            return Uri.encode(Json.encodeToString(value))
        }
    }
}