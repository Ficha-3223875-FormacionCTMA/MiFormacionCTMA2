# Semana 8 · Servicios web, caché y resiliencia

## Arquitectura
`API REST → ActividadDto → RemoteActividadDataSource → ActividadRepository → Room → Flow → ViewModel → StateFlow → Compose`.
Room sigue siendo la fuente canónica. Un error remoto no vacía la tabla. `ActividadDto` está separado de Entity y dominio.

## Contrato educativo
- `GET /v1/actividades`: 200 arreglo JSON, 401, 5xx.
- `GET /v1/actividades/{id}`: 200, 404.
- `POST /v1/actividades/{id}/evidencias`: multipart, utilizado en Semana 9.
Campos del listado: `id`, `titulo`, `descripcion?`, `progreso`, `dias_restantes`, `prioridad`.

## Red y seguridad
Retrofit + kotlinx.serialization + OkHttp. Timeouts explícitos: conexión 10 s, lectura/escritura 15 s. `TokenProvider` mantiene el token de sesión en memoria; no hay tokens reales en código, BuildConfig, README ni logs. `CancellationException` se relanza.

## Política de caché
El refresh valida primero la respuesta y luego reemplaza Room en una transacción DAO. Timeout, 401, 5xx, JSON inválido o desconexión conservan el último dato local. La UI separa `ListadoUiState` de `RefreshUiState`.

## Casos obligatorios
CA-01 200 con actividades: cubierto por mapper + refresh + prueba MockWebServer.
CA-02 200 vacío: prueba automatizada; vacío es respuesta válida.
CA-03 timeout con caché: el Repository no borra Room antes de red.
CA-04 sin red/sin caché: `RefreshUiState.Fallida` y botón Actualizar API permite reintento.
CA-05 401: `RemoteFailure.Sesion`, sin exposición de token.
CA-06 500/JSON inválido: clasificación y caché conservado.
CA-07 refresh rápidos: operaciones suspend/cancelables; Room se actualiza transaccionalmente.
CA-08 cancelación: `CancellationException` no se transforma en error visible.

## Pruebas
`RemoteActividadDataSourceTest`: 200, vacío, 401, 500, JSON inválido y timeout con MockWebServer. No depende de internet real.

## Limitación deliberada
Las URLs stage/prod son dominios `.invalid` porque la guía no suministra un backend institucional real. Para una demostración contra backend se debe reemplazar únicamente la URL de ambiente, sin cambiar UI ni arquitectura.
