package com.buffetapp.pro.home.PackageLunch.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buffetapp.pro.home.PackageLunch.Repository.LunchRepository

class LunchViewModel : ViewModel() {

    private val repository : LunchRepository
    private val _allLunchs = MutableLiveData<List<Lunch>>()
    val allLunchs : LiveData<List<Lunch>> = _allLunchs

    init {
        repository = LunchRepository().getInstance()
        repository.loadLunch(_allLunchs)
    }
}