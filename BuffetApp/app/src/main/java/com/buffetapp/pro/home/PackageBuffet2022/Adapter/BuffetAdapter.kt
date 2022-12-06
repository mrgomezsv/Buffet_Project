package com.buffetapp.pro.home.PackageBuffet2022.Adapter.Model.Repository

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.buffetapp.pro.ClasesR2.MenuProducto
import com.buffetapp.pro.R
import com.buffetapp.pro.databinding.TarjetaMenusBinding

class BuffetAdapter : RecyclerView.Adapter<BuffetAdapter.MyViewHolder>(){

    private lateinit var mListener : onitemClickListener

    interface onitemClickListener{

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onitemClickListener){

        mListener = listener
    }

    private val buffetList = ArrayList<Buffet>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent, false)

        return MyViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = buffetList[position]

        holder.nameBuffet.text = currentitem.name
        holder.descriptionBuffet.text = currentitem.description
        holder.reservation.text = currentitem.reservation
    }

    override fun getItemCount(): Int {
        return buffetList.size
    }

    fun updateBuffetList(buffetList : List<Buffet>){

        this.buffetList.clear()
        this.buffetList.addAll(buffetList)
        notifyDataSetChanged()
    }


    class MyViewHolder(itemView : View, listener: onitemClickListener) : RecyclerView.ViewHolder(itemView){

        val nameBuffet : TextView = itemView.findViewById(R.id.title)
        val descriptionBuffet : TextView = itemView.findViewById(R.id.news_resume)
        val reservation : TextView = itemView.findViewById(R.id.news_reservation)

        init {

            itemView.setOnClickListener {

                listener.onItemClick(adapterPosition)
            }

        }

    }
}