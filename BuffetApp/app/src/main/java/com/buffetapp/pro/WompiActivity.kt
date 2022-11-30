package com.buffetapp.pro

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class WompiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wompi)
        pay()
    }

    private fun pay() {
        val btnPay : Button = findViewById(R.id.btnPay)
        btnPay.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://lk.wompi.sv/RuPk"))
            startActivity(intent)
        }
    }
}