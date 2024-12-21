package com.ikhdaamel.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikhdaamel.ucp2.data.entity.Dosen
import com.ikhdaamel.ucp2.data.entity.MataKuliah
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
        fetchData()
    }

    private fun fetchData(){
        _homeUiState.value = _homeUiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val listDosen = repoDosen.getAllDosen().first()
                val listMatKul = repoMataKuliah.getAllMataKuliah().first()

                _homeUiState.value = _homeUiState.value.copy(
                    listDosen = listDosen,
                    listMatKul = listMatKul,
                    isLoading = false
                )
            } catch (e: Exception) {
                _homeUiState.value = _homeUiState.value.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = "Ada Yang Salah"
                )
            }
        }

    }

}

data class HomeUiState(
    val listDosen : List<Dosen> = emptyList(),
    val listMatKul: List<MataKuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)
