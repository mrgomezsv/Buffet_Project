package com.buffetapp.pro.home.PackageSnack.AdapterS

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.buffetapp.pro.R
import com.buffetapp.pro.home.PackageSnack.ModelS.Snack

class SnackAdapter : RecyclerView.Adapter<SnackAdapter.MyViewHolder>(){

    private val snackList = ArrayList<Snack>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = snackList[position]

        holder.nameSnack.text = currentitem.name
        holder.descriptionSnack.text = currentitem.description
        holder.reservationSnack.text = currentitem.reservation
    }

    override fun getItemCount(): Int {
        return snackList.size
    }

    fun updateSnackList(snackList : List<Snack>){

        this.snackList.clear()
        this.snackList.addAll(snackList)
        notifyDataSetChanged()
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val nameSnack : TextView = itemView.findViewById(R.id.title)
        val descriptionSnack : TextView = itemView.findViewById(R.id.news_resume)
        val reservationSnack : TextView = itemView.findViewById(R.id.news_reservation)
    }
}