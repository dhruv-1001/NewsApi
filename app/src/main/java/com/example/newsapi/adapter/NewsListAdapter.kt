package com.example.newsapi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapi.R
import com.example.newsapi.model.Article

class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.ViewHolder>(){

    private val differentItem = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val data = AsyncListDiffer(this, differentItem)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.article_item,
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = data.currentList[position]

        if (article.author.isEmpty()) holder.author.text = "No Title"
        else holder.author.text = "~${article.author}"

        if (article.description.isEmpty()) holder.author.text = "No Sescription"
        else holder.description.text = article.description

        if (article.author.isEmpty()) holder.author.text = "Author data missing"
        else holder.title.text = article.title

    }

    override fun getItemCount(): Int {
        return data.currentList.size
    }

    class ViewHolder (view: View): RecyclerView.ViewHolder(view){

        val author: TextView = view.findViewById(R.id.author)
        val description: TextView = view.findViewById(R.id.description)
        val title: TextView = view.findViewById(R.id.title)

    }

}
