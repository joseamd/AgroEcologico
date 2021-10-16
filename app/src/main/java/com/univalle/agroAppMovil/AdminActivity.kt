package com.univalle.agroAppMovil

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.univalle.agroAppMovil.adapters.viewPagerAdapter
import com.univalle.agroAppMovil.databinding.ActivityAdminBinding
import com.univalle.agroAppMovil.fragmentos.Crear_Producto_Fragment
import com.univalle.agroAppMovil.fragmentos.Crear_Puesto_Fragment


class AdminActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var mainBinding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        setUpTabs()

        auth= FirebaseAuth.getInstance()

    }

    private fun setUpTabs() {
        val adapter = viewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Crear_Puesto_Fragment(),"Crear Puesto")
        adapter.addFragment(Crear_Producto_Fragment(),"Crear Producto")
        mainBinding.ViewPager.adapter=adapter
        mainBinding.tabLayout.setupWithViewPager(mainBinding.ViewPager)
    }


    //cerrar el logueo
    fun signOut(view:View){
        cerrarSesion()
    }

    private fun cerrarSesion() {
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }


    override fun  onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {

        if(keyCode== KeyEvent.KEYCODE_BACK){
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