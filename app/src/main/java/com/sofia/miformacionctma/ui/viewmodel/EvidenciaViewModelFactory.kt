package com.sofia.miformacionctma.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sofia.miformacionctma.data.repository.EvidenciaRepository

class EvidenciaViewModelFactory(private val repository: EvidenciaRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EvidenciaViewModel::class.java)) return EvidenciaViewModel(repository) as T
        error("ViewModel desconocido: ${modelClass.name}")
    }
}
