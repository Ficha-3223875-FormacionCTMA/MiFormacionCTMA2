package com.sofia.miformacionctma

import com.sofia.miformacionctma.ui.screens.validarFormularioActividad
import com.sofia.miformacionctma.ui.state.FormularioActividadUiState
import org.junit.Assert.*
import org.junit.Test

class FormularioActividadTest {
    private val futura = "2099-12-31"
    @Test fun tituloMenorA3EsInvalido() { assertFalse(validarFormularioActividad(FormularioActividadUiState(titulo="AB",fecha=futura)).puedeGuardar) }
    @Test fun tituloDe3EsValido() { assertTrue(validarFormularioActividad(FormularioActividadUiState(titulo="ABC",fecha=futura)).puedeGuardar) }
    @Test fun descripcionMayorA240EsInvalida() { assertNotNull(validarFormularioActividad(FormularioActividadUiState(titulo="ABC",descripcion="x".repeat(241),fecha=futura)).errorDescripcion) }
    @Test fun progreso100EsValido() { assertTrue(validarFormularioActividad(FormularioActividadUiState(titulo="ABC",fecha=futura,progreso="100")).puedeGuardar) }
    @Test fun progreso101EsInvalido() { assertNotNull(validarFormularioActividad(FormularioActividadUiState(titulo="ABC",fecha=futura,progreso="101")).errorProgreso) }
    @Test fun fechaInvalidaEsDetectada() { assertNotNull(validarFormularioActividad(FormularioActividadUiState(titulo="ABC",fecha="2026-99-99")).errorFecha) }
    @Test fun fechaPasadaEsDetectada() { assertNotNull(validarFormularioActividad(FormularioActividadUiState(titulo="ABC",fecha="2020-01-01")).errorFecha) }
    @Test fun datosCompletosHabilitanGuardar() { assertTrue(validarFormularioActividad(FormularioActividadUiState(titulo="Actividad válida",descripcion="Descripción",fecha=futura,progreso="50")).puedeGuardar) }
}
