package com.buffetapp.pro.OurServices.YearEvent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buffetapp.pro.R
import com.buffetapp.pro.YearActivity
import com.buffetapp.pro.databinding.ItemFotosBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class YearAdapter(private val yearList: MutableList<Year>,
                  private val listener: YearActivity
) :
    RecyclerView.Adapter<YearAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_fotos, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val year = yearList[position]

        holder.setListener(year)

        holder.binding.tvNameSocial.text = year.name

        Glide.with(context)
            .load(year.imgUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_access_time)
            .error(R.drawable.ic_broken_image)
            .centerCrop()
            .into(holder.binding.imgSocial)
    }

    override fun getItemCount(): Int = yearList.size

    fun add(year: Year){
        if (!yearList.contains(year)){
            yearList.add(year)
            notifyItemInserted(yearList.size - 1)
        }
    }

    fun update(year: Year){
        val index = yearList.indexOf(year)
        if (index != -1){
            yearList.set(index, year)
            notifyItemChanged(index)
        }
    }

    fun delete(year: Year){
        val index = yearList.indexOf(year)
        if (index != -1){
            yearList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemFotosBinding.bind(view)

        fun setListener(year: Year){
            binding.root.setOnClickListener {
                listener.onClick(year)
            }

            binding.root.setOnLongClickListener {
                listener.onLongClick(year)
                true
            }
        }
    }
}