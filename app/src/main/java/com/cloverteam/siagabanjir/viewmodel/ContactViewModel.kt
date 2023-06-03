package com.cloverteam.siagabanjir.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cloverteam.siagabanjir.model.Contact

class ContactViewModel: ViewModel() {

    private val data = MutableLiveData<List<Contact>>()

    init {
        data.value = initData()
    }

    private fun initData():List<Contact>{
        return listOf(
            Contact("Pemadam Kebakaran", "113"),
            Contact("Polisi", "110"),
            Contact("Ambulans", "118"),
            Contact("PLN", "123"),
            Contact("Pengaduan PLN", "123"),
            Contact("Pengaduan PDAM", "123"),
            Contact("Pengaduan Telkom", "123"),
        )
    }
    fun getData(): LiveData<List<Contact>> = data

}