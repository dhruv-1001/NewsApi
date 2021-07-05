package com.example.newsapi.model

data class ArticleResult(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)