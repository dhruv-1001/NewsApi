package com.example.newsapi.model

data class ArticleResult(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)