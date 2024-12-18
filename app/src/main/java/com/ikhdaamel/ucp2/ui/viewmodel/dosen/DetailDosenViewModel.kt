package com.ikhdaamel.ucp2.ui.viewmodel.dosen

import androidx.lifecycle.SavedStateHandle
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

class DetailDosenViewModel (
    savedStateHandle: SavedStateHandle,
    private val repoDosen: RepoDosen,
) : ViewModel() {
    private val _nidn: String = checkNotNull(savedStateHandle[DestinasiDetail.NIDN])

    val dosenDetailUiState: StateFlow<DosenDetailUiState> = repoDosen.getAllDosen(_nidn)
        .filterNotNull()
        .map {
            DosenDetailUiState(
                dosenDetailUiState = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DosenDetailUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DosenDetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "terjadi Kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DosenDetailUiState(
                isLoading = true
            ),
        )
}

data class DosenDetailUiState(
    val dosenDetailUiState: DosenEvent = DosenEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String =""
){
    val isUiEventEmpty: Boolean
        get() = dosenDetailUiState == DosenEvent()
    val isEventNotEmpty: Boolean
        get() = dosenDetailUiState != DosenEvent()
}

fun Dosen.toDetailUiEvent(): DosenEvent{
    return DosenEvent(
        nidn = nidn,
        nama = nama,
        jeniKelamin = jeniKelamin
    )
}