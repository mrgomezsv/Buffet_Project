package com.buffetapp.pro

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide

class UserProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        title = "Perfil"
        linearUser()

        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            val personPhoto = account.photoUrl
            val personName = account.displayName
            val personEmail = account.email
            // Uso el nombre y la URL de la foto para mostrar el nombre y la foto del usuario.

            val imageView = findViewById<ImageView>(R.id.image_user)
            // Cargo la foto de perfil del usuario en ImageView usando Glide.
            Glide.with(this)
                .load(personPhoto)
                .into(imageView)

            // Cargo la info del perfil del usuario en ImageView usando Glide.
            val name_profile: TextView = findViewById(R.id.name_profile)
            name_profile.text = personName
            val email_profile: TextView = findViewById(R.id.email_profile)
            email_profile.text = personEmail
        }
    }

    private fun linearUser(){
        val topLayoutUser : LinearLayout = findViewById(R.id.topLayoutUser)
        topLayoutUser.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/mrgomez-dev-2650381a6/"))
            startActivity(intent)
        }
    }
}