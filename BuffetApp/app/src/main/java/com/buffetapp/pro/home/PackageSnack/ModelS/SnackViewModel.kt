package com.buffetapp.pro.home.PackageSnack.ModelS

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buffetapp.pro.home.PackageSnack.RepositoryS.SnackRepository


class SnackViewModel : ViewModel() {

    private val repository : SnackRepository
    private val _allSnacks = MutableLiveData<List<Snack>>()
    val allSnacks : LiveData<List<Snack>> = _allSnacks

    init {
        repository = SnackRepository().getInstance()
        repository.loadLunch(_allSnacks)
    }
}