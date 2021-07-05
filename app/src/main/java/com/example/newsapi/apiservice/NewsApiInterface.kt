package com.example.newsapi.apiservice

import com.example.newsapi.model.ArticleResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiInterface {

    @GET("/v2/top-headlines")
    suspend fun searchForNews(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("pageSize") pageSize: Int = 10,
        @Query("page") page: Int
    ): Response<ArticleResult>

}