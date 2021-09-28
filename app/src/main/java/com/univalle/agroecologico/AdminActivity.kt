package com.univalle.agroecologico

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class AdminActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        auth= FirebaseAuth.getInstance()

    }

    fun signOut(view:View){
        cerrarSesion()
    }


    fun crearPuestoVenta(view: View){
        crearPuesto()
    }
    fun crearUnidad(view: View){
        crearUnidades()
    }

    private fun cerrarSesion() {
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun crearPuesto(){
        startActivity(Intent(this, CrearPuestoActivity::class.java))
    }
    private fun crearUnidades(){
        startActivity(Intent(this, AdicionUnidadActivity::class.java))
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