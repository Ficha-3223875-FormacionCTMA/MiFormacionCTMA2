# Matriz de trazabilidad - Semana 3

## Proyecto: MiFormacionCTMA2

## 1. Objetivo

Mantener la trazabilidad entre las historias de usuario, criterios de aceptación, riesgos, casos de prueba, técnicas aplicadas, resultados de ejecución y defectos.

La relación utilizada es:

Historia de usuario → Criterio de aceptación → Riesgo → Caso de prueba → Resultado → Defecto

---

# 2. Historias de usuario

## HU-MF-01 - Crear actividad

**Como aprendiz**, quiero crear una actividad formativa para organizar mis compromisos académicos desde la aplicación.

### CA-MF-01

Dado que el aprendiz se encuentra en el formulario de creación de actividad, cuando completa correctamente los campos obligatorios y selecciona Guardar, entonces la aplicación debe crear una sola actividad y mostrarla en la lista.

### CA-MF-02

Dado que el aprendiz ingresa información que incumple las reglas de validación, cuando intenta guardar la actividad, entonces la aplicación debe impedir el registro y mostrar el mensaje de validación correspondiente.

### Riesgo asociado

**RSK-MF-01 - Creación incorrecta o duplicada de actividades**

Una actividad podría registrarse con información inválida o crearse más de una vez debido a acciones repetidas del usuario.

**Prioridad del riesgo: Alta.**

---

## HU-MF-02 - Consultar detalle de una actividad

**Como aprendiz**, quiero consultar el detalle de una actividad para revisar su información y posteriormente regresar a la lista.

### CA-MF-03

Dado que existe una actividad registrada, cuando el aprendiz la selecciona desde la lista, entonces la aplicación debe mostrar su información en la pantalla de detalle.

### CA-MF-04

Dado que se solicita una actividad que no existe, cuando la aplicación intenta consultar su identificador, entonces debe mostrar un estado controlado sin producir un cierre inesperado.

### Riesgo asociado

**RSK-MF-02 - Error de navegación o consulta de actividades**

Existe el riesgo de que una navegación incorrecta, un identificador inexistente o un cambio de pantalla provoque información incorrecta o un comportamiento no controlado.

**Prioridad del riesgo: Media.**

---

# 3. Matriz de trazabilidad

| Historia | Criterio | Riesgo | Caso | Técnica | Tipo | Resultado | Defecto |
|---|---|---|---|---|---|---|---|
| HU-MF-01 | CA-MF-01 | RSK-MF-01 | CP-MF-001 | Partición de equivalencia | Positivo | PASS | Ninguno |
| HU-MF-01 | CA-MF-01 | RSK-MF-01 | CP-MF-002 | Valores límite | Positivo | PENDIENTE | No aplica |
| HU-MF-01 | CA-MF-01 | RSK-MF-01 | CP-MF-003 | Valores límite | Positivo | PENDIENTE | No aplica |
| HU-MF-01 | CA-MF-02 | RSK-MF-01 | CP-MF-004 | Valores límite | Negativo | PENDIENTE | No aplica |
| HU-MF-01 | CA-MF-02 | RSK-MF-01 | CP-MF-005 | Valores límite | Negativo | PASS | Ninguno |
| HU-MF-01 | CA-MF-02 | RSK-MF-01 | CP-MF-006 | Partición de equivalencia | Negativo | PASS | Ninguno |
| HU-MF-01 | CA-MF-02 | RSK-MF-01 | CP-MF-007 | Partición de equivalencia | Negativo | PASS | Ninguno |
| HU-MF-01 | CA-MF-01 | RSK-MF-01 | CP-MF-008 | Tabla de decisión | Negativo / control | PASS | Ninguno |
| HU-MF-01 | CA-MF-01 | RSK-MF-01 | CP-MF-009 | Transición / caso de uso | Positivo | PASS | Ninguno |
| HU-MF-02 | CA-MF-03 | RSK-MF-02 | CP-MF-010 | Caso de uso | Positivo | PASS | Ninguno |
| HU-MF-02 | CA-MF-04 | RSK-MF-02 | CP-MF-011 | Partición de equivalencia | Negativo | PASS | Ninguno |
| HU-MF-02 | CA-MF-03 | RSK-MF-02 | CP-MF-012 | Transición de estados | Positivo | PASS | Ninguno |

---

# 4. Trazabilidad de las técnicas

## Partición de equivalencia

Casos relacionados:

- CP-MF-001
- CP-MF-006
- CP-MF-007
- CP-MF-011

También se documentaron explícitamente las particiones del campo progreso en `semana3_tecnicas_caja_negra.md`.

## Valores límite

Casos relacionados:

- CP-MF-002
- CP-MF-003
- CP-MF-004
- CP-MF-005

Rango analizado:

- -1
- 0
- 1
- 99
- 100
- 101

## Tabla de decisión

Caso principal relacionado:

- CP-MF-008

Las reglas completas se encuentran en:

`semana3_decision_estados.md`

## Transición de estados

Casos relacionados:

- CP-MF-009
- CP-MF-012

También se documentaron dos transiciones inválidas en `semana3_decision_estados.md`.

## Casos de uso

Casos relacionados:

- CP-MF-001
- CP-MF-008
- CP-MF-010
- CP-MF-011
- CP-MF-012

---

# 5. Resultados de ejecución

De los 12 casos diseñados:

- 9 casos cuentan con resultado PASS registrado.
- 3 casos permanecen PENDIENTES de ejecución específica.
- 0 casos cuentan actualmente con resultado FAIL real.

Los casos pendientes son:

- CP-MF-002
- CP-MF-003
- CP-MF-004

Los resultados detallados se encuentran documentados en:

`semana3_resultados_ejecucion.md`

---

# 6. Defectos

Durante las ejecuciones reales documentadas de MiFormacionCTMA2 no se identificó hasta el momento un defecto reproducible.

Por esta razón:

- Los casos ejecutados correctamente se relacionan con "Ninguno".
- Los casos no ejecutados se relacionan con "No aplica".
- No se atribuye un defecto real a la aplicación sin evidencia de una discrepancia observable.

Para cumplir el ejercicio académico de gestión y reporte de defectos planteado en la guía de aprendizaje, el defecto reproducible derivado de una simulación se documentará separadamente y se identificará explícitamente como SIMULADO.

---

# 7. Conclusión

La matriz permite identificar el origen y resultado de cada caso de prueba y mantiene la relación entre las historias de usuario, criterios de aceptación, riesgos y técnicas utilizadas.

La trazabilidad facilita comprobar la cobertura alcanzada y evita registrar pruebas o defectos sin una referencia funcional identificable.