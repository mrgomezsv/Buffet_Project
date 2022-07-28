package com.buffetapp.pro


import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.ImageSlider
import com.google.firebase.firestore.FirebaseFirestore
import android.os.Bundle
import com.buffetapp.pro.R
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.denzcoskun.imageslider.constants.ScaleTypes
import android.widget.Toast
import java.util.ArrayList

class Java2 : AppCompatActivity() {
    private var imageSlider: ImageSlider? = null
    var database = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_java2)

        imageSlider = findViewById(R.id.imageSlider)
        val slideModels = ArrayList<SlideModel>()

        database.collection("Images").get()
            .addOnCompleteListener { task -> //now we will check if task is successful
                if (task.isSuccessful) {
                    for (queryDocumentSnapshot in task.result) {
                        slideModels.add(
                            SlideModel(
                                queryDocumentSnapshot.getString("url"),
                                ScaleTypes.FIT
                            )
                        )

                        var imageSlider : ImageSlider = findViewById(R.id.imageSlider)
                        imageSlider.setImageList(slideModels, ScaleTypes.FIT)
                    }
                } else {
                    Toast.makeText(this@Java2, "No puedo cargar imágenes", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(
                    this@Java2,
                    "No puedo cargar imágenes",
                    Toast.LENGTH_SHORT
                ).show()
            }

        //Now run the app
    }
}