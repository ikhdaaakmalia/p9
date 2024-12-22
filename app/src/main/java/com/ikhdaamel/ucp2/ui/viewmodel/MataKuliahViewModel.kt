package com.ikhdaamel.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikhdaamel.ucp2.data.entity.MataKuliah
import com.ikhdaamel.ucp2.repository.RepoMataKuliah
import kotlinx.coroutines.launch

class MataKuliahViewModel(private val repoMataKuliah: RepoMataKuliah): ViewModel(){
    var barangUiState by mutableStateOf(MataKulUiState())

    fun updateState (mataKuliahEvent: MataKuliahEvent) {
        barangUiState = barangUiState.copy(
            mataKuliahEvent = mataKuliahEvent
        )
    }

    private fun validateFields(): Boolean{
        val event = barangUiState.mataKuliahEvent
        val errorState = MatKulFormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode harus diisi",
            nama_mk = if (event.nama_mk.isNotEmpty()) null else "Nama Mata Kuliah harus diisi",
            sks = if (event.sks.isNotEmpty()) null else "SKS harus diisi",
            semester = if (event.semester.isNotEmpty()) null else "Semester harus diisi",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis Mata Kuliah harus diisi",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu harus diisi",
        )
        barangUiState = barangUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = barangUiState.mataKuliahEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repoMataKuliah.insertMataKuliah(currentEvent.toMataKuliahEntity())
                    barangUiState = barangUiState.copy(
                        snackBarMessage = "Data Tersimpan",
                        mataKuliahEvent = MataKuliahEvent(),
                        isEntryValid = MatKulFormErrorState()
                    )
                } catch (e: Exception) {
                    barangUiState = barangUiState.copy(
                        snackBarMessage = "Data Tidak tersimpan"
                    )
                }
            }
        } else {
            barangUiState = barangUiState.copy(
                snackBarMessage = "Input Tidak Valid, Periksa Data"
            )
        }
    }

    fun resetSnackBarMessage() {
        barangUiState = barangUiState.copy(snackBarMessage = null)
    }
}

data class MataKulUiState(
    val mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    val isEntryValid: MatKulFormErrorState = MatKulFormErrorState(),
    val snackBarMessage: String? = null,
)

data class MatKulFormErrorState(
    val kode: String? = null,
    val nama_mk: String? = null,
    val sks: String? = null,
    val semester: String? = null,
    val jenis: String? = null,
    val dosenPengampu: String? = null
){
    fun isValid(): Boolean{
        return kode == null && nama_mk == null && sks == null && semester == null && jenis == null && dosenPengampu == null
    }
}

fun MataKuliahEvent.toMataKuliahEntity(): MataKuliah = MataKuliah(
    kode = kode,
    nama_mk = nama_mk,
    sks = sks,
    semester = semester,
    jenis = jenis,
    dosenPengampu = dosenPengampu
)

data class MataKuliahEvent(
    val kode: String ="",
    val nama_mk: String ="",
    val sks: String ="",
    val semester: String ="",
    val jenis: String ="",
    val dosenPengampu: String =""
)