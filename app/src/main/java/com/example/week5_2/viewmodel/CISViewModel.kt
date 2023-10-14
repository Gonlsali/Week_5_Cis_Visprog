package com.example.week5_2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.week5_2.model.MatkulUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CISViewModel: ViewModel() {

    private val _cisUIState = MutableStateFlow<List<MatkulUIState>>(emptyList())
    val cisUIState: StateFlow<List<MatkulUIState>> = _cisUIState.asStateFlow()

    private var totalIpk by mutableStateOf(0.0)
    private var totalSks by mutableStateOf(0)

    private fun calculateIpk() {
        val copyOfList = _cisUIState.value
        var jumlahIpk = 0.0
        var jumlahSks = 0

        try {
            copyOfList.forEach {
                jumlahIpk += it.ipk
                jumlahSks += it.sks
            }
        } catch (e: Exception) {
            jumlahIpk = 0.0
            jumlahSks = 0
        }
        this.totalIpk = jumlahIpk / jumlahSks
        this.totalSks = jumlahSks
    }

    fun addMatkul(name: String, sks: String, score: String) {
        val matkul = MatkulUIState(name, sks.toInt(), score.toDouble(), sks.toInt()*score.toDouble())

        val list = _cisUIState.value.toMutableList()
        list.add(matkul)
        _cisUIState.value = list
        calculateIpk()
    }

    fun deleteMatkul(matkul: MatkulUIState) {
        val list = _cisUIState.value.toMutableList()
        _cisUIState.value = list - matkul
        calculateIpk()
    }

    fun getSks(): String{
        return this.totalSks.toString()
    }

    fun getIpk(): String {
        return if(_cisUIState.value.isEmpty())
            "0.0"
        else
            this.totalIpk.toString()
    }
}