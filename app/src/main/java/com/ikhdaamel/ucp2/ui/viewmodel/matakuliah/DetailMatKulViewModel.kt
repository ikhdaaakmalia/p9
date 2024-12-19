package com.ikhdaamel.ucp2.ui.viewmodel.matakuliah

import androidx.core.app.NotificationCompat.MessagingStyle.Message
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikhdaamel.ucp2.data.entity.MataKuliah
import com.ikhdaamel.ucp2.repository.RepoMataKuliah
import com.ikhdaamel.ucp2.ui.navigation.DestinasiDetailMatKul
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailMatKulViewModel (
    savedStateHandle: SavedStateHandle,
    private val repoMataKuliah: RepoMataKuliah,
): ViewModel() {
    private val _kode: String = checkNotNull(savedStateHandle[DestinasiDetailMatKul.KODE])

    val detailUiState: StateFlow<MatKulDetailUiState> = repoMataKuliah.getMataKuliah(_kode)
        .filterNotNull()
        .map {
            MatKulDetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(MatKulDetailUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                MatKulDetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Ada Kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = MatKulDetailUiState(
                isLoading = true
            ),
        )
    fun deleteMatKul(){
        detailUiState.value.detailUiEvent.toMataKuliahEntity().let{
            viewModelScope.launch {
                repoMataKuliah.deleteMataKuliah(it)
            }
        }
    }
}

data class MatKulDetailUiState(
    val detailUiEvent: MataKuliahEvent = MataKuliahEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String =""
){
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == MataKuliahEvent()
    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != MataKuliahEvent()
}

fun MataKuliah.toDetailUiEvent(): MataKuliahEvent{
    return MataKuliahEvent(
        kode = kode,
        nama_mk = nama_mk,
        sks = sks,
        jenis = jenis,
        dosenPengampu = dosenPengampu
    )
}