package com.univalle.agroAppMovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.univalle.agroAppMovil.databinding.ActivityActualizarListaBinding


class ActualizarListaActivity : AppCompatActivity(), OnClickListener {

    private lateinit var productoAdapter: ProductoAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var viewBinding: ActivityActualizarListaBinding
    private lateinit var dbReference:DatabaseReference
    var c=0
    val productos = mutableListOf<Producto>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityActualizarListaBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        getProductosFromFirebase()
        productoAdapter = ProductoAdapter(productos,this)
        linearLayoutManager = LinearLayoutManager(this)

    }

     private fun getProductosFromFirebase(){

         dbReference = FirebaseDatabase.getInstance().getReference("Unidad")

        dbReference.addValueEventListener(object : ValueEventListener{
             override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (dataSnapshot in dataSnapshot.getChildren()) {
                        c=c+1
                        var codigo = c.toString()
                        var name =  dataSnapshot.child("nameUnid").getValue(String::class.java)
                        var price = dataSnapshot.child("price").getValue(String::class.java)
                        var selection = dataSnapshot.child("selection").getValue(String::class.java)
                        var url = dataSnapshot.child("url").getValue(String::class.java)


                        var prod = Producto(codigo!!, name!!, price!!, selection!!, url!!)
                        productos.add(prod)

                    }
                    productoAdapter = ProductoAdapter(productos,this@ActualizarListaActivity)
                    viewBinding.rvListaProductos.apply {
                        layoutManager = linearLayoutManager
                        adapter = productoAdapter

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }


    override fun onClick(producto: Producto) {
        val intent = Intent(this,EditarUnidadActivity::class.java)
        intent.putExtra("Dato1", producto.getFullCodigo())
        intent.putExtra("Dato2", producto.getFullName())
        intent.putExtra("Dato3", producto.getFullPrice())
        intent.putExtra("Dato4", producto.getFullItem())
        intent.putExtra("Dato5", producto.getFullUrl())

        startActivity(intent)
        finish()
    }

}