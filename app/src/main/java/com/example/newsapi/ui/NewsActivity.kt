package com.example.newsapi.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapi.R


class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        supportActionBar?.hide()
    }
}