package com.example.gnewsapi.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.gnewsapi.data.pagingsource.TopHeadlinesPagingSource
import com.example.gnewsapi.data.repository.NewsRepository
import com.example.gnewsapi.model.Article
import com.example.gnewsapi.model.toProtobuf

private const val TOP_HEADLINES_INITIAL_PAGE_SIZE = 21
private const val TOP_HEADLINES_PAGE_SIZE = 21

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    val topHeadlinesFlow = Pager(
        config = PagingConfig(
            // Default initialLoadSize is 3 * pageSize, so according to first requirement, we should
            // load only 21 articles first time.
            initialLoadSize = TOP_HEADLINES_INITIAL_PAGE_SIZE,
            pageSize = TOP_HEADLINES_PAGE_SIZE
        ),
        pagingSourceFactory = { TopHeadlinesPagingSource(newsRepository) }
    ).flow.cachedIn(viewModelScope)

    fun saveArticle(article: Article) {
        val protobufArticle = article.toProtobuf()
        Log.d("drevla", "saveArticle: $protobufArticle")
    }
}

/**
 * To follow dependency injection best practices and because our view model has dependencies as
 * parameters we build factory, used to create the view model. Hilt would be a better option for
 * this since we dont have to manually do it, but for the sake of simplicity we will use this (also
 * hilt gives me a headache when setting up correctly all the versions and dependencies :( ).
 *
 * [source](https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-factories)
 */
class NewsViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(repository) as T
    }
}