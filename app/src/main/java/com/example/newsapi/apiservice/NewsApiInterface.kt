package com.example.newsapi.apiservice

import com.example.newsapi.model.ArticleResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiInterface {

    @GET("/v2/top-headlines")
    fun searchForCountry(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<ArticleResult>

}