package com.example.newsapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapi.model.ArticleResult
import com.example.newsapi.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


class ArticleListViewModel(
    private val newsRepo: NewsRepository
): ViewModel() {

    var viewProgressbar: MutableLiveData<Boolean> = MutableLiveData(true)
    var viewArticles: MutableLiveData<Boolean> = MutableLiveData(false)

    var newsList: MutableLiveData<State<ArticleResult>> = MutableLiveData()
    private var newsResponse: ArticleResult? = null

    var displayText = MutableLiveData("Fetching Data")
    private var currentPage = 1

    init{
        fetchNews(currentPage)
    }

    private fun fetchNews(page: Int) {
        viewProgressbar.value = true
        CoroutineScope(Dispatchers.Main).launch{
            val callResponse = newsRepo.getTopHeadlines(page = page)
            newsList.postValue(convertResponse(callResponse))
        }
        viewProgressbar.value = false
        viewArticles.value = true
    }

    private fun convertResponse(callResponse: Response<ArticleResult>): State<ArticleResult>{
        if (callResponse.isSuccessful){
            callResponse.body()?.let { result ->
                currentPage++
                if (newsResponse == null) {
                    newsResponse = result
                }
                else{
                    val oldResponse = newsResponse?.articles
                    val addedResponse = result.articles
                    oldResponse?.addAll(addedResponse)
                }
                return State.Success(newsResponse?: result)
            }
        }
        return State.Error(callResponse.message())
    }
    
}

open class State<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : State<T>(data)
    class Error<T>(message: String, data: T? = null) : State<T>(data, message)
}
