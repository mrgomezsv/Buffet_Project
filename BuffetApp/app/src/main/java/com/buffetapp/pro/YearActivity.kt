package com.buffetapp.pro

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.buffetapp.pro.OurServices.MerryEvent.OnBodasListener
import com.buffetapp.pro.OurServices.YearEvent.OnYearListener
import com.buffetapp.pro.OurServices.YearEvent.Year
import com.buffetapp.pro.OurServices.YearEvent.YearAdapter
import com.buffetapp.pro.databinding.ActivityYearBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class YearActivity : AppCompatActivity(), OnYearListener {

    private lateinit var binding: ActivityYearBinding
    private lateinit var adapter: YearAdapter
    private lateinit var firestoreListener: ListenerRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_year)
        binding = ActivityYearBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Fiestas de 15 Años"

        configRecyclerViewYear()
        configFirestoreListYear()
        configFirestoreListRealTimeYear()

    }

    private fun configRecyclerViewYear() {
        adapter = YearAdapter(mutableListOf(), this)
        binding.recyclerViewYear.apply {
            layoutManager = GridLayoutManager(this@YearActivity, 1,
                GridLayoutManager.VERTICAL, false)
            adapter = this@YearActivity.adapter
        }

        /*(1..5).forEach {
            val product = MenuProducto(it.toString(), "Producto $it", "Envio a Domicilio Gratis",
                "", it, it * 1.1)
            adapter.add(product)
        }*/
    }

    private fun configFirestoreListYear(){
        val db = FirebaseFirestore.getInstance()

        db.collection("year")
            .get()
            .addOnSuccessListener { snapshots ->
                for (document in snapshots){
                    val year = document.toObject(Year::class.java)
                    year.id = document.id
                    adapter.add(year)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al consultar datos.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun configFirestoreListRealTimeYear() {//Update en tiempo real//
        val db = FirebaseFirestore.getInstance()
        val yearRef = db.collection("year")

        firestoreListener = yearRef.addSnapshotListener { snapshots, error ->
            if (error != null){
                Toast.makeText(this, "Error al consultar datos.", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            for (snapshot in snapshots!!.documentChanges){
                val year = snapshot.document.toObject(Year::class.java)
                year.id = snapshot.document.id
                when(snapshot.type){
                    DocumentChange.Type.ADDED -> adapter.add(year)
                    DocumentChange.Type.REMOVED -> adapter.delete(year)
                    DocumentChange.Type.MODIFIED -> adapter.update(year)

                }
            }
        }
    }

    override fun onClick(year: Year) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=+50378736282&text=Hola,%20buen%20día,%20escribo%20desde%20Buffet%20App%20y%20quiero%20hacer%20un%20pedido"))
        startActivity(intent)
        //Toast.makeText(this, "Click Corto.", Toast.LENGTH_SHORT).show()
    }

    override fun onLongClick(year: Year) {
        //Toast.makeText(this, "Click Corto.", Toast.LENGTH_SHORT).show()
    }
}