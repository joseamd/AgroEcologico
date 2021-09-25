package com.univalle.agroecologico

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var txtUser: EditText
    private lateinit var txtPassword: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUser=findViewById(R.id.txtUser)
        txtPassword=findViewById(R.id.txtPassword)
        progressBar=findViewById(R.id.progressBarLogin)
        auth= FirebaseAuth.getInstance()

    }
    fun forgotPassword(view:View){
        startActivity(Intent(this, ForgotPassActivity::class.java))
    }
    fun register(view:View){
        startActivity(Intent(this, RegisterActivity::class.java))
    }
    fun login(view:View){
        loginUser()
    }
    private fun loginUser(){
        val user:String=txtUser.text.toString()
        val pasword:String=txtPassword.text.toString()

        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(pasword)){
            progressBar.visibility=View.VISIBLE

            auth.signInWithEmailAndPassword(user,pasword)
                .addOnCompleteListener(this){
                        task ->

                    if(task.isSuccessful){
                        action()
                    }else{
                        Toast.makeText(baseContext, "Correo o contraseña incorectos.",
                            Toast.LENGTH_LONG).show()
                        progressBar.visibility=View.INVISIBLE
                    }
                }
        }
    }
    private fun action(){
        startActivity(Intent(this, VendedorActivity::class.java))
        onRestart();
    }

    override fun  onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode==KeyEvent.KEYCODE_BACK){
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