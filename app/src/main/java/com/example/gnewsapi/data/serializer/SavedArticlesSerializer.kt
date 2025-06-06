package com.example.gnewsapi.data.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.gnewsapi.Article.ProtobufSavedArticles
import java.io.InputStream
import java.io.OutputStream

object SavedArticlesSerializer : Serializer<ProtobufSavedArticles> {
    override val defaultValue: ProtobufSavedArticles = ProtobufSavedArticles.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ProtobufSavedArticles {
        try {
            return ProtobufSavedArticles.parseFrom(input)
        } catch (exception: Exception) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: ProtobufSavedArticles, output: OutputStream) {
        t.writeTo(output)
    }
}