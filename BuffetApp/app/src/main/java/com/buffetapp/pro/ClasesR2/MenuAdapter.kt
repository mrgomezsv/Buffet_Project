package com.buffetapp.pro.ClasesR2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.recyclerview.widget.RecyclerView
import com.buffetapp.pro.R
import com.buffetapp.pro.databinding.TarjetaMenusBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MenuAdapter(private val menuList: MutableList<MenuProducto>,
                  private val listener: OnMenuListener) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.tarjeta_menus, parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menu = menuList[position]

        holder.setListener(menu)

        holder.binding.itemTitulo.text = menu.name
        holder.binding.itemDetalle.text = menu.description

        /*Glide.with(context)
            .load(menu.imgUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_access_time)
            .error(R.drawable.ic_broken_image)
            .centerCrop()
            .into(holder.binding.itemImage)*/
    }

    override fun getItemCount(): Int = menuList.size

    fun add(menuProducto: MenuProducto){
        if (!menuList.contains(menuProducto)){
            menuList.add(menuProducto)
            notifyItemInserted(menuList.size -1)
        }
    }

    fun update(product: MenuProducto){
        val index = menuList.indexOf(product)
        if (index != -1){
            menuList.set(index, product)
            notifyItemChanged(index)
        }
    }

    fun delete(product: MenuProducto){
        val index = menuList.indexOf(product)
        if (index != -1){
            menuList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = TarjetaMenusBinding.bind(view)

        fun setListener(menuProducto: MenuProducto) {
            binding.root.setOnClickListener {
                listener.onClick(menuProducto)
            }
            binding.root.setOnClickListener {
                listener.onLongClick(menuProducto)
                true
            }
        }
    }
}