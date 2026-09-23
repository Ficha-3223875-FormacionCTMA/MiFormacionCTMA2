# Semana 7 - Registro de casos de aceptación

| Caso | Implementación preparada | Verificación |
|---|---|---|
| CA-01 | `Cargando` inicial y `Vacio` si Room no emite actividades | Prueba `sinActividades_transitaDeCargandoAVacio` |
| CA-02 | Insertar modifica Room/Fake y el Flow actualiza `Contenido` sin refresco manual | Prueba `insertar_emiteContenidoYOperacionExitosa` + ejecución Android |
| CA-03 | DataStore conserva orden, filtro de prioridad y modo de visualización; `combine` recalcula | Prueba de filtro + reinicio manual |
| CA-04 | Búsqueda mediante `flatMapLatest` | Prueba `nuevaBusqueda_cancelaLaAnterior` |
| CA-05 | Repository puede emitir fallo simulado; UI muestra Error y Reintentar | Prueba `errorSimulado_muestraError_yReintentarRecupera` + botón Simular error |
| CA-06 | Guardado/eliminación pertenecen a `viewModelScope`; la cancelación se relanza | Inspección + navegación durante operación |
| CA-07 | StateFlow pertenece al ViewModel y Compose usa recolección lifecycle-aware; `rememberSaveable` conserva navegación de formulario | Prueba manual de rotación/recreación |
| CA-08 | Suite usa `runTest` y tiempo virtual, sin `Thread.sleep` | `ActividadViewModelTest` |

> Las verificaciones marcadas como ejecución Android deben capturarse en el emulador/dispositivo del aprendiz. No se fabrican capturas de ejecución.
