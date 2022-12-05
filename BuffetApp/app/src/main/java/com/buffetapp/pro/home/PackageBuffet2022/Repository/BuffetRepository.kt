package com.buffetapp.pro.home.PackageBuffet2022.Adapter.Model.Repository

import androidx.lifecycle.MutableLiveData


import com.google.firebase.database.*

class BuffetRepository {

    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("buffet")

    @Volatile private var INSTANCE : BuffetRepository?= null

    fun getInstance() : BuffetRepository {
        return INSTANCE ?: synchronized(this){

            val instance = BuffetRepository()
            INSTANCE = instance
            instance
        }
    }

    fun loadBuffet(buffetList : MutableLiveData<List<Buffet>>){
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                try{

                    val _buffetList : List<Buffet> = snapshot.children.map { dataSnapshot ->

                        dataSnapshot.getValue(Buffet::class.java)!!
                    }

                    buffetList.postValue(_buffetList)

                }catch(e: Exception){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
}