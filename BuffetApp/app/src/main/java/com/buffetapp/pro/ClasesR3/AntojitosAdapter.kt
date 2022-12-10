package com.buffetapp.pro.ClasesR3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buffetapp.pro.R
import com.buffetapp.pro.databinding.TarjetaAntojitosBinding

class AntojitosAdapter(private val antojitosList: MutableList<AntojitosProducto>,
                       private val listener: OnAntojitosListener
) :
    RecyclerView.Adapter<AntojitosAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.tarjeta_antojitos, parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val antojitos = antojitosList[position]

        holder.setListener(antojitos)

        holder.binding.itemTituloA.text = antojitos.name
        holder.binding.itemDetalleA.text = antojitos.description

    }

    override fun getItemCount(): Int = antojitosList.size

    fun add(antonijosProducto: AntojitosProducto){
        if (!antojitosList.contains(antonijosProducto)){
            antojitosList.add(antonijosProducto)
            notifyItemInserted(antojitosList.size -1)
        }
    }

    fun update(product: AntojitosProducto){
        val index = antojitosList.indexOf(product)
        if (index != -1){
            antojitosList.set(index, product)
            notifyItemChanged(index)
        }
    }

    fun delete(product: AntojitosProducto){
        val index = antojitosList.indexOf(product)
        if (index != -1){
            antojitosList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = TarjetaAntojitosBinding.bind(view)

        fun setListener(antonijosProducto: AntojitosProducto) {
            binding.root.setOnClickListener {
                listener.onClick(antonijosProducto)
            }
            binding.root.setOnClickListener {
                listener.onLongClick(antonijosProducto)
                true
            }
        }
    }
}