package com.buffetapp.pro.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.buffetapp.pro.R

class NewsAdapter(val newsList: ArrayList<News>): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bindItems(news:News){
            val image = itemView.findViewById<ImageView>(R.id.newImage)
            val id = itemView.findViewById<TextView>(R.id.new_id)
            val title = itemView.findViewById<TextView>(R.id.title)
            val resume = itemView.findViewById<TextView>(R.id.news_resume)


            title.text = news.title.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}