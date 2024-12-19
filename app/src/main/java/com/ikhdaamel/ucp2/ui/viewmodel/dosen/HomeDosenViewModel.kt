package com.ikhdaamel.ucp2.ui.viewmodel.dosen

import androidx.core.app.NotificationCompat.MessagingStyle.Message
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikhdaamel.ucp2.data.entity.Dosen
import com.ikhdaamel.ucp2.repository.RepoDosen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeDosenViewModel(
    private val repoDosen: RepoDosen

): ViewModel() {
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
    val isLoading: Boolean =false,
    val isError: Boolean = false,
    val errorMessage: String =""
)