# Semana 3 - Reporte reproducible de defecto

## BUG-MFC-001

**Título:** Se acepta progreso de actividad superior a 100%.

**Ambiente:** Android Studio / emulador Android de pruebas / versión del proyecto MiFormacionCTMA2.

**Referencia:** HU-MFC-02 / CA-04 / CP-MFC-006 / RSK-MFC-01.

**Precondición:** disponer de una actividad de prueba que permita validar el campo de progreso.

**Datos:** progreso = 101.

**Pasos**
1. Crear o modificar una actividad de prueba.
2. Introducir progreso 101.
3. Ejecutar la validación.
4. Observar el resultado.

**Resultado esperado:** el valor debe ser rechazado por estar fuera del rango 0..100.

**Resultado real simulado:** el valor es aceptado.

**Severidad:** Alta, porque puede producir información de progreso inconsistente.

**Prioridad:** P1, porque afecta una regla básica y puede propagarse a la visualización y al resumen.

**Estado inicial:** Nuevo.

**Evidencia:** pendiente de captura real.

## Ciclo de vida

`NUEVO → TRIAGE/ANÁLISIS → ASIGNADO → EN CORRECCIÓN → RESUELTO → LISTO PARA REPRUEBA → CERRADO`

Si la corrección falla:

`LISTO PARA REPRUEBA → REABIERTO`
