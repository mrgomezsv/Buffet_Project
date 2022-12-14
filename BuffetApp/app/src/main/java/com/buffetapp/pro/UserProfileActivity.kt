package com.buffetapp.pro

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
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
            val personId = account.id
            val personEmail = account.email
            // Uso el nombre y la URL de la foto para mostrar el nombre y la foto del usuario.

            val imageView = findViewById<ImageView>(R.id.image_user)
            // Carguo la foto de perfil del usuario en ImageView usando Glide.
            Glide.with(this)
                .load(personPhoto)
                .into(imageView)

            val name_profile: TextView = findViewById(R.id.name_profile)
            name_profile.text = personName
            val email_profile: TextView = findViewById(R.id.email_profile)
            email_profile.text = personEmail
            val phone_user: TextView = findViewById(R.id.phone_user)
            phone_user.text = personId

            val img_tickete: ImageView = findViewById(R.id.img_tickete)
            val txt_tickete: TextView = findViewById(R.id.txt_tickete)
            val img_tickete2: ImageView = findViewById(R.id.img_tickete2)
            val txt_tickete2: TextView = findViewById(R.id.txt_tickete2)


            //val id = phone_user.text.toString()
            /*if (personId == null) {
                /*img_tickete.setVisibility(View.VISIBLE)
                txt_tickete.setVisibility(View.VISIBLE)*/
                val toast = Toast.makeText(this, "El ID esta lleno", Toast.LENGTH_LONG)
                toast.show()

            } else {
                img_tickete2.setVisibility(View.VISIBLE)
                txt_tickete2.setVisibility(View.VISIBLE)
                val toast = Toast.makeText(this, "El ID esta vacio", Toast.LENGTH_LONG)
                toast.show()

            }*/

            /*val cuenta = GoogleSignIn.getLastSignedInAccount(this)
            if (cuenta == null) {
                println("El usuario no se ha autenticado con Google")
                val toast = Toast.makeText(this, "El ID esta lleno", Toast.LENGTH_LONG)
                toast.show()
            } else {
                println("El usuario se ha autenticado con Google")
                val toast = Toast.makeText(this, "El ID esta vacio", Toast.LENGTH_LONG)
                toast.show()
            }*/
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


/*val user = FirebaseAuth.getInstance().currentUser
val country_profile : TextView = findViewById(R.id.country_profile)
if (user != null) {
    val userProfile = user.providerData[0]
    val userNationality = userProfile.getProperty("locale")
    // Use the user's nationality to display their country of origin.
}*/

// Get the TextView that you want to display the user's nationality in.
/*val textView = findViewById<TextView>(R.id.country_profile)

// Get the user's nationality from Firebase Auth.
val user = FirebaseAuth.getInstance().currentUser
if (user != null) {
    val userProfile = user.providerData[0]
    val userNationality = userProfile.getString("locale")

    // Set the text of the TextView to the user's nationality.
    textView.setText(userNationality)
}*/



//El código que ha proporcionado no es válido en Kotlin. No se puede asignar un valor a una vista ImageView de la manera que lo está intentando hacer. En su lugar, puede usar una biblioteca de carga de imágenes, como Glide o Picasso, para cargar la imagen en la vista ImageView. Por ejemplo, si está usando Glide, puede cargar la imagen en la vista ImageView de la siguiente manera:
//
//Copy code
//// Get the ImageView that you want to display the user's profile photo in.
//val imageView = findViewById<ImageView>(R.id.image_user)
//
//// Load the user's profile photo into the ImageView using Glide.
//Glide.with(this)
//  .load(personPhoto)
//  .into(imageView)
//Tenga en cuenta que esto supone que ha importado la biblioteca de carga de imágenes correctamente en su proyecto y que ha configurado correctamente la vista ImageView en su diseño de interfaz de usuario.




//// Get the ImageView that you want to display the user's profile photo in.
//val imageView = findViewById<ImageView>(R.id.image_user)
//
//// Load the user's profile photo into the ImageView using Glide.
//Glide.with(this)
//  .load(personPhoto)
//  .into(imageView)


//// Primero obtenemos la cuenta del usuario que ha iniciado sesión
//val account = GoogleSignIn.getLastSignedInAccount(this)
//
//if (account != null) {
//    // Si el usuario ha iniciado sesión, obtenemos su nombre y foto de perfil
//    val userName = account.displayName
//    val userPhoto = account.photoUrl
//
//    // Aquí puedes usar el nombre y la foto del usuario para actualizar tu interfaz de usuario,
//    // como mostrando el nombre del usuario en un TextView y la foto en un ImageView.
//}
//``


//implementation 'com.google.android.gms:play-services-auth:17.0.0'


//val account = GoogleSignIn.getLastSignedInAccount(this)
//if (account != null) {
//  val personName = account.displayName
//  val personPhoto = account.photoUrl
//  // Use the name and photo URL to display the user's name and photo.
//}