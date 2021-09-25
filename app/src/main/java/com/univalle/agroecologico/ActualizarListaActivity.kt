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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ActualizarListaActivity : AppCompatActivity() {
    private lateinit var txtNameUnit: EditText
    private lateinit var txtCodeUnit: EditText
    private lateinit var txtPrice: EditText
    private lateinit var spinner: Spinner

    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_lista)

        txtNameUnit=findViewById(R.id.txtNameUnidadEdit)
        txtCodeUnit=findViewById(R.id.txtCodeUnitEdit)
        txtPrice=findViewById(R.id.txtPrecioEdit)
        spinner=findViewById(R.id.listaUnidadesEdit)
        progressBar=findViewById(R.id.progressBarUnidadEdit)

        //Creamos la instancia para la base de datos y la autenticación
        database= FirebaseDatabase.getInstance()
        auth=FirebaseAuth.getInstance()
        dbReference=database.reference.child("Unidad")

    }

    fun ActualizarUnidad(view:View){
        actualizarUnidad()
    }

    private fun actualizarUnidad(){
        val nameUnit:String=txtNameUnit.text.toString()
        val codeUnit:String=txtCodeUnit.text.toString()
        val price:String=txtPrice.text.toString()
        val selection:String=spinner.selectedItem.toString()


        //comparamos que los campos no esten vacios
        if(!TextUtils.isEmpty(nameUnit) && !TextUtils.isEmpty(price)
            && !TextUtils.isEmpty(selection)){

            progressBar.visibility= View.VISIBLE
            val unidad:FirebaseUser?=auth.currentUser

            //se instancia un objeto de la clase Unidades
            val unidad1 = Unidades(nameUnit,price,selection)

            //se actualiza el objeto creado a la bd
            dbReference.child ("Codigo: "+codeUnit).setValue (unidad1);
            Toast.makeText(baseContext, "Unidad actualizada",
                Toast.LENGTH_LONG).show()

            action()

        }

    }
    private fun action(){
        val intent = Intent(this,ActualizarListaActivity::class.java)
        startActivity(intent)
        progressBar.visibility=View.INVISIBLE
        finish();

    }
}