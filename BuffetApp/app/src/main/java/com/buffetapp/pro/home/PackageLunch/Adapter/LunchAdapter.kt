package com.buffetapp.pro.home.PackageLunch.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.buffetapp.pro.R
import com.buffetapp.pro.home.PackageLunch.Model.Lunch

class LunchAdapter : RecyclerView.Adapter<LunchAdapter.MyViewHolder>(){

    private val lunchList = ArrayList<Lunch>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = lunchList[position]

        holder.nameLunch.text = currentitem.name
        holder.descriptionLunch.text = currentitem.description
    }

    override fun getItemCount(): Int {
        return lunchList.size
    }

    fun updateLunchList(lunchList : List<Lunch>){

        this.lunchList.clear()
        this.lunchList.addAll(lunchList)
        notifyDataSetChanged()
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val nameLunch : TextView = itemView.findViewById(R.id.title)
        val descriptionLunch : TextView = itemView.findViewById(R.id.news_resume)

    }
}