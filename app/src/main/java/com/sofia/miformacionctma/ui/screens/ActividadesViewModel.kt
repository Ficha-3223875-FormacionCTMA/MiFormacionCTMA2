package com.sofia.miformacionctma.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sofia.miformacionctma.data.ActividadRepository
import com.sofia.miformacionctma.data.preferences.PreferenciasSource
import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.domain.Prioridad
import com.sofia.miformacionctma.ui.state.FormularioActividadUiState
import com.sofia.miformacionctma.ui.state.ListadoUiState
import com.sofia.miformacionctma.ui.state.OperacionUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ActividadesViewModel(
    private val repository: ActividadRepository,
    private val preferenciasRepository: PreferenciasSource
) : ViewModel() {

    private val consulta = MutableStateFlow("")
    private val refrescar = MutableStateFlow(0)
    val textoBusqueda: StateFlow<String> = consulta.asStateFlow()

    val preferencias = preferenciasRepository.preferencias
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            com.sofia.miformacionctma.data.preferences.PreferenciasUi()
        )

    private val actividadesFiltradas = combine(
        consulta.debounce(250).distinctUntilChanged(),
        refrescar
    ) { texto, _ -> texto }
        .flatMapLatest { texto ->
            if (texto.isBlank()) {
                repository.observarTodas()
            } else {
                repository.buscarPorTexto(texto.trim())
            }
        }

    val uiState: StateFlow<ListadoUiState> =
        combine(
            actividadesFiltradas,
            preferencias
        ) { actividades, prefs ->
            val ordenadas = when (prefs.orden) {
                "TITULO_DESC" -> actividades.sortedByDescending { it.titulo.lowercase() }
                "PROGRESO_ASC" -> actividades.sortedBy { it.progreso }
                "PROGRESO_DESC" -> actividades.sortedByDescending { it.progreso }
                else -> actividades.sortedBy { it.titulo.lowercase() }
            }

            if (ordenadas.isEmpty()) {
                ListadoUiState.Vacio
            } else {
                ListadoUiState.Contenido(ordenadas)
            }
        }
            .onStart { emit(ListadoUiState.Cargando) }
            .catch { throwable ->
                if (throwable is CancellationException) throw throwable
                emit(
                    ListadoUiState.Error(
                        throwable.message ?: "No fue posible cargar las actividades."
                    )
                )
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                ListadoUiState.Cargando
            )

    private val _operacionUiState =
        MutableStateFlow<OperacionUiState>(OperacionUiState.Inactiva)
    val operacionUiState: StateFlow<OperacionUiState> =
        _operacionUiState.asStateFlow()

    private var operacionJob: Job? = null

    fun cambiarBusqueda(texto: String) {
        consulta.value = texto
    }

    fun guardarOrden(orden: String) {
        viewModelScope.launch {
            preferenciasRepository.guardarOrden(orden)
        }
    }

    fun guardarModoVisualizacion(modo: String) {
        viewModelScope.launch {
            preferenciasRepository.guardarModoVisualizacion(modo)
        }
    }

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

        ejecutarOperacion {
            repository.agregar(nuevaActividad)
        }
    }

    fun eliminar(id: Long) {
        ejecutarOperacion {
            repository.eliminar(id)
        }
    }

    fun actualizar(actividad: ActividadFormativa) {
        ejecutarOperacion {
            repository.actualizar(actividad)
        }
    }

    fun buscar(id: Long): ActividadFormativa? {
        return when (val estado = uiState.value) {
            is ListadoUiState.Contenido ->
                estado.actividades.firstOrNull { it.id == id }
            else -> null
        }
    }

    fun reintentar() {
        refrescar.value += 1
    }

    private fun ejecutarOperacion(bloque: suspend () -> Unit) {
        operacionJob?.cancel()
        operacionJob = viewModelScope.launch {
            _operacionUiState.value = OperacionUiState.EnCurso
            try {
                bloque()
                _operacionUiState.value = OperacionUiState.Exitosa
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _operacionUiState.value =
                    OperacionUiState.Fallida(
                        e.message ?: "La operación no pudo completarse."
                    )
            }
        }
    }
}

class ActividadesViewModelFactory(
    private val repository: ActividadRepository,
    private val preferenciasRepository: PreferenciasSource
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActividadesViewModel::class.java)) {
            return ActividadesViewModel(
                repository,
                preferenciasRepository
            ) as T
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
        val formato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        formato.isLenient = false

        val fechaActividad = formato.parse(fecha) ?: return 0
        val hoy = Calendar.getInstance()
        val fechaHoy = formato.parse(formato.format(hoy.time)) ?: return 0

        ((fechaActividad.time - fechaHoy.time) / (1000 * 60 * 60 * 24)).toInt()
    } catch (e: Exception) {
        0
    }
}

/**
 * Datos de ejemplo de la Semana 3.
 * Se conservan como referencia y no se insertan automáticamente en Room.
 */
fun crearActividadesSemana3() = listOf(
    ActividadFormativa(1, "Configurar Android Studio", "Preparar el entorno de desarrollo", 100, -2, Prioridad.ALTA, ""),
    ActividadFormativa(2, "Kotlin básico", "Practicar variables y condiciones", 80, 1, Prioridad.ALTA, ""),
    ActividadFormativa(3, "Null safety", "Aplicar seguridad frente a valores nulos", 40, 2, Prioridad.MEDIA, ""),
    ActividadFormativa(4, "Entregar evidencia", "Subir las capturas del proyecto", 20, -1, Prioridad.ALTA, ""),
    ActividadFormativa(5, "Repasar colecciones", "Practicar listas y operaciones sobre colecciones", 0, 5, Prioridad.BAJA, ""),
    ActividadFormativa(6, "Funciones en Kotlin", "Practicar funciones y parámetros", 65, 3, Prioridad.MEDIA, ""),
    ActividadFormativa(7, "Data classes", "Crear modelos para la aplicación", 30, 4, Prioridad.MEDIA, ""),
    ActividadFormativa(8, "Colecciones Kotlin", "Trabajar con listas y filtros", 75, 6, Prioridad.BAJA, ""),
    ActividadFormativa(9, "Diseño de interfaz", "Preparar la interfaz de Mi Formación CTMA", 50, 7, Prioridad.MEDIA, ""),
    ActividadFormativa(10, "Evidencia Semana 3", "Preparar capturas de la aplicación", 10, 1, Prioridad.ALTA, "")
)

fun validarFormularioActividad(s: FormularioActividadUiState): FormularioActividadUiState {
    val titulo = s.titulo.trim()
    val errorTitulo =
        if (titulo.length !in 3..80) "El título debe tener entre 3 y 80 caracteres" else null

    val errorDescripcion =
        if (s.descripcion.length > 240) "La descripción no puede superar 240 caracteres" else null

    val errorFecha = validarFecha(s.fecha)
    val progreso = s.progreso.toIntOrNull()
    val errorProgreso =
        if (progreso == null || progreso !in 0..100) "El progreso debe estar entre 0 y 100" else null

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

fun validarFecha(fecha: String): String? {
    return try {
        val formato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        formato.isLenient = false
        val fechaIngresada = formato.parse(fecha) ?: return "Usa una fecha válida: AAAA-MM-DD"
        val hoy = Calendar.getInstance()
        val fechaHoy = formato.parse(formato.format(hoy.time))
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
