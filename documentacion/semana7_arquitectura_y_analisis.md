# Semana 7 - Arquitectura, análisis y mapa de concurrencia

## Diagrama y dueño de cada Flow

```text
Room (ActividadDao) ---- Flow<List<ActividadEntity>> ----┐
                                                        ├-> ActividadRepository
DataStore -------- Flow<PreferenciasUi> ----------------┘          |
                                                                  v
Búsqueda MutableStateFlow -> flatMapLatest -> Flow de dominio -> ActividadViewModel
                                                                  |
                           stateIn + viewModelScope + WhileSubscribed(5 s)
                                                                  |
                                      StateFlow<ListadoUiState>
                                      StateFlow<OperacionUiState>
                                                                  |
                                                                  v
                                      Compose + collectAsStateWithLifecycle
```

**Dueños:** Room es dueño del Flow persistente de actividades; DataStore del Flow de preferencias; ViewModel del texto de búsqueda y de los StateFlow de UI; Compose únicamente recolecta y emite eventos.

## Análisis de tres problemas

| Problema | Causa | Impacto | Corrección aplicada |
|---|---|---|---|
| ANR/bloqueo | Ejecutar trabajo bloqueante en Main | UI congelada | Room/DataStore asíncronos; no se introduce bloqueo ni IO por reflejo |
| Búsqueda obsoleta | Varias consultas siguen activas | Resultado viejo puede reemplazar al nuevo | `flatMapLatest` cancela la colección anterior |
| Error sin salida | Bandera/error sin acción de recuperación | Pantalla atrapada | `ListadoUiState.Error` incluye mensaje seguro y botón Reintentar |

## Mapa conceptual

- `suspend`: suspende sin bloquear; no crea un hilo por sí sola.
- `viewModelScope`: propietario de acciones y estado del ViewModel; se cancela al limpiarse.
- `Dispatcher`: se elige según el tipo real de trabajo, no por costumbre.
- `Job`: referencia cancelable del trabajo estructurado.
- `Flow`: secuencia asíncrona que emite cambios.
- `StateFlow`: mantiene el último estado disponible.
- `flatMapLatest`: reemplaza/cancela una búsqueda anterior cuando cambia la entrada.
- `stateIn`: convierte el Flow final en StateFlow.
- `collectAsStateWithLifecycle`: recolecta desde Compose respetando el ciclo de vida.
- `CancellationException`: se relanza; no se presenta como fallo del usuario.
