package com.example.newsapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapi.apiservice.NewsApi
import com.example.newsapi.model.ArticleResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsViewModel: ViewModel() {

    lateinit var _response: ArticleResult
    var displayText = MutableLiveData("Fetching Data")

    init {
        NewsApi.retrofitService.searchForCountry("us").enqueue(object : Callback<ArticleResult> {

            override fun onResponse(call: Call<ArticleResult>, response: Response<ArticleResult>) {
                _response = response.body()!!
                updateDisplayData(errorMessage = "Success")
            }

            override fun onFailure(call: Call<ArticleResult>, t: Throwable) {
                updateDisplayData(errorMessage = t.message.toString())
            }

        })
    }

    private fun updateDisplayData(errorMessage: String){
        displayText.value = errorMessage
    }
    
}
