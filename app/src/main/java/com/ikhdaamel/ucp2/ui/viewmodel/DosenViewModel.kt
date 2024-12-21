package com.ikhdaamel.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikhdaamel.ucp2.data.entity.Dosen
import com.ikhdaamel.ucp2.repository.RepoDosen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DosenViewModel(private val repoDosen: RepoDosen): ViewModel() {
    var homeUiState by mutableStateOf(HomeDosenUiState())

    fun updateState(dosenEvent: DosenEvent){
        homeUiState = homeUiState.copy(
            dosenEvent = dosenEvent
        )
    }

    private fun validateFields(): Boolean {
        val event = homeUiState.dosenEvent
        val errorState = DosenFormErrorState(
            nidn = if (event.nidn.isNotEmpty()) null else "NIDN harus diisi",
            nama = if (event.nama.isNotEmpty()) null else "Nama harus diisi",
            jeniKelamin = if (event.jeniKelamin.isNotEmpty()) null else "Jenis Kelamin harus diisi",
        )
        homeUiState = homeUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData(){
        val currentEvent = homeUiState.dosenEvent
        if (validateFields()){
            viewModelScope.launch {
                try {
                    repoDosen.insertDosen(currentEvent.toDosenEntity())
                    homeUiState = homeUiState.copy(
                        SnackBarMessage = "Data Tersimpan",
                        dosenEvent = DosenEvent(),
                        isEntryValid = DosenFormErrorState()
                    )
                } catch (e: Exception) {
                    homeUiState = homeUiState.copy(
                        SnackBarMessage = "Data Tidak tersimpan"
                    )
                }
            }
        } else {
            homeUiState = homeUiState.copy(
                SnackBarMessage = "Input Tidak Valid, Periksa Data"
            )
        }
    }
    fun resetSnackBarMessage(){
        homeUiState = homeUiState.copy(SnackBarMessage = null)
    }

    val homeDosenUiState: StateFlow<HomeDosenUiState> = repoDosen.getAllDosen()
        .filterNotNull()
        .map {
            HomeDosenUiState(
                listDosen = it.toList(),
                isLoading = false
            )
        }
        .onStart {
            emit(HomeDosenUiState(isLoading = true))
            delay(900)
        }
        .catch{
            emit(
                HomeDosenUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeDosenUiState(
                isLoading = true,
            )
        )
}

data class HomeDosenUiState(
    val listDosen: List<Dosen> = listOf(),
    val dosenEvent: DosenEvent = DosenEvent(),
    val isEntryValid: DosenFormErrorState = DosenFormErrorState(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val SnackBarMessage: String? = null,
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