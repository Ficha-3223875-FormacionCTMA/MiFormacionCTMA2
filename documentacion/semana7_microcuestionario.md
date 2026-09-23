# Semana 7 - Microcuestionario

1. **¿Una función suspend crea automáticamente un hilo?** No. Puede suspenderse sin bloquear, pero no implica un dispatcher específico.
2. **¿Por qué usar viewModelScope?** Porque el trabajo queda ligado al ciclo de vida del ViewModel y se cancela cuando este se limpia.
3. **¿Cuándo usar Dispatchers.IO?** Cuando la capa ejecuta una API realmente bloqueante de E/S; no por costumbre con Room/DataStore.
4. **¿Qué diferencia hay entre Flow y StateFlow?** Flow normalmente es frío; StateFlow es caliente y conserva un valor actual.
5. **¿Para qué sirve flatMapLatest?** Para cancelar la colección anterior cuando una entrada nueva la vuelve obsoleta.
6. **¿Por qué relanzar CancellationException?** Porque una cancelación esperada no debe transformarse en un error de usuario.
7. **¿Por qué separar ListadoUiState y OperacionUiState?** Para que guardar/eliminar no borre el contenido ni cree combinaciones imposibles de estados.
8. **¿Cómo se recolecta StateFlow en Compose?** Con `collectAsStateWithLifecycle`, evitando colecciones infinitas no conscientes del ciclo de vida.
