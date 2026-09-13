# Semana 4 - Prueba de Regresión

## Proyecto: MiFormacionCTMA2

## 1. Objetivo

Ejecutar una regresión focalizada sobre MiFormacionCTMA2 para comprobar que las funcionalidades previamente aceptadas continúan comportándose correctamente después de los cambios relacionados con formulario, estado y navegación.

La regresión se compone de seis casos:

- 3 casos correspondientes al área modificada.
- 3 casos relacionados con funcionalidades que podrían verse afectadas.

---

## 2. Área modificada

Durante la evolución de MiFormacionCTMA2 se trabajó principalmente sobre:

- formulario de creación de actividades;
- validaciones;
- control del guardado;
- manejo de estado;
- navegación entre pantallas;
- consulta del detalle.

Por esta razón, estas funciones constituyen el foco principal de la regresión.

---

## 3. Casos del área modificada

| ID | Caso | Resultado anterior | Resultado actual |
|---|---|---|---|
| CP-MF-001 | Crear actividad con datos válidos | PASS | PASS |
| CP-MF-005 | Rechazar progreso 101 | PASS | PASS |
| CP-MF-008 | Evitar creación duplicada mediante doble guardado | PASS | PASS |

### Resultado

Los tres casos relacionados directamente con el área modificada mantienen el comportamiento previamente aceptado.

---

## 4. Casos relacionados

| ID | Caso | Resultado anterior | Resultado actual |
|---|---|---|---|
| CP-MF-009 | Conservar borrador al recrear la Activity | PASS | PASS |
| CP-MF-010 | Consultar detalle de actividad existente | PASS | PASS |
| CP-MF-012 | Regresar desde detalle hacia la lista | PASS | PASS |

### Resultado

Las funciones relacionadas con estado, consulta de detalle y navegación continúan funcionando después de los cambios evaluados.

---

## 5. Evidencias reutilizadas

Para la comparación se utilizaron evidencias existentes de las ejecuciones anteriores:

- `evidencias/semana4/06-actividad-guardada.png`
- `evidencias/semana4/04-validaciones-formulario.png`
- `evidencias/semana4/09-doble-guardado-controlado.png`
- `evidencias/semana4/07-remember-saveable-rotacion.png`
- `evidencias/semana4/02-detalle-actividad.png`

La navegación DETALLE → LISTA también había sido comprobada durante las pruebas funcionales anteriores.

---

## 6. Comparación de resultados

| Categoría | Casos | PASS | FAIL | BLOCKED |
|---|---:|---:|---:|---:|
| Área modificada | 3 | 3 | 0 | 0 |
| Funciones relacionadas | 3 | 3 | 0 | 0 |
| Total | 6 | 6 | 0 | 0 |

No se observaron regresiones respecto a los comportamientos previamente aceptados.

---

## 7. Defectos asociados

Durante esta regresión no se identificaron nuevos defectos reales reproducibles.

El defecto documentado previamente como simulado continúa identificado únicamente como ejercicio académico y no se considera un fallo real de la versión evaluada.

---

## 8. Resultado de regresión

**Resultado general: PASS**

Casos ejecutados: 6

- PASS: 6
- FAIL: 0
- BLOCKED: 0

---

## 9. Decisión

**La versión puede continuar.**

Los seis casos seleccionados conservaron los resultados previamente aceptados y no se identificaron impactos negativos derivados de los cambios evaluados.

---

## 10. Conclusión

La regresión focalizada permitió comprobar que las funciones principales de creación, validación, control de doble guardado, conservación del estado, consulta de detalle y navegación continúan funcionando correctamente.

Los tres casos del área modificada y los tres casos relacionados obtuvieron resultado PASS, por lo que no se detectaron regresiones en el alcance evaluado.