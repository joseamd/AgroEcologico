package com.univalle.agroecologico

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class CrearPuestoActivity : AppCompatActivity() {

    private lateinit var txtNamePuesto: EditText
    private lateinit var txtCedula: EditText
    private lateinit var txtPassword: EditText
    private lateinit var txtCelular: EditText
    private lateinit var txtEmail: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_puesto)

        txtNamePuesto=findViewById(R.id.txtNamePuesto)
        txtCedula=findViewById(R.id.txtCedula)
        txtPassword=findViewById(R.id.txtPasswordPuesto)
        txtCelular=findViewById(R.id.txtCelular)
        txtEmail=findViewById(R.id.txtEmailPuesto)
        progressBar=findViewById(R.id.progressBarPuesto)
        //Creamos la instancia para la base de datos y la autenticación
        database= FirebaseDatabase.getInstance()
        auth=FirebaseAuth.getInstance()

        dbReference=database.reference.child("puestoVenta")
    }
    fun crearpuesto(view:View){
        createNewPuesto()
    }

    private fun createNewPuesto(){
        val namePuesto:String=txtNamePuesto.text.toString()
        val cedula:String=txtCedula.text.toString()
        val password:String=txtPassword.text.toString()
        val celular:String=txtCelular.text.toString()
        val email:String=txtEmail.text.toString()

        //comparamos que los campos no esten vacios
        if(!TextUtils.isEmpty(namePuesto) && !TextUtils.isEmpty(cedula)
            && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(celular)&& !TextUtils.isEmpty(email)){
            progressBar.visibility= View.VISIBLE

            val puesto:FirebaseUser?=auth.currentUser

            //se instancia un objeto de la clase puestoVenta
            val puestoVenta = PuestoVenta(namePuesto,cedula,password,celular,email)

            //se envía el objeto creado a la bd y id del puesto será la cedula
            dbReference.child ("puesto:"+cedula).setValue (puestoVenta);
            verifyEmail(puesto)
            action()

        }

    }
    private fun action(){
        val intent = Intent(this,AdminActivity::class.java)
        startActivity(intent)
        finish();
    }

    private fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
                    task ->
                if(task.isComplete){
                    Toast.makeText(baseContext, "Puesto creado correctamente",
                        Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(baseContext, "Error al crear el puesto",
                        Toast.LENGTH_LONG).show()
                    progressBar.visibility=View.INVISIBLE
                }
            }
    }


}