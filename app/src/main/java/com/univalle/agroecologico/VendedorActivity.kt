package com.univalle.agroecologico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class VendedorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendedor)
    }
    fun editarPuestoVenta(view: View){
        editarPuesto()
    }
    fun actualizarLista(view: View){
        editarUnidades()
    }

    private fun editarPuesto(){
        startActivity(Intent(this, EditarPuestoActivity::class.java))
    }
    private fun editarUnidades(){
        startActivity(Intent(this, ActualizarListaActivity::class.java))
    }
}