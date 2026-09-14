# Mi Formación CTMA · Semana 7

## Concurrencia y estado reactivo

Este incremento continúa la Semana 6 sin reemplazar la persistencia local.

### Arquitectura

```text
Compose
   ↓
ViewModel + viewModelScope
   ↓
StateFlow / UiState
   ↓
Repository
   ↓
Room / DataStore
   ↓
Flow
   ↺
Compose
```

### Implementado

- Corrutinas coordinadas por `viewModelScope`.
- Repository con funciones `suspend` y `Flow`.
- `ListadoUiState`: Cargando, Contenido, Vacio y Error.
- `OperacionUiState`: Inactiva, EnCurso, Exitosa y Fallida.
- `OperacionUiState`: Inactiva, EnCurso, Exitosa y Fallida.
- Búsqueda reactiva con `debounce` y `flatMapLatest`.
- Ordenación y modo de visualización persistidos con DataStore.
- Recolección en Compose con `collectAsStateWithLifecycle`.
- Cancelación de operaciones anteriores mediante `Job`.
- `CancellationException` se relanza para respetar la cancelación estructurada.
- Pruebas deterministas con `runTest` y repositorio falso.

### Casos cubiertos

- Carga inicial y lista vacía.
- Inserción/eliminación mediante Room sin refresco manual.
- Persistencia de preferencias de orden y modo.
- Búsqueda rápida donde la consulta más reciente reemplaza a la anterior.
- Estados de error y acción de reintento preparados en la UI.
- Operaciones bajo `viewModelScope`, cancelables al destruir el ViewModel.
- Estado conservado por `StateFlow`.

### Decisiones técnicas

La UI no crea `CoroutineScope`, no usa `GlobalScope` ni decide dispatchers. El ViewModel coordina el trabajo y el Repository mantiene el acceso a datos. La búsqueda usa `debounce` + `flatMapLatest` para cancelar consultas obsoletas.

### Pruebas

Se añadió `ActividadesViewModelFlowTest` con `runTest`, `StandardTestDispatcher` y un repositorio falso. No se utilizan esperas reales como `Thread.sleep`.

### Nota

La guía de Semana 7 también contempla demostraciones instrumentadas de rotación, error simulado y evidencia visual. Estas deben verificarse en el emulador/dispositivo durante la sustentación.
