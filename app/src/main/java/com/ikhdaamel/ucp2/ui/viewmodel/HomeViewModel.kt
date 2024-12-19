package com.ikhdaamel.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikhdaamel.ucp2.repository.RepoDosen
import com.ikhdaamel.ucp2.repository.RepoMataKuliah
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repoDosen: RepoDosen,
    private val repoMataKuliah: RepoMataKuliah
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState(isLoading = true))
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    init {
        viewModelScope.launch {
            repoDosen.getAllDosen()
                .onStart {
                    _homeUiState.value = HomeUiState(isLoading = true)
                    delay(900)
                }
                .catch { exception ->
                    _homeUiState.value = HomeUiState(
                        isLoading = false,
                        isError = true,
                        errorMessage = exception.message ?: "Ada Yang Salah"
                    )
                }
                .collect { dosenList ->
                    _homeUiState.value = HomeUiState(
                        isLoading = false,
                        isError = false
                    )
                }
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)
