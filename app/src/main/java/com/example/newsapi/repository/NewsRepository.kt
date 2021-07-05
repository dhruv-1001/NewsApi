package com.example.newsapi.repository

import com.example.newsapi.apiservice.NewsApi
import com.example.newsapi.model.ArticleResult
import retrofit2.Response

class NewsRepository {

    suspend fun getTopHeadlines(page: Int): Response<ArticleResult> {
        return NewsApi.retrofitService.searchForNews(page = page)
    }

}