package com.example.gnewsapi.networking

import com.example.gnewsapi.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    /**
     * Retrieves the top headlines with parameters
     * @param country The 2-letter ISO 3166-1 code of the country you want to get headlines for.
     * Possible options: us. Note: you can't mix this param with the sources param.
     * @param category The category you want to get headlines for. Possible options: business,
     * entertainment, general, health, science, sports, technology. Note: you can't mix this param
     * with the sources param.
     * @param sources A comma-seperated string of sorts of articles you want headlines from.
     * @param query Keywords or a phrase to search for.
     * @param pageSize The number of results to return per page. 21 is the default,
     * 100 is the maximum.
     * @param page  Use this to page through the results if the total results found is greater than
     * the page size.
     * @param apiKey REQUIRED Your API key.
     *
     * At least one of the following parameters are required:
     * sources, q, language, country, category.
     *
     * [source](https://newsapi.org/docs/endpoints/top-headlines)
     */
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("category") category: String? = null,
        @Query("sources") sources: String? = null,
        @Query("q") query: String? = null,
        @Query("pageSize") pageSize: Int = 21,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String,
    ): NewsResponse


}