package com.univalle.agroAppMovil.fragmentos

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.univalle.agroAppMovil.AdminActivity
import com.univalle.agroAppMovil.PuestoVenta
import com.univalle.agroAppMovil.databinding.FragmentCrearPuestoBinding


class Crear_Puesto_Fragment : Fragment(), View.OnClickListener{

    private lateinit var mBinding: FragmentCrearPuestoBinding
    private lateinit var dbReference:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth

      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
          //Creamos la instancia para la base de datos y la autenticación
          database= FirebaseDatabase.getInstance()
          auth=FirebaseAuth.getInstance()
          dbReference=database.reference.child("puestoVenta")

          mBinding = FragmentCrearPuestoBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.btncrearpuesto.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        val namePuesto = mBinding.txtNamePuesto.text.toString()
        val cedula = mBinding.txtCedula.text.toString()
        val password = mBinding.txtPasswordPuesto.text.toString()
        val celular = mBinding.txtCelular.text.toString()
        val email = mBinding.txtEmailPuesto.text.toString()

        //comparamos que los campos no esten vacios
        if(!TextUtils.isEmpty(namePuesto) && !TextUtils.isEmpty(cedula)
            && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(celular)&& !TextUtils.isEmpty(email)) {

            val puesto: FirebaseUser? = auth.currentUser

            //se instancia un objeto de la clase puestoVenta
            val puestoVenta = PuestoVenta(namePuesto, cedula, password, celular, email)

            //se envía el objeto creado a la bd y id del puesto será la cedula
            dbReference.child("puesto:" + cedula).setValue(puestoVenta);
            verifyEmail(puesto)
            action()
        }
    }
    private fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()
            ?.addOnCompleteListener(requireActivity()){
                    task ->
                if(task.isComplete){
                    Toast.makeText(getActivity(), "Puesto creado correctamente",
                        Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(getActivity(), "Error al crear el puesto",
                        Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun action() {
        val intent = Intent(activity, AdminActivity::class.java)
        startActivity(intent)
    }


}