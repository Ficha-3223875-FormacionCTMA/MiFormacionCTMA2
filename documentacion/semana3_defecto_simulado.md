# Registro y reporte de defecto simulado - Semana 3

## Proyecto: MiFormacionCTMA2

> **IMPORTANTE:** El siguiente defecto es una simulación académica realizada para aplicar el proceso de gestión y documentación de defectos solicitado en la guía de aprendizaje. No corresponde a un fallo real observado durante las pruebas ejecutadas sobre MiFormacionCTMA2.

---

# 1. Registro de defectos

| ID | Caso origen | Título | Severidad | Prioridad | Estado | Tipo |
|---|---|---|---|---|---|---|
| BUG-SIM-MF-001 | CP-MF-008 | Se crea una actividad duplicada al presionar Guardar dos veces | Alta | Alta | Simulado | Defecto simulado |

---

# 2. Reporte reproducible

## Identificador

**BUG-SIM-MF-001**

## Título

Se crea una actividad duplicada al presionar el botón Guardar dos veces consecutivamente.

## Clasificación

**DEFECTO SIMULADO**

Este escenario se utiliza exclusivamente con fines académicos para practicar la documentación y gestión de defectos.

## Historia relacionada

**HU-MF-01 - Crear actividad**

Como aprendiz, quiero crear una actividad formativa para organizar mis compromisos académicos desde la aplicación.

## Criterio relacionado

**CA-MF-01**

Al completar correctamente los campos obligatorios y seleccionar Guardar, la aplicación debe crear una sola actividad y mostrarla en la lista.

## Riesgo relacionado

**RSK-MF-01 - Creación incorrecta o duplicada de actividades**

## Caso de prueba relacionado

**CP-MF-008 - Doble pulsación en Guardar**

---

# 3. Ambiente de prueba simulado

- Aplicación: MiFormacionCTMA2
- Plataforma: Android
- Ambiente: Emulador Android
- Datos utilizados: sintéticos
- Tipo de ejecución: simulada

---

# 4. Precondiciones

1. La aplicación se encuentra abierta.
2. El usuario está ubicado en el formulario Crear actividad.
3. Todos los campos contienen datos válidos.
4. No existe previamente una actividad idéntica en la lista.

---

# 5. Datos de prueba

- Título: Estudiar Kotlin
- Descripción: Repasar funciones y colecciones
- Fecha: fecha válida
- Progreso: 50
- Prioridad: ALTA

---

# 6. Pasos para reproducir la simulación

1. Abrir MiFormacionCTMA2.
2. Ingresar a la opción Crear actividad.
3. Completar todos los campos con datos válidos.
4. Presionar rápidamente dos veces el botón Guardar.
5. Regresar a la lista de actividades.
6. Observar la cantidad de actividades creadas.

---

# 7. Resultado esperado

La aplicación debe procesar una única solicitud de guardado y crear solamente una actividad.

---

# 8. Resultado simulado

Para efectos del ejercicio académico, se supone que la aplicación procesa ambas pulsaciones y crea dos actividades idénticas.

Este resultado es hipotético y no corresponde al comportamiento real observado durante las pruebas de MiFormacionCTMA2.

---

# 9. Decisión

**FAIL - SIMULADO**

Existe una discrepancia entre el resultado esperado y el resultado definido para la simulación.

---

# 10. Severidad

**Alta**

La duplicación afecta la integridad de la información y podría provocar que el aprendiz tenga registros repetidos de una misma actividad.

---

# 11. Prioridad

**Alta**

La creación de actividades es una funcionalidad principal de MiFormacionCTMA2, por lo que una duplicación debería corregirse antes de considerar estable esta funcionalidad.

---

# 12. Estado

**Simulado / No aplica a la versión real**

No se requiere corrección sobre la versión actualmente probada debido a que este comportamiento no fue observado realmente.

---

# 13. Evidencia

No se adjunta captura como evidencia de un fallo real.

La prueba real relacionada con CP-MF-008 produjo resultado PASS y cuenta con la evidencia:

`evidencias/semana4/09-doble-guardado-controlado.png`

Esta evidencia demuestra que la versión probada controla correctamente el doble guardado.

---

# 14. Trazabilidad

HU-MF-01
→ CA-MF-01
→ RSK-MF-01
→ CP-MF-008
→ BUG-SIM-MF-001

---

# 15. Conclusión

BUG-SIM-MF-001 permite aplicar académicamente el proceso de registro, clasificación y documentación de un defecto reproducible sin atribuir a MiFormacionCTMA2 una falla que no fue observada durante la ejecución real.

El escenario permite diferenciar claramente el resultado real de CP-MF-008, que fue PASS, del resultado hipotético utilizado para practicar la gestión de defectos.