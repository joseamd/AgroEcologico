package com.univalle.agroecologico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.univalle.agroecologico.databinding.ActivityActualizarListaBinding

class ActualizarListaActivity : AppCompatActivity() {

    private lateinit var productoAdapter: ProductoAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var viewBinding: ActivityActualizarListaBinding

    private lateinit var dbReference:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityActualizarListaBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        productoAdapter = ProductoAdapter(mutableListOf())
        linearLayoutManager = LinearLayoutManager(this)
        viewBinding.rvListaProductos.apply {
            layoutManager = linearLayoutManager
            adapter = productoAdapter


            //Creamos la instancia para la base de datos y la autenticación
            database = FirebaseDatabase.getInstance()
            auth = FirebaseAuth.getInstance()
            dbReference = database.reference.child("Unidad")

        }
    }

    private fun getProductos():MutableList<Producto>{
        val productos = mutableListOf<Producto>()

        val arroz = Producto("Arroz Diana", "$2.000", "libra")
        val frijol = Producto("Frijol Diana", "$2.500", "libra")
        val huevos = Producto("Huevos AAA", "$10.500", "Panel Completo")
        val pezcado = Producto("Trucha", "$15.500", "Filete completo")

        productos.add(arroz)
        productos.add(frijol)
        productos.add(huevos)
        productos.add(pezcado)
        productos.add(arroz)
        productos.add(frijol)
        productos.add(huevos)
        productos.add(pezcado)

        return productos

    }


    fun ActualizarUnidad(view:View){
        actualizarUnidad()
    }

    private fun actualizarUnidad(){
        val intent = Intent(this,CrearPuestoActivity::class.java)
        startActivity(intent)
        finish()
    }

}