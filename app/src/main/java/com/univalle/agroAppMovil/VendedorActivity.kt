package com.univalle.agroAppMovil

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth


class VendedorActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendedor)

        auth= FirebaseAuth.getInstance()

    }

    fun signOut(view:View){
        cerrarSesion()
    }

    fun editarPuestoVenta(view: View){
        editarPuesto()
    }
    fun actualizarLista(view: View){
        editarUnidades()
    }

    private fun cerrarSesion() {
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun editarPuesto(){
        startActivity(Intent(this, EditarPuestoActivity::class.java))
    }
    private fun editarUnidades(){
        startActivity(Intent(this, ActualizarListaActivity::class.java))
    }

    override fun  onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {

        if(keyCode== KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder(this)
                .setTitle("")
                .setMessage("¿Desea salir de Agroecológico?")
                .setPositiveButton("Si",
                    DialogInterface.OnClickListener  { dialog, which  ->
                        finish()
                    })
                .setNegativeButton("Cancelar",
                    DialogInterface.OnClickListener  { dialog, which  ->
                        dialog.dismiss() })
                .show() }
        return super.onKeyDown(keyCode, event) }
}