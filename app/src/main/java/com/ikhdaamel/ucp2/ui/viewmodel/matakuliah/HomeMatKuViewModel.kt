package com.ikhdaamel.ucp2.ui.viewmodel.matakuliah

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikhdaamel.ucp2.data.entity.Dosen
import com.ikhdaamel.ucp2.data.entity.MataKuliah
import com.ikhdaamel.ucp2.repository.RepoDosen
import com.ikhdaamel.ucp2.repository.RepoMataKuliah
import com.ikhdaamel.ucp2.ui.viewmodel.dosen.HomeDosenUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeMatKulViewModel(
    private val repoMataKuliah: RepoMataKuliah
): ViewModel() {
    val homeMatKulUiState: StateFlow<HomeMatKulUiState> = repoMataKuliah.getAllMataKuliah()
        .filterNotNull()
        .map {
            HomeMatKulUiState(
                listMatKul = it.toList(),
                isLoading = false
            )
        }
        .onStart {
            emit(HomeMatKulUiState(isLoading = true))
            delay(900)
        }
        .catch{
            emit(
                HomeMatKulUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeMatKulUiState(
                isLoading = true,
            )
        )
}


data class HomeMatKulUiState(
    val listMatKul: List<MataKuliah> = listOf(),
    val isLoading: Boolean =false,
    val isError: Boolean = false,
    val errorMessage: String =""
)