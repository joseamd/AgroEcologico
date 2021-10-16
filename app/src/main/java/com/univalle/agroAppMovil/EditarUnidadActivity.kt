package com.univalle.agroAppMovil

import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.univalle.agroAppMovil.databinding.ActivityEditarUnidadBinding
import kotlinx.android.synthetic.main.activity_admin.view.*
import kotlinx.android.synthetic.main.activity_editar_unidad.*
import java.util.*
import android.widget.Spinner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class EditarUnidadActivity : AppCompatActivity() {
    private lateinit var txtNameUnit: EditText
    private lateinit var txtCodeUnit: EditText
    private lateinit var txtPrice: EditText
    private lateinit var spinner: Spinner
    private lateinit var binding: ActivityEditarUnidadBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var context: Context

    private lateinit var ImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_unidad)

        txtNameUnit = findViewById(R.id.txtNameUnidad)
        txtCodeUnit = findViewById(R.id.txtCodeUnit)
        txtPrice = findViewById(R.id.txtPrecio)
        spinner = findViewById(R.id.listaUnidades)

        val dato1 = intent.getStringExtra("Dato1")
        val dato2 = intent.getStringExtra("Dato2")
        val dato3 = intent.getStringExtra("Dato3")
        val dato4 = intent.getStringExtra("Dato4")
        val dato5 = intent.getStringExtra("Dato5")

        //cargarmos la información en el formulario editar unidad
        val textView1 = findViewById<View>(R.id.txtCodeUnit) as TextView
        textView1.text = "$dato1"
        val textView2 = findViewById<View>(R.id.txtNameUnidad) as TextView
        textView2.text = "$dato2"
        val textView3 = findViewById<View>(R.id.txtPrecio) as TextView
        textView3.text = "$dato3"
        //busco en el sprint el valor del item seleccionado
        for (position in 0 until listaUnidades.count) {
            if (listaUnidades.getItemAtPosition(position).equals(dato4) ) {
                listaUnidades.setSelection(position)
                return
            }
        }
        Glide.with(context)
            .load(dato5) //cual es la url que debe cargar
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.firebaseImage)//en donde lo vamos a cargar


        //Creamos la instancia para la base de datos y la autenticación
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database.reference.child("Unidad")
        binding = ActivityEditarUnidadBinding.inflate(layoutInflater)

        binding.selectImageBtnEdit.setOnClickListener {
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
            ImageUri = data?.data!!
            firebaseImage.setImageURI(ImageUri);
        }
    }

    fun createNewUnidad(view: View){
        crearUnidad()
    }

    private fun crearUnidad() {
//        val nameUnit:String=txtNameUnit.text.toString()
//        val codeUnit:String=txtCodeUnit.text.toString()
//        val price:String=txtPrice.text.toString()
//        val selection:String=spinner.selectedItem.toString()
//
//        val formatter= SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
//        val now = Date()
//        val fileName = formatter.format(now)
//        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")
//
//
//        //comparamos que los campos no esten vacios
//        if(!TextUtils.isEmpty(nameUnit) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(selection)){
//
//            progressBar.visibility= View.VISIBLE
//
//            //subimos la imagen al storage
//            storageReference.putFile(ImageUri).addOnSuccessListener {
//                storageReference.downloadUrl.addOnSuccessListener {
//                    binding.firebaseImage.setImageURI(null)
//                    val url = it.toString()
//                    //se instancia un objeto de la clase Unidades
//                    val unidad1 = Unidades(nameUnit, price, selection, url)
//                    //se envía el objeto creado a la bd
//                    dbReference.child(codeUnit).setValue(unidad1);
//                }
//
//            }.addOnFailureListener {
//
//            }
//
//
//            Toast.makeText(baseContext, "Actualización correcta",
//                Toast.LENGTH_LONG).show()
//            action()
//
//        }

    }

    private fun action(){
        val intent = Intent(this,ActualizarListaActivity::class.java)
        startActivity(intent)
        progressBar.visibility= View.INVISIBLE
        finish();

    }
}