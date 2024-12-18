package com.ikhdaamel.ucp2.ui.viewmodel.dosen

import com.ikhdaamel.ucp2.data.entity.Dosen

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