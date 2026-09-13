# Semana 4 - Matriz de Trazabilidad Actualizada

## Proyecto: MiFormacionCTMA2

## 1. Objetivo

Actualizar la trazabilidad de las pruebas de MiFormacionCTMA2 incorporando los resultados obtenidos durante la Semana 4.

La matriz relaciona:

**Historia / necesidad → criterio → riesgo → caso de prueba → ejecución → resultado → evidencia → defecto**

---

## 2. Convenciones

### Resultado

- **PASS:** el resultado obtenido coincide con el esperado.
- **FAIL:** el resultado obtenido no coincide con el esperado.
- **BLOCKED:** una condición externa impide completar la ejecución.
- **PENDIENTE:** el caso todavía no cuenta con una ejecución registrada.

### Defectos

Cuando no existe un defecto real asociado al caso se registra:

**N/A**

El defecto académico simulado se identifica explícitamente para evitar confundirlo con un defecto real de MiFormacionCTMA2.

---

## 3. Matriz de trazabilidad

| Necesidad / Historia | Criterio | Riesgo | Caso | Ejecución | Resultado | Evidencia | Defecto |
|---|---|---|---|---|---|---|---|
| Crear actividades | Permitir guardar una actividad cuando los datos son válidos | No poder registrar actividades | CP-MF-001 | Ejecutado | PASS | `semana4/06-actividad-guardada.png` | N/A |
| Validar progreso | Aceptar progreso igual a 0 | Rechazar incorrectamente un valor límite válido | CP-MF-002 | Pendiente | PENDIENTE | Sin evidencia específica | N/A |
| Validar progreso | Aceptar progreso igual a 100 | Rechazar incorrectamente un valor límite válido | CP-MF-003 | Pendiente | PENDIENTE | Sin evidencia específica | N/A |
| Validar progreso | Rechazar progreso menor a 0 | Permitir valores fuera del rango | CP-MF-004 | Pendiente | PENDIENTE | Sin evidencia específica | N/A |
| Validar progreso | Rechazar progreso mayor a 100 | Permitir valores fuera del rango | CP-MF-005 | Ejecutado | PASS | `semana4/04-validaciones-formulario.png` | N/A |
| Validar título | Exigir mínimo 3 caracteres | Crear actividades con títulos inválidos | CP-MF-006 | Ejecutado | PASS | `semana4/04-validaciones-formulario.png` | N/A |
| Validar fecha | Rechazar una fecha anterior a la actual | Registrar actividades con fecha inválida | CP-MF-007 | Ejecutado | PASS | `semana4/04-validaciones-formulario.png` | N/A |
| Controlar guardado | Evitar creación duplicada al pulsar Guardar repetidamente | Duplicación accidental de actividades | CP-MF-008 | Ejecutado | PASS | `semana4/09-doble-guardado-controlado.png` | BUG-SIM-MF-001* |
| Conservar estado | Mantener el borrador durante la recreación de la Activity | Pérdida accidental de información | CP-MF-009 | Ejecutado | PASS | `semana4/07-remember-saveable-rotacion.png` | N/A |
| Consultar actividad | Mostrar el detalle de una actividad existente | Mostrar información incorrecta | CP-MF-010 | Ejecutado | PASS | `semana4/02-detalle-actividad.png` | N/A |
| Manejar ID inexistente | Mostrar un estado controlado sin cerrar inesperadamente | Crash o pantalla inconsistente | CP-MF-011 | Ejecutado | PASS | `semana4/08-id-inexistente.png` | N/A |
| Navegar entre pantallas | Permitir regresar desde DETALLE hacia LISTA | Pérdida del flujo de navegación | CP-MF-012 | Ejecutado | PASS | Evidencia funcional previa | N/A |
| Autorización académica | Rechazar acceso no autorizado | Acceso a un recurso sin permisos | CP-MF-AUT-001 | Ejecutado en API/MOCK | PASS | Evidencia Postman Semana 4 | N/A |

