package com.ikhdaamel.ucp2.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikhdaamel.ucp2.data.entity.Dosen
import com.ikhdaamel.ucp2.repository.RepoDosen
import kotlinx.coroutines.launch

class DosenViewModel(private val repoDosen: RepoDosen): ViewModel() {
    var uiState by mutableStateOf(DosenUiState())

    fun updateState(dosenEvent: DosenEvent){
        uiState = uiState.copy(
            dosenEvent = dosenEvent
        )
    }

    private fun validateFields(): Boolean {
        val event = uiState.dosenEvent
        val errorState = FormErrorState(
            nidn = if (event.nidn.isNotEmpty()) null else "NIDN harus diisi",
            nama = if (event.nama.isNotEmpty()) null else "Nama harus diisi",
            jeniKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin harus diisi",
        )
    }

    fun saveData(){
        val currentEvent = uiState.dosenEvent
        if (validateFields()){
            viewModelScope.launch {
                try {
                    repoDosen.insertDosen(currentEvent.toDosenEntity())
                    uiState = uiState.copy(
                        SnackBaraMessage = "Data Tersimpan",
                        dosenEvent = DosenEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        SnackBarMessage = "Data Tidak tersimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                SnackBarMessage = "Input Tidak Valid, Periksa Data"
            )
        }
    }

}

data class DosenUiState(
    val dosenEvent: DosenEvent = DosenEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val SnackBarMessage: String? = null,
)

data class FormErrorState(
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