package com.buffetapp.pro.Opciones

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.buffetapp.pro.R
import com.buffetapp.pro.databinding.ActivityAboutUsBinding

class AboutUsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        callF()
        facebookLink()
        webLink()
        whastappLink()
        link3()

        title = "Team Buffet ProEvent"
    }

    private fun facebookLink(){
        val btnFacebook : Button = findViewById(R.id.btnFacebook)
        btnFacebook.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100009157009110"))
            startActivity(intent)
        }
    }

    private fun webLink(){
        val btnUrl : Button = findViewById(R.id.btnUrl)
        btnUrl.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://buffetproevent.com/"))
            startActivity(intent)
        }
    }

    private fun whastappLink(){
        val btnWhastapp: Button = findViewById(R.id.btnWhastapp)
        btnWhastapp.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=+50378736282&text=Hola,%20necesito%20informaci%C3%B3n%20de%20buffetproevent.com%20me%20comunico%20desde%20Buffet%20App"))
            startActivity(intent)
        }
    }

    //Codigo para Boton de Llamadas

    private fun callF(){
        binding.btnCall.setOnClickListener{ requestPermissions() }
    }

    private fun requestPermissions(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            when {

                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    call()
                }

                else -> requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }

        }else{
            call()
        }
    }

    private fun call(){

        startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$78736282")))

    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){ isGranted->

        if (isGranted){
            call()
        }else{
            Toast.makeText(this, "Necesita habilitar este permiso para poder Comunicarte con Nosotros", Toast.LENGTH_LONG).show()
        }
    }

    private fun link3(){
        val txtUrl3: TextView = findViewById(R.id.powerTxt3)
        txtUrl3.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.webforallsv.com/portfolio/buffet-proevent-app/")
            )
            startActivity(intent)
        }
    }
}