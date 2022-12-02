package com.buffetapp.pro

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class WompiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wompi)
        pay()
        me4()
    }

    private fun pay() {
        val btnPay : Button = findViewById(R.id.btnPay)
        btnPay.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://lk.wompi.sv/RuPk"))
            startActivity(intent)
        }
    }

    private fun me4(){
        val powerTxt4 : TextView = findViewById(R.id.powerTxt4)
        powerTxt4?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/mrgomez-dev-2650381a6/"))
            startActivity(intent)
        }
    }
}