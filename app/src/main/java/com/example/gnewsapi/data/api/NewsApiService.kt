package com.example.gnewsapi.data.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsApiService {

    /**
     * Retrieves the top headlines with parameters
     *
     * @param country The 2-letter ISO 3166-1 code of the country you want to get headlines for.
     * Possible options: us. Note: you can't mix this param with the sources param.
     * @param category The category you want to get headlines for. Possible options: business,
     * entertainment, general, health, science, sports, technology. Note: you can't mix this param
     * with the sources param.
     * @param sources A comma-seperated string of sorts of articles you want headlines from.
     * @param query Keywords or a phrase to search for.
     * @param pageSize The number of results to return per page. 21 is the default,
     * 100 is the maximum.
     * @param page Use this to page through the results if the total results found is greater than
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
        @Header("X-Api-Key") apiKey: String,
        @Query("country") country: String = "us",
        @Query("category") category: String? = null,
        @Query("sources") sources: String? = null,
        @Query("q") query: String? = null,
        @Query("pageSize") pageSize: Int = 21,
        @Query("page") page: Int,
    ): NewsResponse

    /**
     * Search through millions of articles from over 150,000 large and small news sources and blogs.
     *
     * @param query Keywords or phrases to search for in the article title and body.
     * @param searchIn The fields to restrict your q search to. The possible options are: title,
     * description, content. Default: all fields are searched.
     * @param sources A comma-seperated string of identifiers (maximum 20) for the news sources or
     * blogs you want headlines from.
     * @param domains A comma-seperated string of domains to restrict the search to.
     * @param excludeDomains A comma-seperated string of domains to remove from the results.
     * @param from A date and optional time for the oldest article allowed. This should be in ISO
     * 8601 format (e.g. 2025-06-09 or 2025-06-09T08:44:59)
     * @param to A date and optional time for the newest article allowed. This should be in ISO 8601
     * format (e.g. 2025-06-09 or 2025-06-09T08:44:59)
     * @param language The 2-letter ISO-639-1 code of the language you want to get headlines for.
     * Default: all languages returned.
     * @param sortBy The order to sort the articles in. Possible options: relevancy, popularity,
     * publishedAt. Default: publishedAt
     * @param pageSize The number of results to return per page. Default: 100. Maximum: 100.
     * @param page Use this to page through the results.
     *
     * At least one of the following parameters are required:
     * sources, q, language, country, category.
     *
     * [source](https://newsapi.org/docs/endpoints/everything)
     */
    @GET("/v2/everything")
    suspend fun getEverything(
        @Header("X-Api-Key") apiKey: String,
        @Query("q") query: String,
        @Query("searchIn") searchIn: String? = null,
        @Query("sources") sources: String? = null,
        @Query("domains") domains: String? = null,
        @Query("excludeDomains") excludeDomains: String? = null,
        @Query("from") from: String? = null,
        @Query("to") to: String? = null,
        @Query("language") language: String? = null,
        @Query("sortBy") sortBy: String? = null,
        @Query("pageSize") pageSize: Int = 21,
        @Query("page") page: Int,
    ): NewsResponse

}