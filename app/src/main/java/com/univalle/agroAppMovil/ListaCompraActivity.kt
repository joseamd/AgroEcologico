package com.univalle.agroAppMovil

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.univalle.agroAppMovil.databinding.ActivityActualizarListaBinding
import com.univalle.agroAppMovil.databinding.ActivityListaCompraBinding
import com.univalle.agroAppMovil.databinding.ActivityListaCompraBinding.inflate
import kotlinx.android.synthetic.main.activity_carrito.*
import kotlinx.android.synthetic.main.activity_lista_compra.*

enum class ProviderType{
    BASIC,
    GOOGLE,
    FACEBOOK
}

class ListaCompraActivity : AppCompatActivity(), OnClickListener{
    private lateinit var productoAdapter: ProductoAdapter
    private lateinit var btnVerCarrito: Button
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var viewBinding: ActivityListaCompraBinding
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    var c=0
    val productos = mutableListOf<Producto>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityListaCompraBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        btnVerCarrito = findViewById(R.id.btnVerCarrito)

        //setup
        val bundle:Bundle? = intent.extras
        val email: String? = bundle?.getString("email")
        val provider: String? = bundle?.getString("provider")
        if (email != null) {
            if (provider != null) {
                setup(email, provider)
            }
        }else{
            setup("","")
        }

        //Guardado de dato
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()

        getProductosFromFirebase()
        linearLayoutManager = LinearLayoutManager(this)
        //Creamos la instancia para la base de datos y la autenticación
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database.reference.child("usuario")


    }

    fun verCarrito(view: View){
        startActivity(Intent(this, CarritoActivity::class.java))
    }

    private fun setup(email: String, provider: String) {
        title = "Inicio"
        emailTextView.text=email
        providerTextView.text=provider

        logOutButton.setOnClickListener{
            //Borrado de dato
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            if (provider == ProviderType.FACEBOOK.name){
                LoginManager.getInstance().logOut()
            }

            FirebaseAuth.getInstance().signOut()
            onBackPressed()

        }
    }


    private fun getProductosFromFirebase(){

        dbReference = FirebaseDatabase.getInstance().getReference("Unidad")

        dbReference.addValueEventListener(object : ValueEventListener {
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
                    productoAdapter = ProductoAdapter(productos,this@ListaCompraActivity)
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
        val intent = Intent(this,DetalleActivity::class.java)
        intent.putExtra("Dato1", producto.getFullName())
        intent.putExtra("Dato2", producto.getFullUrl())
        intent.putExtra("Dato3", producto.getFullItem())
        intent.putExtra("Dato4", producto.getFullPrice())
        intent.putExtra("Dato5", producto.getFullCodigo())

        startActivity(intent)
    }

}