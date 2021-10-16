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

class RegisterActivity : AppCompatActivity() {

    private lateinit var txtName: EditText
    private lateinit var txtLastName: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtName=findViewById(R.id.txtName)
        txtLastName=findViewById(R.id.txtLastName)
        txtEmail=findViewById(R.id.txtEmail)
        txtPassword=findViewById(R.id.txtPassword)
        progressBar=findViewById(R.id.progressBar)
        //Creamos la instancia para la base de datos y la autenticación
        database= FirebaseDatabase.getInstance()
        auth=FirebaseAuth.getInstance()

        dbReference=database.reference.child("usuario")

    }

    fun register(view:View){
        createNewAccount()
    }
    private fun createNewAccount(){
        val name:String=txtName.text.toString()
        val lastName:String=txtLastName.text.toString()
        val email:String=txtEmail.text.toString()
        val password:String=txtPassword.text.toString()

        //comparamos que los campos no esten vacios
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            progressBar.visibility= View.VISIBLE

            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){
                        task ->
                    if(task.isComplete){
                        val usuario:FirebaseUser?=auth.currentUser
                        verifyEmail(usuario)

                    //Ingresa nombre y apellido a la BD
                        val userBD= usuario?.uid?.let { dbReference.child(it) }
                        userBD?.child("Name")?.setValue(name)
                        userBD?.child("LastName")?.setValue(lastName)
                        action()
                    }
                }

        }
    }
    private fun action(){
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
                    task ->
                if(task.isComplete){
                    Toast.makeText(baseContext, "El usuario fue registrado correctamente",
                        Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(baseContext, "Error al registrar el usuario",
                        Toast.LENGTH_LONG).show()
                    progressBar.visibility=View.INVISIBLE
                }
            }
    }

}