package com.univalle.agroAppMovil

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.univalle.agroAppMovil.databinding.ItemListaBinding

class ProductoAdapter(private val dataSetProductos: List<Producto>, private val listener: OnClickListener): RecyclerView.Adapter<ProductoAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(view: View) :  RecyclerView.ViewHolder(view){
        val viewBinding = ItemListaBinding.bind(view)
        fun setListener(producto:Producto){
            viewBinding.root.setOnClickListener {
                listener.onClick(producto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto =dataSetProductos.get(position)
        with(holder){
            setListener(producto)
            viewBinding.tvId.text = producto.codigo
            viewBinding.tvNameProduct.text = producto.name
            viewBinding.tvPrecio.text = producto.precio
            viewBinding.tvUnidadProduct.text = producto.item

            Glide.with(context)
                .load(producto.url) //cual es la url que debe cargar
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(viewBinding.ivImageProduct)//en donde lo vamos a cargar

        }

    }

    override fun getItemCount(): Int = dataSetProductos.size

}