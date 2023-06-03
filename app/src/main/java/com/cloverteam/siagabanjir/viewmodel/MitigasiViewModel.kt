package com.cloverteam.siagabanjir.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cloverteam.siagabanjir.model.Mitigasi

class MitigasiViewModel: ViewModel(){

    private val data = MutableLiveData<List<Mitigasi>>()
    private var counter = 1
    init {
        data.value = initData()
        counter = data.value?.size ?: 0
    }


    private fun initData(): List<Mitigasi>{
        return listOf(
            Mitigasi("Membangun tanggul"),
            Mitigasi("Membangun embung"),
            Mitigasi("Membangun saluran air"),
            Mitigasi("Membangun jaringan irigasi"),
            Mitigasi("Membangun jaringan drainase"),
            Mitigasi("Membangun jaringan pipa air bersih"),
            Mitigasi("Membangun jaringan pipa air kotor"),
            Mitigasi("Membangun jaringan listrik"),
            Mitigasi("Membangun jaringan telekomunikasi"),
            Mitigasi("Membangun jalan"),
            Mitigasi("Membangun jembatan"),
            Mitigasi("Membangun tempat penampungan sampah"),
            Mitigasi("Membangun tempat pembuangan sampah"),
            Mitigasi("Membangun tempat pembuangan limbah"),
            Mitigasi("Membangun tempat pembuangan limbah medis"),
            Mitigasi("Membangun tempat pembuangan limbah industri"),
            Mitigasi("Membangun tempat pembuangan limbah rumah tangga"),
            Mitigasi("Membangun tempat pembuangan limbah pertanian"),
            Mitigasi("Membangun tempat pembuangan limbah peternakan"),
            Mitigasi("Membangun tempat pembuangan limbah perikanan"),
        )
    }
    fun getData(): MutableLiveData<List<Mitigasi>> = data
}