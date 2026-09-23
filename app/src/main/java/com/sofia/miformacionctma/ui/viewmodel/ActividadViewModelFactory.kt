package com.sofia.miformacionctma.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sofia.miformacionctma.data.preferences.PreferenciasDataSource
import com.sofia.miformacionctma.data.repository.ActividadDataSource

class ActividadViewModelFactory(
    private val repository: ActividadDataSource,
    private val preferencesRepository: PreferenciasDataSource
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