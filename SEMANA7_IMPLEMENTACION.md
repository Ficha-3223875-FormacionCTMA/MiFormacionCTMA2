# Semana 7 · Implementación

Este ZIP contiene el incremento de Semana 7 sobre el proyecto de Semana 6.

## Archivos principales agregados/modificados

- `ui/state/ListadoUiState.kt`
- `ui/state/OperacionUiState.kt` (incluido en el mismo archivo de estados)
- `ui/screens/ActividadesViewModel.kt`
- `ui/screens/PantallaActividadesSemana7.kt`
- `data/ActividadRepository.kt`
- `data/preferences/PreferenciasRepository.kt`
- `MainActivity.kt`
- `ActividadesViewModelFlowTest.kt`
- dependencias Lifecycle Compose y kotlinx-coroutines-test
- migración Room 1→2 completada para incluir la tabla `actividades`

## Requisitos de la guía atendidos

Corrutinas, `viewModelScope`, `Flow`, `StateFlow`, `stateIn`, `SharingStarted.WhileSubscribed(5_000)`, `debounce`, `flatMapLatest`, estados de listado y operación, DataStore para preferencias, `collectAsStateWithLifecycle`, cancelación y pruebas deterministas con `runTest`.

## Importante

El proyecto se entrega sin `.git`, `.idea`, `.gradle`, `local.properties` ni carpetas de compilación. La rama de Git de Semana 7 debe crearse en el equipo a partir de la Semana 6:

`feature/semana-07-coroutines-flow`
