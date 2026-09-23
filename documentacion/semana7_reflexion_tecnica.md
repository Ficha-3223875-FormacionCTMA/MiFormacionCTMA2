# Semana 7 - Reflexión técnica

El incremento mantiene Room como fuente única de verdad y evita crear una segunda lista persistente en memoria. La principal decisión fue convertir el estado visible en una jerarquía sellada, de modo que la pantalla solo pueda encontrarse en Cargando, Contenido, Vacío o Error. Las operaciones de escritura tienen un estado separado para no ocultar el contenido durante un guardado o eliminación.

La búsqueda se implementó con `flatMapLatest` porque una entrada nueva vuelve obsoleta la consulta anterior. Esta decisión permite cancelación estructurada sin administrar Jobs manualmente. Para el ciclo de vida se usa `viewModelScope`, `stateIn` con `WhileSubscribed(5_000)` y `collectAsStateWithLifecycle`, evitando `GlobalScope` y colecciones permanentes desde Compose.

Se aceptó no imponer `Dispatchers.IO` en el ViewModel: Room y DataStore ya proporcionan APIs asíncronas. Se descartó crear `CoroutineScope(Dispatchers.IO)` desde la UI porque dejaría responsabilidades de concurrencia en la capa equivocada y dificultaría la cancelación y las pruebas.

Las pruebas usan repositorios falsos, `runTest` y tiempo virtual. Así se comprueban transiciones y cancelación sin `Thread.sleep`. La recuperación ante errores presenta mensajes comprensibles y una acción Reintentar, sin mostrar detalles internos de SQLite o trazas técnicas al usuario.
