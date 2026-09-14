# Semana 3 - Ejecución simulada y gestión de defectos

La guía de Semana 3 permite usar simulación cuando todavía no existe un prototipo ejecutable. Los siguientes resultados son **simulados** y no deben presentarse como ejecución real.

| ID | Caso | Resultado simulado | Decisión | Defecto |
|---|---|---|---|---|
| SIM-MFC-01 | CP-MFC-001 | Se muestran las actividades | PASS | — |
| SIM-MFC-02 | CP-MFC-003 | Una actividad con 100% aparece COMPLETADA | PASS | — |
| SIM-MFC-03 | CP-MFC-006 | Se acepta progreso 101 | FAIL | BUG-MFC-001 |
| SIM-MFC-04 | CP-MFC-008 | Actividad con días -1 aparece EN CURSO | FAIL | BUG-MFC-002 |
| SIM-MFC-05 | CP-MFC-002 | El promedio mostrado no coincide con el cálculo esperado | FAIL | BUG-MFC-003 |

## Registro mínimo

| ID | Caso origen | Título | Severidad | Prioridad | Estado | Referencia |
|---|---|---|---|---|---|---|
| BUG-MFC-001 | CP-MFC-006 | Se acepta progreso fuera del rango máximo | Alta | P1 | Nuevo | HU-MFC-02/CA-04 |
| BUG-MFC-002 | CP-MFC-008 | Estado de actividad vencida incorrecto | Alta | P1 | Nuevo | HU-MFC-02/CA-05 |
| BUG-MFC-003 | CP-MFC-002 | Promedio de progreso incorrecto | Media | P2 | Nuevo | HU-MFC-01/CA-02 |

**Nota:** estos defectos se incluyen como parte de la simulación académica solicitada por la guía. Deben reemplazarse o confirmarse mediante ejecución real.
