package com.buffetapp.pro.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.buffetapp.pro.HomeActivity
import com.buffetapp.pro.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterUser : AppCompatActivity() {

    private lateinit var txtName: EditText
    private lateinit var txtLastName: EditText
    private lateinit var txtPhone: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPasword: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database : FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        txtName=findViewById(R.id.nameUserEditTextRegister)
        txtLastName=findViewById(R.id.lastnameUserEditTextRegister)
        txtPhone=findViewById(R.id.phoneEditTextRegister)
        txtEmail=findViewById(R.id.emailEditTextRegister)
        txtPasword=findViewById(R.id.passwordEditTextRegister)

        progressBar=findViewById(R.id.progressBar)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        dbReference = database.reference.child("Usuarios")

        registerClick()
        nexA()

    }

    private fun registerClick(){
        val registerbtn : Button = findViewById(R.id.registerBtn)
        registerbtn.setOnClickListener {
            createNewAccount()
        }
    }

    private fun createNewAccount(){
        val name:String = txtName.text.toString()
        val lastName:String = txtLastName.text.toString()
        val phone:String = txtPhone.text.toString()
        val email:String = txtEmail.text.toString()
        val password:String = txtPasword.text.toString()

        if(!TextUtils.isEmpty(name) &&!TextUtils.isEmpty(lastName) &&!TextUtils.isEmpty(phone) &&!TextUtils.isEmpty(email) &&!TextUtils.isEmpty(password)){

            progressBar.visibility = View.VISIBLE

            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) {
                        task ->

                    if (task.isComplete){
                        val user: FirebaseUser?=auth.currentUser
                        verifyEmail(user)

                        val userBD = dbReference.child(user?.uid.toString())

                        userBD.child("Name").setValue(name)
                        userBD.child("LastName").setValue(lastName)
                        userBD.child("Phone").setValue(phone)
                        userBD.child("Email").setValue(email)
                        userBD.child("Password").setValue(password)

                        action(name)
                    }
                }
        }
    }

    private fun action(name: String){
        val name:String = txtName.text.toString()
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("name", name)
        }
        startActivity(homeIntent)
        finish()
    }

    private fun verifyEmail(user: FirebaseUser?){
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this) {
                    task ->

                if (task.isComplete){
                    Toast.makeText(this, "Email enviado", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this, "Error al enviar el Email", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun nexA(){
        val btnnexMain : Button = findViewById(R.id.btnNexA)
        btnnexMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}