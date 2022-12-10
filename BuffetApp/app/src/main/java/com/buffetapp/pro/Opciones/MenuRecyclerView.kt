package com.buffetapp.pro.Opciones

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.buffetapp.pro.ClasesR2.MenuAdapter
import com.buffetapp.pro.ClasesR2.MenuProducto
import com.buffetapp.pro.ClasesR2.OnMenuListener
import com.buffetapp.pro.Connection.ConnectionLiveData
import com.buffetapp.pro.HomeActivity
import com.buffetapp.pro.R
import com.buffetapp.pro.databinding.ActivityMenuRecyclerViewBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.ktx.Firebase

class MenuRecyclerView : AppCompatActivity(), OnMenuListener {

    private lateinit var binding: ActivityMenuRecyclerViewBinding
    private lateinit var adapter: MenuAdapter
    private lateinit var firestoreListener: ListenerRegistration

    private lateinit var cld : ConnectionLiveData
    private lateinit var layout1 : ConstraintLayout
    private lateinit var layout2 : ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_menu_recycler_view)
        binding = ActivityMenuRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configRecyclerViewMenu()
        configFirestoreList()
        configFirestoreListRealTime()

        fechaT()

        title = "Menú"

        checkNetworkConnection()

        layout1 = findViewById(R.id.layout1)
        layout2 = findViewById(R.id.layout2)

        tryBtn()

    }

    private fun checkNetworkConnection(){
        cld = ConnectionLiveData(application)

        cld.observe(this) { isConnected ->

            if (isConnected) {
                layout1.visibility = View.VISIBLE
                layout2.visibility = View.GONE
            } else {
                layout1.visibility = View.GONE
                layout2.visibility = View.VISIBLE
            }
        }
    }

    private fun fechaT(){
        val database = Firebase.database.reference

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data0 = snapshot.getValue(String::class.java)
                findViewById<TextView>(R.id.txtFechaM).text = "$data0"
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        val dataRef = database.child("Data").child("FechaMenu")

        dataRef.addValueEventListener(listener)
    }

    private fun configRecyclerViewMenu() {
        adapter = MenuAdapter(mutableListOf(), this)
        binding.recyclerViewList2.apply {
            layoutManager = GridLayoutManager(this@MenuRecyclerView, 1,
                GridLayoutManager.VERTICAL, false)
            adapter = this@MenuRecyclerView.adapter
        }

        /*(1..5).forEach {
            val product = MenuProducto(it.toString(), "Producto $it", "Envio a Domicilio Gratis",
                "", it, it * 1.1)
            adapter.add(product)
        }*/
    }

    private fun configFirestoreList(){
        val db = FirebaseFirestore.getInstance()

        db.collection("lista_menu")
            .get()
            .addOnSuccessListener { snapshots ->
                for (document in snapshots){
                    val menu = document.toObject(MenuProducto::class.java)
                    menu.id = document.id
                    adapter.add(menu)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al consultar datos.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun configFirestoreListRealTime() {//Update en tiempo real//
        val db = FirebaseFirestore.getInstance()
        val menuRef = db.collection("lista_menu")

        firestoreListener = menuRef.addSnapshotListener { snapshots, error ->
            if (error != null){
                Toast.makeText(this, "Error al consultar datos.", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            for (snapshot in snapshots!!.documentChanges){
                val menu = snapshot.document.toObject(MenuProducto::class.java)
                menu.id = snapshot.document.id
                when(snapshot.type){
                    DocumentChange.Type.ADDED -> adapter.add(menu)
                    DocumentChange.Type.REMOVED -> adapter.delete(menu)
                    DocumentChange.Type.MODIFIED -> adapter.update(menu)

                }
            }
        }
    }

    override fun onClick(menuProducto: MenuProducto) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=+50378736282&text=Hola,%20buen%20día,%20escribo%20desde%20Buffet%20App%20y%20quiero%20hacer%20un%20pedido"))
        startActivity(intent)
        //Toast.makeText(this, "Click Corto.", Toast.LENGTH_SHORT).show()
    }

    override fun onLongClick(menuProducto: MenuProducto) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=+50378736282&text=Hola,%20buen%20día,%20escribo%20desde%20Buffet%20App%20y%20quiero%20hacer%20un%20pedido"))
        startActivity(intent)
    //Toast.makeText(this, "Click Largo.", Toast.LENGTH_SHORT).show()
    }

    private fun tryBtn() {
        val btnTry: Button = findViewById(R.id.try_again_btn)
        btnTry.setOnClickListener {
            val intent = Intent(this, MenuRecyclerView::class.java)
            startActivity(intent)
        }
    }
}
