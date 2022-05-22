package com.buffetapp.pro.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.buffetapp.pro.R
import com.google.firebase.auth.FirebaseAuth

class LostPassword : AppCompatActivity() {

    private lateinit var txtEmail: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost_password)

        txtEmail=findViewById(R.id.emailEditTextRecu)
        auth= FirebaseAuth.getInstance()

        lostP()
    }

    private fun lostP() {
        val send: Button = findViewById(R.id.send)
        send.setOnClickListener {
            val email = txtEmail.text.toString()

            if (!TextUtils.isEmpty(email)) {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(this) { task ->

                        if (task.isSuccessful) {
                            Toast.makeText(this, "Email enviado", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, MainActivity::class.java))

                        } else {
                            Toast.makeText(this, "Error al recuperar Contraseña", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
            }
        }
    }
}