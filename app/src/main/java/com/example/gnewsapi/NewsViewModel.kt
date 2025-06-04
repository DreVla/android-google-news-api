package com.example.gnewsapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gnewsapi.model.ArticleDTO
import com.example.gnewsapi.networking.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository): ViewModel() {
    private val _articles = MutableLiveData<List<ArticleDTO>>()
    val articles: LiveData<List<ArticleDTO>> = _articles

    fun getTopHeadlines(
        country: String = "us",
        category: String? = null,
        sources: String? = null,
        query: String? = null,
        pageSize: Int = 21,
        page: Int = 0,
        apiKey: String = BuildConfig.NEWS_API_KEY,
        ) {
        viewModelScope.launch {
            val response = newsRepository.getTopHeadlines(
                country = country,
                category = category,
                sources = sources,
                query = query,
                pageSize = pageSize,
                page = page,
                apiKey = apiKey
            )
            _articles.value = response.articles
        }

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
    override fun <T: ViewModel> create (modelClass: Class<T>): T {
        return NewsViewModel(repository) as T
    }
}