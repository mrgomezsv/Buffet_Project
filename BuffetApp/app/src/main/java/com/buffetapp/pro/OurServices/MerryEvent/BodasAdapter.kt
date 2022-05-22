package com.buffetapp.pro.OurServices.MerryEvent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buffetapp.pro.R
import com.buffetapp.pro.databinding.ItemFotosBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class BodasAdapter(private val bodasList: MutableList<Bodas>,
                   private val listener: OnBodasListener
) :
    RecyclerView.Adapter<BodasAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_fotos, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bodas = bodasList[position]

        holder.setListener(bodas)

        holder.binding.tvNameSocial.text = bodas.name

        Glide.with(context)
            .load(bodas.imgUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_access_time)
            .error(R.drawable.ic_broken_image)
            .centerCrop()
            .into(holder.binding.imgSocial)
    }

    override fun getItemCount(): Int = bodasList.size

    fun add(bodas: Bodas){
        if (!bodasList.contains(bodas)){
            bodasList.add(bodas)
            notifyItemInserted(bodasList.size - 1)
        }
    }

    fun update(bodas: Bodas){
        val index = bodasList.indexOf(bodas)
        if (index != -1){
            bodasList.set(index, bodas)
            notifyItemChanged(index)
        }
    }

    fun delete(bodas: Bodas){
        val index = bodasList.indexOf(bodas)
        if (index != -1){
            bodasList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemFotosBinding.bind(view)

        fun setListener(bodas: Bodas){
            binding.root.setOnClickListener {
                listener.onClick(bodas)
            }

            binding.root.setOnLongClickListener {
                listener.onLongClick(bodas)
                true
            }
        }
    }
}