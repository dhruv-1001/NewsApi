package com.example.newsapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapi.repository.NewsRepository

class ArticleListViewModelFactory(
    private val newsRepo: NewsRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleListViewModel(newsRepo) as T
    }

}