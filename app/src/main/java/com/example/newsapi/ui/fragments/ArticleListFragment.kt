package com.example.newsapi.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_article_list, container, false
        )

        val newsRepo = NewsRepository()
        val viewModelFactory = ArticleListViewModelFactory(newsRepo)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ArticleListViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        newsListAdapter = NewsListAdapter()
        binding.articleList.adapter = newsListAdapter

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
//                        val pageAt = it.totalResults / 10 + 2
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

    val scrollListener = object : RecyclerView.OnScrollListener(){

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

        }

    }

}