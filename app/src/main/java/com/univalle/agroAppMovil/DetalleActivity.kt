package com.univalle.agroAppMovil

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.univalle.agroAppMovil.databinding.ActivityDetalleBinding
import kotlinx.android.synthetic.main.activity_detalle.view.*
import android.graphics.BitmapFactory

import android.R.string
import android.graphics.Bitmap
import android.util.Base64
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception


class DetalleActivity : AppCompatActivity() {

    private lateinit var tvProducto: TextView
    private lateinit var tvDescripcion: TextView
    private lateinit var tvPrecioProd: TextView
    private lateinit var btnAgregar: Button
    private lateinit var context: Context
    private lateinit var firebaseImagen: ImageView

    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityDetalleBinding

    private var idCliente = "IeRhAkIJckctYUxXvm0D03LaD083"
    private lateinit var idProducto: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        tvProducto = findViewById(R.id.tvProducto)
        firebaseImagen = findViewById(R.id.firebaseImagen)
        tvDescripcion = findViewById(R.id.tvDescripcion)
        tvPrecioProd = findViewById(R.id.tvPrecioProd)
        btnAgregar = findViewById(R.id.btnAgregar)

        val dato1 = intent.getStringExtra("Dato1")
        val dato2 = intent.getStringExtra("Dato2")
        val dato3 = intent.getStringExtra("Dato3")
        val dato4 = intent.getStringExtra("Dato4")
        val dato5 = intent.getStringExtra("Dato5")


        //cargarmos la información en el formulario editar unidad
        val textView1 = findViewById<View>(R.id.tvProducto) as TextView
        textView1.text = "$dato1"
        val textView3 = findViewById<View>(R.id.tvDescripcion) as TextView
        textView3.text = "$dato3"
        val textView4 = findViewById<View>(R.id.tvPrecioProd) as TextView
        textView4.text = "$dato4"


//        Glide.with(context)
//            .load(dato2) //cual es la url que debe cargar
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
//            .into(binding.firebaseImagen)//en donde lo vamos a cargar

        //Creamos la instancia para la base de datos y la autenticación
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database.reference.child("usuario")

        idProducto = dato5.toString()


    }

    fun addCarrito(view: View){
        agregarCarrito()
    }

    private fun agregarCarrito() {
        dbReference.child(idCliente).child("carrito").child(idProducto).setValue(true)
        finish()
    }


}