package com.buffetapp.pro.home.PackageLunch.Repository

import androidx.lifecycle.MutableLiveData
import com.buffetapp.pro.home.PackageLunch.Model.Lunch
import com.google.firebase.database.*

class LunchRepository {

        private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("lunch")

        @Volatile private var INSTANCE : LunchRepository ?= null

        fun getInstance() : LunchRepository{
            return INSTANCE ?: synchronized(this){

                val instance = LunchRepository()
                INSTANCE = instance
                instance
            }
        }

        fun loadLunch(lunchList : MutableLiveData<List<Lunch>>){
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    try{

                        val _lunchList : List<Lunch> = snapshot.children.map { dataSnapshot ->

                            dataSnapshot.getValue(Lunch::class.java)!!
                        }

                        lunchList.postValue(_lunchList)

                    }catch(e: Exception){

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })
        }
    }
