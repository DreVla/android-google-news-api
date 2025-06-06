package com.example.gnewsapi.ui.screen

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.gnewsapi.Article.ProtobufSavedArticles
import com.example.gnewsapi.data.pagingsource.TopHeadlinesPagingSource
import com.example.gnewsapi.data.repository.NewsRepository
import com.example.gnewsapi.data.serializer.SavedArticlesSerializer
import com.example.gnewsapi.model.Article
import com.example.gnewsapi.model.toProtobuf
import com.example.gnewsapi.util.ConnectivityObserver
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TOP_HEADLINES_INITIAL_PAGE_SIZE = 21
private const val TOP_HEADLINES_PAGE_SIZE = 21


class NewsViewModel(
    private val newsRepository: NewsRepository,
    private val context: Context,
    private val connectivityObserver: ConnectivityObserver,
) : ViewModel() {

    val isConnected = connectivityObserver
        .isConnected
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            false,
        )

    /**
     * Creating data store instance to store saved articles.
     *
     * [source](https://developer.android.com/codelabs/android-proto-datastore#6)
     * [source](https://developer.android.com/topic/libraries/architecture/datastore#proto-datastore)
     */
    private val Context.savedArticlesDataStore: DataStore<ProtobufSavedArticles> by dataStore(
        fileName = "saved_article_data_store.pb",
        serializer = SavedArticlesSerializer
    )

    private val _saveEvent = MutableSharedFlow<String>()
    val saveEvent: SharedFlow<String> = _saveEvent

    private val _isArticleSaved = MutableSharedFlow<Boolean>()
    val isArticleSaved: SharedFlow<Boolean> = _isArticleSaved

    val topHeadlinesFlow = Pager(
        config = PagingConfig(
            // Default initialLoadSize is 3 * pageSize, so according to first requirement, we should
            // load only 21 articles first time.
            initialLoadSize = TOP_HEADLINES_INITIAL_PAGE_SIZE,
            pageSize = TOP_HEADLINES_PAGE_SIZE
        ),
        pagingSourceFactory = { TopHeadlinesPagingSource(newsRepository) }
    ).flow.cachedIn(viewModelScope)

    /**
     * Checks if article is saved.
     *
     * @param article Article to be checked
     */
    fun checkIfArticleIsSaved(article: Article) {
        viewModelScope.launch {
            val protobufArticle = article.toProtobuf()
            val isSaved = context.savedArticlesDataStore.data.first().articlesList
                .contains(protobufArticle)
            _isArticleSaved.emit(isSaved)
        }
    }

    /**
     * Here is where the POST Api call should be implemented. Because just a mock was required
     * I decided to instead use protobuf data store and store articles so I can display them when
     * offline, therefore solving the optional requirement as well.
     *
     * @param article Article to be saved
     */
    fun toggleSaveArticle(article: Article) {
        val protobufArticle = article.toProtobuf()
        viewModelScope.launch {
            context.savedArticlesDataStore.updateData { store ->
                val savedArticles = store.articlesList
                savedArticles.all {
                    true
                }
                val articleExists = savedArticles.contains(protobufArticle)

                val updatedArticles = if (articleExists) {
                    savedArticles.filter { it.url != protobufArticle.url }
                } else {
                    savedArticles + protobufArticle
                }

                _isArticleSaved.emit(
                    !articleExists
                )

                _saveEvent.emit(
                    if (articleExists)
                        "Article removed from saved"
                    else
                        "Article saved"
                )

                updatedArticles.all {
                    true
                }
                store.toBuilder().clearArticles().addAllArticles(updatedArticles).build()
            }
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
class NewsViewModelFactory(
    private val repository: NewsRepository,
    private val context: Context,
    private val connectivityObserver: ConnectivityObserver,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(repository, context, connectivityObserver) as T
    }
}