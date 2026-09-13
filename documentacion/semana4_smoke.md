# Semana 4 - Suite de Smoke Testing

## Proyecto: MiFormacionCTMA2

## 1. Objetivo

Ejecutar una prueba de humo sobre las funciones críticas de MiFormacionCTMA2 y del entorno académico API/MOCK de Semana 4, con el fin de decidir si el producto se encuentra suficientemente estable para continuar con las pruebas exploratorias y de regresión.

La duración máxima definida para esta suite es de 20 minutos.

---

## 2. Casos seleccionados

Se seleccionaron cinco casos críticos:

| ID | Descripción | Entorno |
|---|---|---|
| CP-MF-001 | Crear actividad con datos válidos | Android |
| CP-MF-005 | Rechazar progreso mayor a 100 | Android |
| CP-MF-010 | Consultar detalle de actividad existente | Android |
| CP-MF-012 | Regresar desde detalle hacia la lista | Android |
| CP-MF-AUT-001 | Acceso no autorizado a recurso | API/MOCK |

---

## 3. Criterios de resultado

### PASS

El resultado real coincide con el comportamiento esperado.

### FAIL

El resultado real presenta una diferencia reproducible frente al comportamiento esperado.

### BLOCKED

La ejecución no puede completarse debido a una condición externa documentada.

La falta de tiempo no se considera BLOCKED.

---

## 4. Ejecución

| Caso | Resultado esperado | Resultado real | Estado |
|---|---|---|---|
| CP-MF-001 | La actividad válida se crea y aparece en la lista | La actividad fue creada correctamente | PASS |
| CP-MF-005 | La aplicación rechaza progreso 101 | La validación impidió el guardado | PASS |
| CP-MF-010 | Se abre el detalle de la actividad seleccionada | El detalle fue mostrado correctamente | PASS |
| CP-MF-012 | Desde detalle se puede regresar a la lista | La navegación regresó correctamente a la lista | PASS |
| CP-MF-AUT-001 | El entorno simulado responde 403 ante acceso no autorizado | La solicitud respondió 403 Forbidden y los tests pasaron | PASS |

---

## 5. Evidencias utilizadas

### Android

- `evidencias/semana4/06-actividad-guardada.png`
- `evidencias/semana4/04-validaciones-formulario.png`
- `evidencias/semana4/02-detalle-actividad.png`

### API/MOCK

- `evidencias/semana4_pruebas/14_postman_403_autorizacion_tests.png`
- `evidencias/semana4_pruebas/15_postman_runner_resumen.png`

---

## 6. Resultado de la suite

Total de casos ejecutados: 5

- PASS: 5
- FAIL: 0
- BLOCKED: 0

---

## 7. Decisión de continuidad

**CONTINUAR**

La suite de humo no identificó fallos bloqueantes en las funciones críticas seleccionadas.

Por esta razón, se considera que la versión evaluada es suficientemente estable para continuar con las pruebas exploratorias y de regresión de la Semana 4.

---

## 8. Conclusión

La ejecución de la suite de humo permitió validar rápidamente funciones críticas relacionadas con creación, validación, consulta de detalle, navegación y autorización simulada.

Los cinco casos seleccionados obtuvieron resultado PASS, por lo cual no se identificaron condiciones que impidan continuar con las siguientes actividades de prueba.