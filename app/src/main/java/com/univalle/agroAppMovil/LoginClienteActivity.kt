package com.univalle.agroAppMovil

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_login_cliente.*

class LoginClienteActivity : AppCompatActivity() {

    private lateinit var txtUserCliente: EditText
    private lateinit var txtPasswordCliente: EditText
    private lateinit var progressBarCliente: ProgressBar
    private lateinit var auth: FirebaseAuth

    private val GOOGLE_SIGN_IN = 100
    private val callbackManager = CallbackManager.Factory.create()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_cliente)

        txtUserCliente=findViewById(R.id.txtUserCliente)
        txtPasswordCliente=findViewById(R.id.txtPasswordCliente)
        progressBarCliente=findViewById(R.id.progressBarLoginCliente)
        auth= FirebaseAuth.getInstance()

        session()

    }

    private fun session() {
        //Guardado de dato
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email: String? = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)

        if (email != null && provider != null){
            authLayout.visibility = View.INVISIBLE
            action(email, ProviderType.valueOf(provider))
        }
    }


    fun forgotPassword(view:View){
        startActivity(Intent(this, ForgotPassActivity::class.java))
    }
    fun login(view:View){
        loginUser()
    }
    fun register(view:View){
        registroUser()
    }
    fun googleRegister(view: View){
        googleUser()
    }
    fun faceRegister(view: View){
        faceUser()
    }

    private fun faceUser() {

        LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))

        LoginManager.getInstance().registerCallback(callbackManager,
        object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                result?.let {
                    val token = it.accessToken
                    val credential = FacebookAuthProvider.getCredential(token.token)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if(it.isSuccessful){
                            action(it.result?.user?.email ?: "", ProviderType.FACEBOOK)
                        }else{
                            showAlert()
                        }
                    }
                }

            }

            override fun onCancel() {
                //No hacemos nada
            }

            override fun onError(error: FacebookException?) {
                showAlert()
            }

        })
    }

    private fun googleUser(){
        //configuración

        val googleConf =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        val googleclient = GoogleSignIn.getClient(this, googleConf)
        googleclient.signOut()

        startActivityForResult(googleclient.signInIntent, GOOGLE_SIGN_IN)
    }

    private fun registroUser() {
        val user:String=txtUserCliente.text.toString()
        val password:String=txtPasswordCliente.text.toString()

        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)) {
            progressBarCliente.visibility = View.VISIBLE

            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(user, password).addOnCompleteListener {

                    if (it.isSuccessful) {
                        action(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        showAlert()
//                        Toast.makeText(baseContext, "Correo o contraseña incorectos.",
//                            Toast.LENGTH_LONG).show()
                        progressBarCliente.visibility = View.INVISIBLE
                    }
                }
        }
    }


    private fun loginUser(){
        val user:String=txtUserCliente.text.toString()
        val password:String=txtPasswordCliente.text.toString()

        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)){
            progressBarCliente.visibility=View.VISIBLE

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(user,password).addOnCompleteListener{

                    if(it.isSuccessful){
                        action(it.result?.user?.email ?: "", ProviderType.BASIC)
                    }else{
                        showAlert()
                        progressBarCliente.visibility=View.INVISIBLE
                    }
                }

        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error de Autenticación")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    private fun action(email: String, provider: ProviderType){
        val ListaCompra = Intent(this, ListaCompraActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(ListaCompra)
    }

    //si el usuario ya se ha registrado, automaticamente se direcciona al activity principal
    override fun onStart() {
        super.onStart()
        authLayout.visibility =View.VISIBLE

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        callbackManager.onActivityResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)

                if (account != null){
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if(it.isSuccessful){
                            action(it.result?.user?.email ?: "", ProviderType.GOOGLE)
                        }else{
                            showAlert()
                        }
                    }

                }
            }catch (e: ApiException){
                showAlert()
            }

        }
    }

}