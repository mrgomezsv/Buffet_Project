package com.buffetapp.pro.home.PackageSnack.RepositoryS

import androidx.lifecycle.MutableLiveData
import com.buffetapp.pro.home.PackageSnack.ModelS.Snack


import com.google.firebase.database.*

class SnackRepository {

    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("snack")

    @Volatile private var INSTANCE : SnackRepository?= null

    fun getInstance() : SnackRepository {
        return INSTANCE ?: synchronized(this){

            val instance = SnackRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadLunch(snackList : MutableLiveData<List<Snack>>){
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                try{

                    val _snackList : List<Snack> = snapshot.children.map { dataSnapshot ->

                        dataSnapshot.getValue(Snack::class.java)!!
                    }

                    snackList.postValue(_snackList)

                }catch(e: Exception){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
}
