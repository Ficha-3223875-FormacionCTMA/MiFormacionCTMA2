# Semana 9 · Capacidades del dispositivo y seguridad

## Historia
Como Aprendiz, quiero adjuntar, reemplazar o eliminar una fotografía de evidencia en una actividad para conservar un soporte verificable sin conceder acceso general a mis archivos.

## Criterios
1. Photo Picker comparte solo una imagen y no solicita READ_MEDIA_IMAGES.
2. La cámara externa recibe una `content URI` creada con FileProvider; no se usa `file URI`.
3. Solo se aceptan MIME `image/*`, tamaño entre 1 byte y 5 MB y URI legible.
4. Room guarda URI y metadatos, nunca Bitmap/Base64.
5. La evidencia puede quedar LOCAL/FALLIDA sin perderse ante error remoto.
6. POST_NOTIFICATIONS se solicita solo al pulsar Recordatorios y solo en Android 13+.

## Persistencia y migración
`EvidenciaEntity` se relaciona 1:1 con `ActividadEntity` mediante FK con CASCADE. AppDatabase pasa de v2 a v3. `MIGRATION_2_3` crea tabla e índice único y existe prueba instrumental que conserva la actividad anterior.

## Riesgos y controles
| Riesgo | Control verificable |
|---|---|
| Acceso excesivo a galería | Photo Picker, sin READ_MEDIA_IMAGES |
| Exponer file URI | FileProvider + content URI |
| Imagen no válida | MIME + stream legible + límite 5 MB |
| Base inflada | Room guarda URI/metadatos, no binario |
| Pérdida por timeout | Estado FALLIDA conserva URI local |
| Token expuesto | TokenProvider en memoria; sin logs Authorization |
| HTTP en producción | stage/prod HTTPS; cleartext base desactivado |
| Permiso de notificación sin contexto | Solicitud solo al activar Recordatorios |
| Datos personales en evidencia | Texto de finalidad y revisión previa antes de sincronizar |

## Casos CA-01 a CA-09
CA-01 selector válido: vista previa + URI persistida. CA-02 cancelación: no modifica estado. CA-03 cámara: FileProvider. CA-04 tipo/tamaño inválido: rechazo. CA-05 reinicio: Room restaura URI/metadatos. CA-06 subida fallida: FALLIDA + Reintentar mediante Sincronizar. CA-07 notificación negada: app sigue usable. CA-08 eliminar: elimina registro y se intenta eliminar archivo propio. CA-09 prodRelease: URL HTTPS, sin cleartext general y sin logs sensibles.

## Ambientes
`dev`: 10.0.2.2 para backend local del emulador. `stage` y `prod`: HTTPS y dominios de marcador `.invalid` hasta recibir endpoints reales. Ninguna variante contiene credenciales.

## Pruebas
`EvidenciaValidatorTest` cubre MIME, archivo vacío y límite. `MigrationTest` cubre v2→v3. Las pruebas de red de Semana 8 cubren fallos de sincronización base.
