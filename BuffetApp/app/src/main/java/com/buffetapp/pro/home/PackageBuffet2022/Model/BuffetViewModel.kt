package com.buffetapp.pro.home.PackageBuffet2022.Adapter.Model.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BuffetViewModel : ViewModel() {

    private val repository : BuffetRepository
    private val _allBuffet = MutableLiveData<List<Buffet>>()
    val allBuffet : LiveData<List<Buffet>> = _allBuffet

    init {
        repository = BuffetRepository().getInstance()
        repository.loadBuffet(_allBuffet)
    }
}