package com.buffetapp.pro.Login

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.buffetapp.pro.HomeActivity
import com.buffetapp.pro.R
import com.buffetapp.pro.databinding.ActivityHomeFragmentBinding
import com.buffetapp.pro.databinding.ActivityWelcomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import java.lang.Exception

class Welcome : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityWelcomeBinding

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    //constants
    internal companion object {
        private const val RC_SIGN_IN = 99
        const val TAG = "GOOGLE_SIGN_IN_TAG"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        //setContentView(R.layout.activity_welcome)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mailLogin()
        userRegis()
        link()

        title = "Bienvenido"

        //configure the Google SignIn
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) //no se preocupe si se muestra en rojo, se resolverá cuando se cree por primera vez
            .requestEmail() //solo necesitamos correo electrónico de la cuenta de Google
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //Botón de inicio de sesión de Google, haga clic para comenzar Google SignIn
        binding.googleSignInBtn.setOnClickListener {
            Log.d(MainActivity.TAG, "onCreate: comenzar Google SignIn")
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }
    }

    //LogIn Google

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            // start profile activity
            startActivity(Intent(this@Welcome, HomeActivity::class.java))
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Resultado devuelto al iniciar la intención desde GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Log.d(
                TAG,
                "onActivityResult: Resultado de la intención de inicio de sesión de GOOGLE"
            )
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Éxito de Google SignIn, ahora autenticación con firebase
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)

            } catch (e: Exception) {
                // Fracaso Google Sign In
                Log.d(TAG, "onActivityResult: ${e.message}")
            }
        }
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        Log.d(
            TAG,
            "firebaseAuthWithGoogleAccount: iniciar la autenticación de Firebase con la cuenta de Google"
        )

        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                Log.d(TAG, "firebaseAuthWithGoogleAccount: Conectado")

                val firebaseUser = firebaseAuth.currentUser
                val uid = firebaseUser!!.uid
                val email = firebaseUser!!.email
                val name = firebaseUser!!.displayName
                //val phote = firebaseUser!!.photoUrl

                Log.d(TAG, "firebaseAuthWithGoogleAccount: Uid: $uid")
                Log.d(TAG, "firebaseAuthWithGoogleAccount: Email: $email")

                if (authResult.additionalUserInfo!!.isNewUser) {
                    Log.d(TAG, "firebaseAuthWithGoogleAccount: Cuenta creada... \n$email")
                    Toast.makeText(
                        this@Welcome,
                        "Cuenta creada... \n$email",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    /*Log.d(TAG, "firebaseAuthWithGoogleAccount: Usuario existente ... \n$email")
                    Toast.makeText(this@MainActivity, "Bienvenido... \n$email", Toast.LENGTH_SHORT)*/
                    Log.d(
                        TAG,
                        "firebaseAuthWithGoogleAccount: Usuario existente ... \n$name"
                    )
                    Toast.makeText(this@Welcome, "Bienvenido... \n$name", Toast.LENGTH_SHORT)
                        .show()
                }
                // start profile activity
                startActivity(Intent(this@Welcome, SplashScreenActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->

                //login failed
                Log.d(
                    TAG,
                    "firebaseAuthWithGoogleAccount: Error de inicio de sesión debido a ${e.message}"
                )
                Toast.makeText(
                    this@Welcome,
                    "Error de inicio de sesión debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun mailLogin() {//Login con Correo
        val btnMain: Button = findViewById(R.id.signUpButtonMain)
        btnMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun userRegis() {//Registrar Usuario
        val btnuserRegis: TextView = findViewById(R.id.textRegis)
        btnuserRegis.setOnClickListener {
            val intent = Intent(this, RegisterUIWelcome::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun link() {// Yo
        val txtUrl: TextView = findViewById(R.id.powerTxt)
        txtUrl.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.webforallsv.com/portfolio/buffet-proevent-app/")
            )
            startActivity(intent)
        }
    }
}