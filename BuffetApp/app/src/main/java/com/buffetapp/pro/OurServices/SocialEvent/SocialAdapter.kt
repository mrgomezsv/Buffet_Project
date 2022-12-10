package com.buffetapp.pro.OurServices.SocialEvent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buffetapp.pro.R
import com.buffetapp.pro.databinding.ItemFotosBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class SocialAdapter(private val socialList: MutableList<Social>,
                     private val listener: OnSocialListener
) :
    RecyclerView.Adapter<SocialAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_fotos, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val social = socialList[position]

        holder.setListener(social)

        holder.binding.tvNameSocial.text = social.name

        Glide.with(context)
            .load(social.imgUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_access_time)
            .error(R.drawable.ic_broken_image)
            .centerCrop()
            .into(holder.binding.imgSocial)
    }

    override fun getItemCount(): Int = socialList.size

    fun add(social: Social){
        if (!socialList.contains(social)){
            socialList.add(social)
            notifyItemInserted(socialList.size - 1)
        }
    }

    fun update(social: Social){
        val index = socialList.indexOf(social)
        if (index != -1){
            socialList.set(index, social)
            notifyItemChanged(index)
        }
    }

    fun delete(social: Social){
        val index = socialList.indexOf(social)
        if (index != -1){
            socialList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemFotosBinding.bind(view)

        fun setListener(social: Social){
            binding.root.setOnClickListener {
                listener.onClick(social)
            }

            binding.root.setOnLongClickListener {
                listener.onLongClick(social)
                true
            }
        }
    }
}