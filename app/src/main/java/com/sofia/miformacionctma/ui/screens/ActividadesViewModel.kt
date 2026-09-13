package com.sofia.miformacionctma.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sofia.miformacionctma.data.ActividadRepository
import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.domain.Prioridad
import com.sofia.miformacionctma.ui.state.FormularioActividadUiState
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ActividadesViewModel(
 private val repository: ActividadRepository
) : ViewModel() {

 val actividades: StateFlow<List<ActividadFormativa>> =
  repository.actividades

 fun agregar(s: FormularioActividadUiState) {

  val progreso = s.progreso.toIntOrNull() ?: 0

  val diasRestantes = calcularDiasRestantes(s.fecha)

  val nuevaActividad = ActividadFormativa(
   id = 0L,
   titulo = s.titulo.trim(),
   descripcion = s.descripcion.trim().ifBlank { null },
   progreso = progreso,
   diasRestantes = diasRestantes,
   prioridad = s.prioridad,
   fecha = s.fecha
  )

  repository.agregar(nuevaActividad)
 }

 fun buscar(id: Long): ActividadFormativa? {
  return actividades.value.firstOrNull { it.id == id }
 }
 fun eliminar(id: Long) {
  repository.eliminar(id)
 }
}

class ActividadesViewModelFactory(
 private val repository: ActividadRepository
) : ViewModelProvider.Factory {

 @Suppress("UNCHECKED_CAST")
 override fun <T : ViewModel> create(
  modelClass: Class<T>
 ): T {

  if (modelClass.isAssignableFrom(ActividadesViewModel::class.java)) {
   return ActividadesViewModel(repository) as T
  }

  throw IllegalArgumentException(
   "ViewModel desconocido: ${modelClass.name}"
  )
 }
}

/**
 * Calcula cuántos días faltan para la fecha indicada.
 * Se utiliza SimpleDateFormat para funcionar desde API 24.
 */
fun calcularDiasRestantes(fecha: String): Int {

 return try {

  val formato =
   SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

  formato.isLenient = false

  val fechaActividad =
   formato.parse(fecha) ?: return 0

  val hoy = Calendar.getInstance()

  val fechaHoy =
   formato.parse(formato.format(hoy.time))
    ?: return 0

  val diferencia =
   fechaActividad.time - fechaHoy.time

  (diferencia / (1000 * 60 * 60 * 24)).toInt()

 } catch (e: Exception) {

  0
 }
}

/**
 * Datos de ejemplo de la Semana 3.
 * Se conservan como referencia y no se insertan automáticamente
 * en Room para evitar duplicados cada vez que inicia la aplicación.
 */
fun crearActividadesSemana3() = listOf(

 ActividadFormativa(
  1,
  "Configurar Android Studio",
  "Preparar el entorno de desarrollo",
  100,
  -2,
  Prioridad.ALTA,
  ""
 ),

 ActividadFormativa(
  2,
  "Kotlin básico",
  "Practicar variables y condiciones",
  80,
  1,
  Prioridad.ALTA,
  ""
 ),

 ActividadFormativa(
  3,
  "Null safety",
  "Aplicar seguridad frente a valores nulos",
  40,
  2,
  Prioridad.MEDIA,
  ""
 ),

 ActividadFormativa(
  4,
  "Entregar evidencia",
  "Subir las capturas del proyecto",
  20,
  -1,
  Prioridad.ALTA,
  ""
 ),

 ActividadFormativa(
  5,
  "Repasar colecciones",
  "Practicar listas y operaciones sobre colecciones",
  0,
  5,
  Prioridad.BAJA,
  ""
 ),

 ActividadFormativa(
  6,
  "Funciones en Kotlin",
  "Practicar funciones y parámetros",
  65,
  3,
  Prioridad.MEDIA,
  ""
 ),

 ActividadFormativa(
  7,
  "Data classes",
  "Crear modelos para la aplicación",
  30,
  4,
  Prioridad.MEDIA,
  ""
 ),

 ActividadFormativa(
  8,
  "Colecciones Kotlin",
  "Trabajar con listas y filtros",
  75,
  6,
  Prioridad.BAJA,
  ""
 ),

 ActividadFormativa(
  9,
  "Diseño de interfaz",
  "Preparar la interfaz de Mi Formación CTMA",
  50,
  7,
  Prioridad.MEDIA,
  ""
 ),

 ActividadFormativa(
  10,
  "Evidencia Semana 3",
  "Preparar capturas de la aplicación",
  10,
  1,
  Prioridad.ALTA,
  ""
 )
)

fun validarFormularioActividad(
 s: FormularioActividadUiState
): FormularioActividadUiState {

 val titulo = s.titulo.trim()

 val errorTitulo =
  if (titulo.length !in 3..80) {
   "El título debe tener entre 3 y 80 caracteres"
  } else {
   null
  }

 val errorDescripcion =
  if (s.descripcion.length > 240) {
   "La descripción no puede superar 240 caracteres"
  } else {
   null
  }

 val errorFecha = validarFecha(s.fecha)

 val progreso = s.progreso.toIntOrNull()

 val errorProgreso =
  if (progreso == null || progreso !in 0..100) {
   "El progreso debe estar entre 0 y 100"
  } else {
   null
  }

 return s.copy(
  errorTitulo = errorTitulo,
  errorDescripcion = errorDescripcion,
  errorFecha = errorFecha,
  errorProgreso = errorProgreso,
  puedeGuardar =
   errorTitulo == null &&
           errorDescripcion == null &&
           errorFecha == null &&
           errorProgreso == null
 )
}

/**
 * Valida la fecha sin utilizar java.time,
 * por lo que funciona desde API 24.
 */
fun validarFecha(fecha: String): String? {

 return try {

  val formato =
   SimpleDateFormat(
    "yyyy-MM-dd",
    Locale.getDefault()
   )

  formato.isLenient = false

  val fechaIngresada =
   formato.parse(fecha)
    ?: return "Usa una fecha válida: AAAA-MM-DD"

  val hoy = Calendar.getInstance()

  val fechaHoy =
   formato.parse(formato.format(hoy.time))
    ?: return "Usa una fecha válida: AAAA-MM-DD"

  if (fechaIngresada.before(fechaHoy)) {

   "La fecha no puede ser anterior a hoy"

  } else {

   null
  }

 } catch (e: Exception) {

  "Usa una fecha válida: AAAA-MM-DD"
 }
}