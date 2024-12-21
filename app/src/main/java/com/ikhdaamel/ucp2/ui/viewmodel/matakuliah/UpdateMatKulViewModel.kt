package com.ikhdaamel.ucp2.ui.viewmodel.matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikhdaamel.ucp2.data.entity.MataKuliah
import com.ikhdaamel.ucp2.repository.RepoMataKuliah
import com.ikhdaamel.ucp2.ui.navigation.DestinasiUpdate
import com.ikhdaamel.ucp2.ui.viewmodel.HomeMataKulUiState
import com.ikhdaamel.ucp2.ui.viewmodel.MatKulFormErrorState
import com.ikhdaamel.ucp2.ui.viewmodel.MataKuliahEvent
import com.ikhdaamel.ucp2.ui.viewmodel.toMataKuliahEntity
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

//data class MatKulUiState(
//    val mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
//    val isEntryValid: MatKulFormErrorState = MatKulFormErrorState(),
//    val SnackBarMessage: String? = null
//)
class UpdateMatKulViewModel (
    savedStateHandle: SavedStateHandle,
    private val repoMataKuliah: RepoMataKuliah
): ViewModel() {
    var updateUiState by mutableStateOf(HomeMataKulUiState())
        private set
    private val _kode: String = checkNotNull(savedStateHandle[DestinasiUpdate.KODE])

    init {
        viewModelScope.launch {
            updateUiState = repoMataKuliah.getMataKuliah(_kode)
                .filterNotNull()
                .first()
                .toUiStateMatKul()
        }
    }

    fun updateState(mataKuliahEvent: MataKuliahEvent) {
        updateUiState = updateUiState.copy(
            mataKuliahEvent = mataKuliahEvent
        )
    }

     fun validateFields(): Boolean{
        val event = updateUiState.mataKuliahEvent
        val errorState = MatKulFormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode harus diisi",
            nama_mk = if (event.nama_mk.isNotEmpty()) null else "Nama Mata Kuliah harus diisi",
            sks = if (event.sks.isNotEmpty()) null else "SKS harus diisi",
            semester = if (event.semester.isNotEmpty()) null else "Semester harus diisi",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis Mata Kuliah harus diisi",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu harus diisi",
        )
        updateUiState = updateUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
     }

    fun updateData(){
        val currentEvent = updateUiState.mataKuliahEvent

        if (validateFields()){
            viewModelScope.launch {
                try {
                    repoMataKuliah.updateMataKuliah(currentEvent.toMataKuliahEntity())
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data Berhasil Diupdate",
                        mataKuliahEvent = MataKuliahEvent(),
                        isEntryValid = MatKulFormErrorState()
                    )
                    println("snackBarMessage diatur: ${updateUiState.snackBarMessage}")
                } catch (e: Exception) {
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data Gagal Diupdate"
                    )
                }
            }
        } else {
            updateUiState = updateUiState.copy(
                snackBarMessage = "Data Gagal diupdate"
            )
        }
    }
    fun resetSnackBarMessage(){
        updateUiState = updateUiState.copy(snackBarMessage = null)
    }
}

fun MataKuliah.toUiStateMatKul() : HomeMataKulUiState = HomeMataKulUiState(
    mataKuliahEvent = this.toDetailUiEvent()
)