package com.univalle.agroecologico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
    }

    fun crearPuestoVenta(view: View){
        crearPuesto()
    }
    fun crearUnidad(view: View){
        crearUnidades()
    }

    private fun crearPuesto(){
        startActivity(Intent(this, CrearPuestoActivity::class.java))
    }
    private fun crearUnidades(){
        startActivity(Intent(this, AdicionUnidadActivity::class.java))
    }
}