package com.example.newsapi.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.example.newsapi.model.Article

@SuppressLint("SetTextI18n")
@BindingAdapter("articleTitle")
fun TextView.articleTitle(item: Article){
    item.let {
        text = if (item.title.isEmpty()) "No Title"
        else item.title
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("articleDescription")
fun TextView.articleDescription(item: Article){
    item.let {
        text = if (item.title.isEmpty()) "No Description"
        else item.description
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("articleAuthor")
fun TextView.articleAuthor(item: Article){
    item.let {
        text = if (item.title.isEmpty()) "No Author"
        else item.title
    }
}

@BindingAdapter("visibilityLinear")
fun visibilityLinear(linearLayout: LinearLayout, visible: Boolean){
    linearLayout.isVisible = visible
}

@BindingAdapter("visibilityProgressBar")
fun visibilityProgressBar(progressBar: ProgressBar, visible: Boolean){
    progressBar.isVisible = visible
}

