package com.univalle.agroAppMovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_carrito.*
import com.google.firebase.database.DataSnapshot
import com.univalle.agroAppMovil.databinding.ActivityCarritoBinding


class CarritoActivity : AppCompatActivity() , OnClickListener{
    private var idCliente = "IeRhAkIJckctYUxXvm0D03LaD083"
    private lateinit var productoAdapter: ProductoAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var viewBinding: ActivityCarritoBinding
    private lateinit var dbReference:DatabaseReference
    var c=0
    var suma=0
    val productos = mutableListOf<Producto>()

    private lateinit var tvTotal: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        tvTotal = findViewById(R.id.tvTotal)
        setTitle("Carrito de Compras")

        viewBinding = ActivityCarritoBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        getProductosFromFirebase()
        productoAdapter = ProductoAdapter(productos, this)
        linearLayoutManager = LinearLayoutManager(this)

    }

//    private fun getProductosFromFirebase(){
//        dbReference.child("usuario").child(idCliente).addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (item in dataSnapshot.children) {
//                        val idProducto = item.key.toString()
//                        dbReference.child("Unidad").child(idProducto).addValueEventListener(object : ValueEventListener{
//                            override fun onDataChange(snapshot: DataSnapshot) {
//                                var codigo = idProducto
//                                var name =  dataSnapshot.child("nameUnid").getValue(String::class.java)
//                                var price = dataSnapshot.child("price").getValue(String::class.java)
//                                var selection = dataSnapshot.child("selection").getValue(String::class.java)
//                                var url = dataSnapshot.child("url").getValue(String::class.java)        //
//
//                                var prod = Producto(codigo!!, name!!, price!!, selection!!, url!!)
//                                productos.add(prod)
//                                val precio: Int? = price.toInt()
//                                suma = suma+ precio!!
//                            }
//
//                            override fun onCancelled(error: DatabaseError) {
//                                TODO("Not yet implemented")
//                            }
//
//                        })
//                        }
//                      val textView1 = findViewById<View>(R.id.tvTotal) as TextView
//                      textView1.text = "Total: $"+suma
//                        productoAdapter = ProductoAdapter(productos,this@CarritoActivity)
//                        viewBinding.rvListaCarrito.apply {
//                        layoutManager = linearLayoutManager
//                        adapter = productoAdapter
//
//                    }
//                    }else {
//                    val textView1 = findViewById<View>(R.id.tvTotal) as TextView
//                    textView1.text = "Total: $0"
//                }
//
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                }
//
//            })
//        }
//
//    override fun onClick(producto: Producto) {
//        TODO("Not yet implemented")
//    }
//}




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
                        val precio: Int? = price.toInt()
                        suma = suma+ precio!!

                    }
                    val textView1 = findViewById<View>(R.id.tvTotal) as TextView
                    textView1.text = "Total: $"+suma
                    productoAdapter = ProductoAdapter(productos,this@CarritoActivity)
                    viewBinding.rvListaCarrito.apply {
                        layoutManager = linearLayoutManager
                        adapter = productoAdapter

                    }
                }else {
                    val textView1 = findViewById<View>(R.id.tvTotal) as TextView
                    textView1.text = "Total: $0"
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    override fun onClick(producto: Producto) {

    }


}