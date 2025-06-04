package com.example.gnewsapi.model

/**
 * Created using the example response endpoint from the Google News Api and the help of online
 * json to kotlin transformer at https://transform.tools/json-to-kotlin
 *
 * Response object from the Google News Api
 * @param status The status of the response.
 * Options: ok, error. In the case of error a code and message property will be populated.
 * @param totalResults The total number of results available for your request.
 * @param articles The list of articles for the request.
 */
data class NewsResponse(
    val status: String,
    val totalResults: Long,
    val articles: List<ArticleDTO>,
)