package com.buffetapp.pro

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class WhatsAppActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whats_app)

        //enviar()

    }

    /*private fun enviar(){
        val textMensaje: EditText = findViewById(R.id.txtMensaje)
        val btnSend: Button = findViewById(R.id.btnSend)*/
        /*btnSend.setOnClickListener {
            Toast.makeText(this, "Ajustes guardados", Toast.LENGTH_LONG).show()
        }*/
        /*btnSend.setOnClickListener {
            try {
                //val text = "This is a test" // Replace with your message.
                val toNumber = "50378736282" // Replace with mobile phone number without +Sign or leading zeros, but with country code
                //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("http://api.whatsapp.com/send?phone=$toNumber&text=$textMensaje")
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }*/
    //}
}