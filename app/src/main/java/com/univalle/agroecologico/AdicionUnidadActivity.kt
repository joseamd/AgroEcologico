package com.univalle.agroecologico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

import com.google.firebase.database.*
import com.univalle.agroecologico.databinding.ActivityAdicionUnidadBinding
import kotlinx.android.synthetic.main.activity_adicion_unidad.*
import java.util.*


class AdicionUnidadActivity : AppCompatActivity() {

    private lateinit var txtNameUnit: EditText
    private lateinit var txtCodeUnit: EditText
    private lateinit var txtPrice: EditText
    private lateinit var spinner: Spinner

    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth

    private lateinit var binding: ActivityAdicionUnidadBinding
    private lateinit var ImageUri: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicion_unidad)

        txtNameUnit=findViewById(R.id.txtNameUnidad)
        txtCodeUnit=findViewById(R.id.txtCodeUnit)
        txtPrice=findViewById(R.id.txtPrecio)
        spinner=findViewById(R.id.listaUnidades)
        progressBar=findViewById(R.id.progressBarUnidad)


        //Creamos la instancia para la base de datos y la autenticación
        database= FirebaseDatabase.getInstance()
        auth=FirebaseAuth.getInstance()
        dbReference=database.reference.child("Unidad")
        binding= ActivityAdicionUnidadBinding.inflate(layoutInflater)

        selectImageBtn.setOnClickListener {
            selectImage()
        }

    }

    private fun selectImage() {

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode== RESULT_OK){
            println("llego aqui")
            ImageUri = data?.data!!
            firebaseImage.setImageURI(ImageUri);

        }
    }

    fun createNewUnidad(view:View){
        crearUnidad()
    }

    private fun crearUnidad() {
        val nameUnit:String=txtNameUnit.text.toString()
        val codeUnit:String=txtCodeUnit.text.toString()
        val price:String=txtPrice.text.toString()
        val selection:String=spinner.selectedItem.toString()

        val formatter= SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")


        //comparamos que los campos no esten vacios
        if(!TextUtils.isEmpty(nameUnit) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(selection)){

            progressBar.visibility= View.VISIBLE
            val unidad:FirebaseUser?=auth.currentUser
            //se instancia un objeto de la clase Unidades
            val unidad1 = Unidades(nameUnit,price,selection)
            //se envía el objeto creado a la bd
            dbReference.child ("Codigo: "+codeUnit).setValue (unidad1);


            //subimos la imagen al storage
            storageReference.putFile(ImageUri).
                addOnSuccessListener{
                    binding.firebaseImage.setImageURI(null)
                }.addOnFailureListener {

            }

            Toast.makeText(baseContext, "Unidad creada correctamente",
                Toast.LENGTH_LONG).show()
            action()

        }

    }


    private fun action(){
        val intent = Intent(this,AdicionUnidadActivity::class.java)
        startActivity(intent)
        progressBar.visibility=View.INVISIBLE
        finish();

    }
}