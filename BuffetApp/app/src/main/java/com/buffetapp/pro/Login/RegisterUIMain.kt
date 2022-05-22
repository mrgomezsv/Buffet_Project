package com.buffetapp.pro.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.buffetapp.pro.HomeActivity
import com.buffetapp.pro.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class RegisterUIMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_uimain)


        val providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())

        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val response = IdpResponse.fromResultIntent(it.data)
            if (it.resultCode == RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    back()
                }
            }
        }.launch(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build())
    }

    private fun back(){
        val intent = Intent(this, RegisterUIMain::class.java)
        startActivity(intent)
        finish()
    }
}