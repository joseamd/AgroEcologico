package com.univalle.agroecologico

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.univalle.agroecologico.databinding.ItemListaBinding

class ProductoAdapter (private val dataSetProductos: List<Producto>): RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(view: View) :  RecyclerView.ViewHolder(view){
        val viewBinding = ItemListaBinding.bind(view)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto =dataSetProductos.get(position)
        with(holder){
            viewBinding.tvNameProduct.text = producto.name
            viewBinding.tvPrecio.text = producto.precio
            viewBinding.tvUnidadProduct.text = producto.item

        }

    }

    override fun getItemCount(): Int = dataSetProductos.size

}