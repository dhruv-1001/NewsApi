package com.example.newsapi.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapi.R
import com.example.newsapi.adapter.NewsListAdapter
import com.example.newsapi.databinding.FragmentArticleListBinding
import com.example.newsapi.repository.NewsRepository
import com.example.newsapi.viewmodel.ArticleListViewModel
import com.example.newsapi.viewmodel.ArticleListViewModelFactory
import com.example.newsapi.viewmodel.State

class ArticleListFragment : Fragment() {

    private lateinit var binding: FragmentArticleListBinding
    private lateinit var viewModel: ArticleListViewModel
    private lateinit var newsListAdapter: NewsListAdapter
    var isLastPage = false
    var isLoading = false
    var isScrolling = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_article_list, container, false
        )

        val newsRepo = NewsRepository()
        val viewModelFactory = ArticleListViewModelFactory(newsRepo)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ArticleListViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        newsListAdapter = NewsListAdapter()
        binding.articleList.apply {
            adapter = newsListAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@ArticleListFragment.scrollListener)
        }

        newsListAdapter.navigateOnClickListener {
            val bundle = Bundle()
            bundle.apply { putSerializable("article", it) }
            findNavController().navigate(R.id.action_articleListFragment_to_articleFragment, bundle)
        }

        return binding.root

    }

    override fun onResume() {
        super.onResume()

        viewModel.newsList.observe(viewLifecycleOwner, { response->
            when(response){
                is State.Success -> {
                    viewModel.viewProgressbar.value = false
                    viewModel.viewArticles.value = true
                    response.data?.let {
                        newsListAdapter.data.submitList(it.articles.toList())
                        val totalPages = it.totalResults / 10 + 2
                        isLastPage = viewModel.currentPage == totalPages
                    }
                }
                is State.Error -> {
                    makeToast(response.message.toString())
                }
            }
        })

    }

    private fun makeToast(message: String){
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private val scrollListener = object : RecyclerView.OnScrollListener(){

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = binding.articleList.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= 10
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                viewModel.fetchNews()
                isScrolling = false
            }
            else{
                binding.articleList.setPadding(0,0,0,0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

    }

}