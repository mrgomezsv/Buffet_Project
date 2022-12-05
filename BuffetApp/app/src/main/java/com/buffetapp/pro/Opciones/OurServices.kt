package com.buffetapp.pro.Opciones

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.buffetapp.pro.*
import com.buffetapp.pro.OurServices.BodasActivity
import com.buffetapp.pro.OurServices.CristaleriaActivity
import com.buffetapp.pro.OurServices.SocialesActivity

class OurServices : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_our_services)

        title = "Nuestros Servicios"

        Fifteen()
        MerryA()
        Cristaleria()
        Social()
    }

    private fun MerryA(){
        val button4 : Button = findViewById(R.id.button4)
        button4.setOnClickListener {
            val intent = Intent(this, YearActivity::class.java)
            startActivity(intent)
        }
    }

    private fun Fifteen(){
        val button5 : Button = findViewById(R.id.button5)
        button5.setOnClickListener {
            val intent = Intent(this, BodasActivity::class.java)
            startActivity(intent)
        }
    }

    private fun Cristaleria(){
        val button6 : Button = findViewById(R.id.button6)
        button6.setOnClickListener {
            val intent = Intent(this, CristaleriaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun Social(){
        val button7 : Button = findViewById(R.id.button7)
        button7.setOnClickListener {
            val intent = Intent(this, SocialesActivity::class.java)
            startActivity(intent)
        }
    }
}