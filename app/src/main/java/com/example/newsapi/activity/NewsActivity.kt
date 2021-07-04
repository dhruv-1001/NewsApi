package com.example.newsapi.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.newsapi.databinding.ActivityNewsBinding
import com.example.newsapi.R
import com.example.newsapi.viewmodel.NewsViewModel


class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)

        supportActionBar?.hide()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

    }
}