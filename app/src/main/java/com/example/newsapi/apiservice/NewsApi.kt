package com.example.newsapi.apiservice

object NewsApi {

    val retrofitService: NewsApiInterface by lazy {
        retrofit.create(NewsApiInterface::class.java)
    }

}