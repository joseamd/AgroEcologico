package com.univalle.agroAppMovil.fragmentos

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.univalle.agroAppMovil.*
import com.univalle.agroAppMovil.databinding.FragmentCrearProductoBinding
import java.util.*


class Crear_Producto_Fragment : Fragment(), View.OnClickListener {

    private lateinit var mBinding: FragmentCrearProductoBinding

    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var ImageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //Creamos la instancia para la base de datos y la autenticación
        database= FirebaseDatabase.getInstance()
        auth=FirebaseAuth.getInstance()
        dbReference=database.reference.child("Unidad")

        mBinding = FragmentCrearProductoBinding.inflate(inflater,container, false)
        mBinding.selectImageBtn.setOnClickListener {
            selectImage()
        }


        return mBinding.root
    }

    private fun selectImage() {

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode== AppCompatActivity.RESULT_OK){
            ImageUri = data?.data!!
            mBinding.firebaseImagePro.setImageURI(ImageUri);
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.btnRegisterUnidad.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        val  codeUnit = mBinding.txtCodeUnit.text.toString()
        val  nameUnit = mBinding.txtNameUnidad.text.toString()
        val  price = mBinding.txtPrecio.text.toString()
        val  selection = mBinding.listaUnidades.selectedItem.toString()


        val formatter= SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

        if(!TextUtils.isEmpty(nameUnit) && !TextUtils.isEmpty(price) && !TextUtils.isEmpty(selection)) {

            //progressBar.visibility = View.VISIBLE
            val unidad: FirebaseUser? = auth.currentUser
            val asignar = storageReference.child("images" + ImageUri!!.lastPathSegment)


            //subimos la imagen al storage
            asignar.putFile(ImageUri).addOnSuccessListener {
                asignar.downloadUrl.addOnSuccessListener {
                    mBinding.firebaseImagePro.setImageURI(null)
                    val url = it.toString()
                    //se instancia un objeto de la clase Unidades
                    val unidad1 = Producto(codeUnit,nameUnit, price, selection, url)
                    //se envía el objeto creado a la bd
                    dbReference.child(codeUnit).setValue(unidad1);
                }

            }.addOnFailureListener {

            }
            Toast.makeText(getActivity(), "Producto creado correctamente", Toast.LENGTH_SHORT).show();
            action()

        }

    }

    private fun action() {
        val intent = Intent(activity, AdminActivity::class.java)
        startActivity(intent)
    }


}