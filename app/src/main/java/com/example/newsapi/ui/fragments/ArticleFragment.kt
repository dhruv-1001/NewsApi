package com.example.newsapi.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.newsapi.R
import com.example.newsapi.databinding.FragmentArticleBinding

class ArticleFragment : Fragment() {

    private val args: ArticleFragmentArgs by navArgs()
    private lateinit var binding: FragmentArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_article, container, false
        )

        val article = args.article
        val webView: WebView = binding.wvWebView

        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        return binding.root
    }

}