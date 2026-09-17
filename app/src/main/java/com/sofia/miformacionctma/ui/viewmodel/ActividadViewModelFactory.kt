package com.sofia.miformacionctma.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sofia.miformacionctma.data.preferences.PreferencesRepository
import com.sofia.miformacionctma.data.repository.ActividadRepository

class ActividadViewModelFactory(
    private val repository: ActividadRepository,
    private val preferencesRepository: PreferencesRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(ActividadViewModel::class.java)) {
            return ActividadViewModel(
                repository = repository,
                preferencesRepository = preferencesRepository
            ) as T
        }

        throw IllegalArgumentException(
            "ViewModel desconocido: ${modelClass.name}"
        )
    }
}