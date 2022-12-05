package com.buffetapp.pro.OurServices

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.buffetapp.pro.OurServices.SocialEvent.OnSocialListener
import com.buffetapp.pro.OurServices.SocialEvent.Social
import com.buffetapp.pro.OurServices.SocialEvent.SocialAdapter
import com.buffetapp.pro.databinding.ActivitySocialesBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class SocialesActivity : AppCompatActivity(), OnSocialListener {

    private lateinit var binding: ActivitySocialesBinding
    private lateinit var adapter: SocialAdapter
    private lateinit var firestoreListener: ListenerRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_sociales)
        binding = ActivitySocialesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Eventos Sociales"

        configRecyclerViewSocial()
        configFirestoreListSocial()
        configFirestoreListRealTimeSocial()

    }

    private fun configRecyclerViewSocial() {
        adapter = SocialAdapter(mutableListOf(), this)
        binding.recyclerViewSociales.apply {
            layoutManager = GridLayoutManager(this@SocialesActivity, 1,
                GridLayoutManager.VERTICAL, false)
            adapter = this@SocialesActivity.adapter
        }

        /*(1..5).forEach {
            val product = MenuProducto(it.toString(), "Producto $it", "Envio a Domicilio Gratis",
                "", it, it * 1.1)
            adapter.add(product)
        }*/
    }

    private fun configFirestoreListSocial(){
        val db = FirebaseFirestore.getInstance()

        db.collection("social")
            .get()
            .addOnSuccessListener { snapshots ->
                for (document in snapshots){
                    val social = document.toObject(Social::class.java)
                    social.id = document.id
                    adapter.add(social)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al consultar datos.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun configFirestoreListRealTimeSocial() {//Update en tiempo real//
        val db = FirebaseFirestore.getInstance()
        val socialRef = db.collection("social")

        firestoreListener = socialRef.addSnapshotListener { snapshots, error ->
            if (error != null){
                Toast.makeText(this, "Error al consultar datos.", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            for (snapshot in snapshots!!.documentChanges){
                val social = snapshot.document.toObject(Social::class.java)
                social.id = snapshot.document.id
                when(snapshot.type){
                    DocumentChange.Type.ADDED -> adapter.add(social)
                    DocumentChange.Type.REMOVED -> adapter.delete(social)
                    DocumentChange.Type.MODIFIED -> adapter.update(social)

                }
            }
        }
    }

    override fun onClick(social: Social) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=+50378736282&text=Hola,%20buen%20día,%20escribo%20desde%20Buffet%20App%20y%20quiero%20hacer%20un%20pedido"))
        startActivity(intent)
        //Toast.makeText(this, "Click Corto.", Toast.LENGTH_SHORT).show()
    }

    override fun onLongClick(social: Social) {

        //Toast.makeText(this, "Click Largo.", Toast.LENGTH_SHORT).show()
    }
}