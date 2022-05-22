package com.buffetapp.pro.OurServices

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.buffetapp.pro.OurServices.MerryEvent.Bodas
import com.buffetapp.pro.OurServices.MerryEvent.BodasAdapter
import com.buffetapp.pro.OurServices.MerryEvent.OnBodasListener
import com.buffetapp.pro.databinding.ActivityBodasBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class BodasActivity : AppCompatActivity(), OnBodasListener {

    private lateinit var binding: ActivityBodasBinding
    private lateinit var adapter: BodasAdapter
    private lateinit var firestoreListener: ListenerRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_bodas)
        binding = ActivityBodasBinding.inflate(layoutInflater)
        setContentView(binding.root)


        title = "Recepciones de Bodas"

        configRecyclerViewBodas()
        configFirestoreListBodas()
        configFirestoreListRealTimeBodas()

    }

    private fun configRecyclerViewBodas() {
        adapter = BodasAdapter(mutableListOf(), this)
        binding.recyclerViewBodas.apply {
            layoutManager = GridLayoutManager(this@BodasActivity, 1,
                GridLayoutManager.VERTICAL, false)
            adapter = this@BodasActivity.adapter
        }

        /*(1..5).forEach {
            val product = MenuProducto(it.toString(), "Producto $it", "Envio a Domicilio Gratis",
                "", it, it * 1.1)
            adapter.add(product)
        }*/
    }

    private fun configFirestoreListBodas(){
        val db = FirebaseFirestore.getInstance()

        db.collection("bodas")
            .get()
            .addOnSuccessListener { snapshots ->
                for (document in snapshots){
                    val bodas = document.toObject(Bodas::class.java)
                    bodas.id = document.id
                    adapter.add(bodas)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al consultar datos.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun configFirestoreListRealTimeBodas() {//Update en tiempo real//
        val db = FirebaseFirestore.getInstance()
        val bodasRef = db.collection("bodas")

        firestoreListener = bodasRef.addSnapshotListener { snapshots, error ->
            if (error != null){
                Toast.makeText(this, "Error al consultar datos.", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            for (snapshot in snapshots!!.documentChanges){
                val bodas = snapshot.document.toObject(Bodas::class.java)
                bodas.id = snapshot.document.id
                when(snapshot.type){
                    DocumentChange.Type.ADDED -> adapter.add(bodas)
                    DocumentChange.Type.REMOVED -> adapter.delete(bodas)
                    DocumentChange.Type.MODIFIED -> adapter.update(bodas)

                }
            }
        }
    }

    override fun onClick(bodas: Bodas) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=+50378736282&text=Hola,%20buen%20día,%20escribo%20desde%20Buffet%20App%20y%20quiero%20hacer%20un%20pedido"))
        startActivity(intent)
        //Toast.makeText(this, "Click Corto.", Toast.LENGTH_SHORT).show()
    }

    override fun onLongClick(bodas: Bodas) {

        //Toast.makeText(this, "Click Largo.", Toast.LENGTH_SHORT).show()
    }
}