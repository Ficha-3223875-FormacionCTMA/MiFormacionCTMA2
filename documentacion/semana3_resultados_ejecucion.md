# Resultados de ejecución - Semana 3

## Proyecto: MiFormacionCTMA2

## 1. Objetivo

Registrar los resultados obtenidos durante la ejecución de los casos de prueba diseñados para MiFormacionCTMA2 y relacionarlos con las evidencias disponibles.

Estados utilizados:

- **PASS:** el comportamiento observado coincide con el resultado esperado.
- **FAIL:** existe una diferencia entre el resultado esperado y el observado.
- **PENDIENTE:** el caso está diseñado, pero todavía no se dispone de evidencia suficiente para confirmar su resultado.

---

# 2. Resultados

| ID | Caso de prueba | Resultado | Evidencia / observación |
|---|---|---|---|
| CP-MF-001 | Crear actividad con datos válidos | PASS | `evidencias/semana4/06-actividad-guardada.png` |
| CP-MF-002 | Progreso mínimo = 0 | PENDIENTE | Requiere comprobación específica del límite inferior. |
| CP-MF-003 | Progreso máximo = 100 | PENDIENTE | Requiere comprobación específica del límite superior. |
| CP-MF-004 | Progreso menor al mínimo = -1 | PENDIENTE | Requiere comprobación específica. |
| CP-MF-005 | Progreso mayor al máximo = 101 | PASS | La evidencia de validaciones muestra rechazo de progreso superior a 100: `evidencias/semana4/04-validaciones-formulario.png`. |
| CP-MF-006 | Título demasiado corto | PASS | La validación fue comprobada en `evidencias/semana4/04-validaciones-formulario.png`. |
| CP-MF-007 | Fecha anterior a la actual | PASS | La fecha inválida fue comprobada en `evidencias/semana4/04-validaciones-formulario.png`. |
| CP-MF-008 | Doble pulsación en Guardar | PASS | Se comprobó que solo se crea una actividad: `evidencias/semana4/09-doble-guardado-controlado.png`. |
| CP-MF-009 | Conservar borrador al recrear Activity | PASS | El borrador permanece después de la rotación: `evidencias/semana4/07-remember-saveable-rotacion.png`. |
| CP-MF-010 | Abrir detalle de actividad existente | PASS | `evidencias/semana4/02-detalle-actividad.png`. |
| CP-MF-011 | Consultar ID inexistente | PASS | Se muestra un estado controlado sin cierre inesperado: `evidencias/semana4/08-id-inexistente.png`. |
| CP-MF-012 | Regresar desde detalle a la lista | PASS | Flujo de navegación comprobado durante las pruebas funcionales de la aplicación. |

---

# 3. Resumen provisional

- Casos diseñados: 12
- Casos con resultado PASS registrado: 9
- Casos con resultado FAIL registrado: 0
- Casos pendientes de comprobación específica: 3

Casos pendientes:

- CP-MF-002
- CP-MF-003
- CP-MF-004

---

# 4. Gestión de defectos

Durante las ejecuciones documentadas hasta el momento no se identificó una discrepancia reproducible entre el resultado esperado y el resultado observado.

Por esta razón, no se registra un defecto ficticio.

Si durante la ejecución de los casos pendientes se obtiene un resultado diferente al esperado, se deberá registrar un defecto incluyendo:

- Identificador.
- Título.
- Ambiente.
- Precondiciones.
- Pasos para reproducir.
- Resultado esperado.
- Resultado actual.
- Severidad.
- Prioridad.
- Evidencia.
- Caso de prueba relacionado.

---

# 5. Evidencias reutilizadas

Las evidencias utilizadas fueron obtenidas durante pruebas reales realizadas previamente sobre MiFormacionCTMA2 y se reutilizan debido a que demuestran los comportamientos evaluados por los casos de prueba de esta guía.

No se generaron evidencias duplicadas cuando ya existía una captura suficiente para demostrar el comportamiento correspondiente.