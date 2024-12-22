package com.ikhdaamel.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikhdaamel.ucp2.data.entity.Dosen
import com.ikhdaamel.ucp2.repository.RepoDosen
import kotlinx.coroutines.launch

class DosenViewModel(private val repoDosen: RepoDosen): ViewModel() {
    var dosenUiState by mutableStateOf(DosenUiState())

    fun updateState(dosenEvent: DosenEvent){
        dosenUiState = dosenUiState.copy(
            dosenEvent = dosenEvent
        )
    }

    private fun validateFields(): Boolean {
        val event = dosenUiState.dosenEvent
        val errorState = DosenFormErrorState(
            nidn = if (event.nidn.isNotEmpty()) null else "NIDN harus diisi",
            nama = if (event.nama.isNotEmpty()) null else "Nama harus diisi",
            jeniKelamin = if (event.jeniKelamin.isNotEmpty()) null else "Jenis Kelamin harus diisi",
        )
        dosenUiState = dosenUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData(){
        val currentEvent = dosenUiState.dosenEvent
        if (validateFields()){
            viewModelScope.launch {
                try {
                    repoDosen.insertDosen(currentEvent.toDosenEntity())
                    dosenUiState = dosenUiState.copy(
                        snackBarMessage = "Data Tersimpan",
                        dosenEvent = DosenEvent(),
                        isEntryValid = DosenFormErrorState()
                    )
                } catch (e: Exception) {
                    dosenUiState = dosenUiState.copy(
                        snackBarMessage = "Data Tidak tersimpan"
                    )
                }
            }
        } else {
            dosenUiState = dosenUiState.copy(
                snackBarMessage = "Input Tidak Valid, Periksa Data"
            )
        }
    }
    fun resetSnackBarMessage(){
        dosenUiState = dosenUiState.copy(snackBarMessage = null)
    }
}

data class DosenUiState(
    val dosenEvent: DosenEvent = DosenEvent(),
    val isEntryValid: DosenFormErrorState = DosenFormErrorState(),
    val snackBarMessage: String? = null,
)

data class DosenFormErrorState(
    val nidn: String? = null,
    val nama: String? = null,
    val jeniKelamin: String? = null,
){
    fun isValid(): Boolean{
        return nidn == null && nama == null && jeniKelamin == null
    }
}

fun DosenEvent.toDosenEntity(): Dosen = Dosen(
    nidn = nidn,
    nama = nama,
    jeniKelamin = jeniKelamin
)

data class DosenEvent(
    val nidn: String ="",
    val nama: String ="",
    val jeniKelamin: String =""
)