\* **BUG-SIM-MF-001** corresponde únicamente a un defecto simulado con fines académicos.  
El caso real CP-MF-008 obtuvo resultado PASS y no demuestra que exista actualmente ese defecto en la aplicación.

---

## 4. Trazabilidad de DevTools

| Tipo | Caso | Herramienta | Resultado | Evidencia |
|---|---|---|---|---|
| Positivo | Creación válida | Chrome DevTools | PASS | `01_devtools_network_caso_positivo.png` |
| Positivo | Inspección de headers | Chrome DevTools | PASS | `02_devtools_headers_post_201.png` |
| Positivo | Inspección de payload | Chrome DevTools | PASS | `03_devtools_payload_caso_positivo.png` |
| Negativo | Datos inválidos | Chrome DevTools | PASS | `04_devtools_caso_negativo_validacion.png` |
| Negativo | Registro en Console | Chrome DevTools | PASS | `05_devtools_console_caso_negativo.png` |
| Negativo | Registro en Application | Chrome DevTools | PASS | `06_devtools_application_localstorage.png` |

Las pruebas DevTools corresponden al entorno WEB académico simulado y no a la interfaz Android real.

---

## 5. Trazabilidad de Postman

| Solicitud | Método | Comportamiento evaluado | Resultado |
|---|---|---|---|
| 01 - Consultar actividad existente | GET | Consulta positiva | PASS |
| 02 - Crear actividad válida | POST | Creación positiva | PASS |
| 03 - Consultar actividad inexistente | GET | Respuesta 404 | PASS |
| 04 - Actualizar actividad | PUT | Actualización completa | PASS |
| 05 - Actualizar progreso parcialmente | PATCH | Actualización parcial | PASS |
| 06 - Eliminar actividad | DELETE | Eliminación | PASS |
| 07 - Solicitud sin autenticación válida | GET | Respuesta 401 simulada | PASS |
| 08 - Acceso no autorizado a recurso | GET | Respuesta 403 simulada | PASS |

### Collection Runner

- Tests ejecutados: **16**
- PASS: **16**
- FAIL: **0**
- Errors: **0**

Evidencia:

`evidencias/semana4_pruebas/15_postman_runner_resumen.png`

Las respuestas 401 y 403 son simulaciones académicas y no representan autenticación o autorización implementadas realmente en MiFormacionCTMA2.

---

## 6. Trazabilidad Lab 3

| Tipo de prueba | Casos / alcance | Resultado | Decisión |
|---|---|---|---|
| Smoke | 5 casos críticos | 5 PASS | CONTINUAR |
| Exploratoria | Charter de 25 minutos | PASS | Continuar a regresión |
| Regresión | 3 área modificada + 3 relacionados | 6 PASS | CONTINUAR |

Documentos relacionados:

- `documentacion/semana4_smoke.md`
- `documentacion/semana4_exploratoria.md`
- `documentacion/semana4_regresion.md`

---

## 7. Estado consolidado

Los casos ejecutados de la aplicación Android mantienen los resultados esperados.

Los casos CP-MF-002, CP-MF-003 y CP-MF-004 permanecen identificados como pendientes porque no cuentan con una ejecución específica registrada.

Las pruebas WEB/API se mantienen separadas de la aplicación real y se identifican como ejercicios académicos simulados.

No se identificaron nuevos defectos reales reproducibles durante las ejecuciones finales de Semana 4.

---

## 8. Conclusión

La matriz permite mantener la relación entre necesidades, criterios, riesgos, casos, ejecuciones, resultados, evidencias y defectos.

La actualización de Semana 4 incorpora las ejecuciones realizadas con Android, Chrome DevTools y Postman, además de los resultados de Smoke Testing, prueba exploratoria y regresión focalizada.

La trazabilidad diferencia explícitamente las funcionalidades reales de MiFormacionCTMA2 de los entornos WEB/API utilizados únicamente con fines académicos.