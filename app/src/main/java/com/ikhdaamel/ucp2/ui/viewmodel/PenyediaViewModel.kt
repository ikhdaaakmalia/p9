package com.ikhdaamel.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.ikhdaamel.ucp2.KRSApp

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                KRSApp().containerApp.repoDosen,
                KRSApp().containerApp.repoMataKuliah
            )
        }
        initializer {
            DosenViewModel(
                KRSApp().containerApp.repoDosen
            )
        }
        initializer {
            DosenViewModel(
                KRSApp().containerApp.repoDosen
            )
        }
        initializer {
            MataKuliahViewModel(
                KRSApp().containerApp.repoMataKuliah
            )
        }
        initializer {
            UpdateMatKulViewModel(
                createSavedStateHandle(),
                KRSApp().containerApp.repoMataKuliah
            )
        }
        initializer {
          DetailMatKulViewModel(
                createSavedStateHandle(),
                KRSApp().containerApp.repoMataKuliah
          )
        }
    }
}

fun CreationExtras.krsApp(): KRSApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KRSApp)