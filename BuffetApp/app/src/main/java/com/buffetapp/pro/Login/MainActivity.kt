package com.buffetapp.pro.Login

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.buffetapp.pro.HomeActivity
import com.buffetapp.pro.ProviderType
import com.buffetapp.pro.R
import com.buffetapp.pro.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityMainBinding

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    //constants
    internal companion object {
        private const val RC_SIGN_IN = 100
        const val TAG = "GOOGLE_SIGN_IN_TAG"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        link() //lind de contacto IT

        setup()

        title = "Buffet App"

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
            Log.d(TAG, "onCreate: comenzar Google SignIn")
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }

        recuperarC() // ir a Recuperar la Contraseña
        userR1() //Registrar Usuario
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    //LogIn Google

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            // start profile activity
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Resultado devuelto al iniciar la intención desde GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Log.d(TAG, "onActivityResult: Resultado de la intención de inicio de sesión de GOOGLE")
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
        Log.d(TAG, "firebaseAuthWithGoogleAccount: iniciar la autenticación de Firebase con la cuenta de Google")

        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                Log.d(TAG, "firebaseAuthWithGoogleAccount: Conectado")

                val firebaseUser = firebaseAuth.currentUser
                val uid = firebaseUser!!.uid
                val email = firebaseUser!!.email
                val name = firebaseUser!!.displayName

                Log.d(TAG, "firebaseAuthWithGoogleAccount: Uid: $uid")
                Log.d(TAG, "firebaseAuthWithGoogleAccount: Email: $email")

                if (authResult.additionalUserInfo!!.isNewUser) {
                    Log.d(TAG, "firebaseAuthWithGoogleAccount: Cuenta creada... \n$email")
                    Toast.makeText(
                        this@MainActivity,
                        "Cuenta creada... \n$email",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    /*Log.d(TAG, "firebaseAuthWithGoogleAccount: Usuario existente ... \n$email")
                    Toast.makeText(this@MainActivity, "Bienvenido... \n$email", Toast.LENGTH_SHORT)*/
                    Log.d(TAG, "firebaseAuthWithGoogleAccount: Usuario existente ... \n$name")
                    Toast.makeText(this@MainActivity, "Bienvenido... \n$name", Toast.LENGTH_SHORT)
                        .show()
                }
                // start profile activity
                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->

                //login failed
                Log.d(TAG, "firebaseAuthWithGoogleAccount: Error de inicio de sesión debido a ${e.message}")
                Toast.makeText(
                    this@MainActivity,
                    "Error de inicio de sesión debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onStart() {
        super.onStart()
        val authLayout: LinearLayout = findViewById(R.id.authLayout)
        authLayout.visibility = View.VISIBLE

    }

    private fun setup() {

        title = "Autenticación"

        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val signUpButton: Button = findViewById(R.id.signUpButton)
        val logInButton: Button = findViewById(R.id.registerBtn)
        //val googleButtonLogIn: Button = findViewById(R.id.googleSignInBtn)

        ////////////////////////////////El Registro de Usuario PASA a su activity///////////////////

        //SignUp Mediante Correo Electronico
        /*  signUpButton.setOnClickListener {
              if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                  FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                      emailEditText.text.toString(),
                      passwordEditText.text.toString()
                  ).addOnCompleteListener {

                      if (it.isSuccessful) {
                          showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                      } else {
                          showAlert()
                      }
                  }
              }
          }*/

        //Ir a Registro de Usuario Nuevo mediante Mail
        signUpButton.setOnClickListener {
            val intent = Intent(this, RegisterUser::class.java)
            startActivity(intent)
        }

        ////////////////////////////////////////////////////////////////////////////////////////////

        //LogIn Mediante Correo Electronico
        logInButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        emailEditText.text.toString(), passwordEditText.text.toString()
                    ).addOnCompleteListener {

                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                            Toast.makeText(this, "Bienvendio", Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            showAlert()
                        }
                    }
            }
        }
    }

    private fun showAlert() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType) {

        val homeIntent = Intent(this, SplashScreenActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)

        }
        startActivity(homeIntent)
        finish()
    }

    private fun recuperarC(){//Recuperar Contraseña
        val btnOlvideP : TextView = findViewById(R.id.btnOlvide)
        btnOlvideP.setOnClickListener {
            val intent = Intent(this, LostPassword::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun userR1(){//Registrar Usuario
        val btnuserR1 : TextView = findViewById(R.id.textView81)
        btnuserR1.setOnClickListener {
            val intent = Intent(this, RegisterUIMain::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun link(){//Ir a web
        val txtUrl : TextView = findViewById(R.id.powerTxt)
        txtUrl.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.webforallsv.com/portfolio/buffet-proevent-app/"))
            startActivity(intent)
        }
    }
}