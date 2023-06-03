package com.cloverteam.siagabanjir.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cloverteam.siagabanjir.model.Mitigasi

class MitigasiViewModel: ViewModel(){

    private val data = MutableLiveData<List<Mitigasi>>()

    init {
        data.value = initData()
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
        )
    }
    fun getData(): MutableLiveData<List<Mitigasi>> = data
}