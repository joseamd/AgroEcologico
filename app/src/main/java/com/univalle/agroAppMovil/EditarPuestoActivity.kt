package com.univalle.agroAppMovil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class EditarPuestoActivity : AppCompatActivity() {

    private lateinit var txtCedula: EditText
    private lateinit var txtNamePuesto: EditText
    private lateinit var txtPassword: EditText
    private lateinit var txtCelular: EditText
    private lateinit var txtEmail: EditText

    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_puesto)

        txtCedula=findViewById(R.id.txtCedulaEdit)
        txtNamePuesto=findViewById(R.id.txtEditNamePuesto)
        txtPassword=findViewById(R.id.txtEditPasswordPuesto)
        txtCelular=findViewById(R.id.txtEditCelular)
        txtEmail=findViewById(R.id.txtEditEmailPuesto)

        progressBar=findViewById(R.id.progressBarPuestoEdit)
        //Creamos la instancia para la base de datos y la autenticación
        database= FirebaseDatabase.getInstance()
        auth=FirebaseAuth.getInstance()

        dbReference=database.reference.child("puestoVenta")

    }

    fun EditPuesto(view: View){
        EditNewPuesto()
    }

    private fun EditNewPuesto(){

        val cedula:String=txtCedula.text.toString()
        val namePuesto:String=txtNamePuesto.text.toString()
        val password:String=txtPassword.text.toString()
        val celular:String=txtCelular.text.toString()
        val email:String=txtEmail.text.toString()


        //comparamos que los campos no esten vacios
        if(!TextUtils.isEmpty(namePuesto) && !TextUtils.isEmpty(cedula)){
            progressBar.visibility= View.VISIBLE

            }
            val puesto: FirebaseUser?=auth.currentUser

            //se modifica solo el campo solicitado
        val puestoVenta = PuestoVenta(namePuesto,cedula,password,celular,email)

            //se envía el objeto creado a la bd y id del puesto será la cedula
            dbReference.child ("puesto:"+cedula).setValue (puestoVenta);
            Toast.makeText(baseContext, "Actualización correcta",Toast.LENGTH_LONG).show()

            //dbReference.child ("puesto:"+cedula).setValue (namePuesto);
            //Toast.makeText(baseContext, "Puesto modificado correctamente",
            //Toast.LENGTH_LONG).show()
            action()

        }

    private fun action(){
        val intent = Intent(this,EditarPuestoActivity::class.java)
        startActivity(intent)
        finish();

    }

}