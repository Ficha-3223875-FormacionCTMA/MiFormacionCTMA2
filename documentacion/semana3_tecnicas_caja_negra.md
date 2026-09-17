# Técnicas de caja negra - Semana 3

## Proyecto: MiFormacionCTMA2

## 1. Objetivo

Documentar la aplicación de técnicas de prueba de caja negra sobre las funcionalidades de creación, validación y navegación de actividades de MiFormacionCTMA2.

Las técnicas utilizadas son:

- Partición de equivalencia.
- Análisis de valores límite.
- Tabla de decisión.
- Transición de estados.
- Escenarios derivados de casos de uso.

---

# 2. Partición de equivalencia

## Campo progreso

La regla establece que el progreso válido de una actividad debe encontrarse entre 0 y 100.

A partir de esta regla se identifican tres particiones que no se solapan:

| Partición | Rango | Clasificación | Representante | Caso relacionado |
|---|---|---|---|---|
| PE-01 | Progreso < 0 | Inválida | -1 | CP-MF-004 |
| PE-02 | Progreso entre 0 y 100 | Válida | 50 | CP-MF-001 |
| PE-03 | Progreso > 100 | Inválida | 101 | CP-MF-005 |

### Resultado esperado

- Los valores pertenecientes a PE-01 deben ser rechazados.
- Los valores pertenecientes a PE-02 deben ser aceptados.
- Los valores pertenecientes a PE-03 deben ser rechazados.

De esta forma se cubren explícitamente tres clases de equivalencia: una válida y dos inválidas.

---

# 3. Análisis de valores límite

Para el campo progreso se establece el rango válido:

0 <= progreso <= 100

Se prueban los límites y sus valores inmediatamente vecinos.

| Valor | Relación con el límite | Resultado esperado |
|---|---|---|
| -1 | Vecino inferior de 0 | Rechazado |
| 0 | Límite inferior | Aceptado |
| 1 | Vecino superior del límite inferior | Aceptado |
| 99 | Vecino inferior del límite superior | Aceptado |
| 100 | Límite superior | Aceptado |
| 101 | Vecino superior de 100 | Rechazado |

### Casos relacionados

- CP-MF-002: progreso = 0.
- CP-MF-003: progreso = 100.
- CP-MF-004: progreso = -1.
- CP-MF-005: progreso = 101.

Los valores 1 y 99 se documentan como datos complementarios de análisis de frontera para demostrar los vecinos internos de ambos límites.

---

# 4. Partición de equivalencia del título

El título permite complementar las pruebas de validación del formulario.

| Partición | Condición | Clasificación | Ejemplo |
|---|---|---|---|
| PE-T01 | Menos de 3 caracteres | Inválida | AB |
| PE-T02 | Entre 3 y 80 caracteres | Válida | Estudiar Kotlin |
| PE-T03 | Más de 80 caracteres | Inválida | Texto superior a 80 caracteres |

El caso CP-MF-006 utiliza la partición inválida inferior para verificar que la aplicación impida guardar un título demasiado corto.

---

# 5. Tabla de decisión

La decisión de guardar una actividad depende principalmente de:

- Validez del título.
- Validez de la fecha.
- Validez del progreso.
- Existencia o no de un guardado en curso.

Las reglas detalladas se encuentran documentadas en:

`documentacion/semana3_decision_estados.md`

La tabla permite comprobar, entre otras situaciones:

- Título inválido -> no guardar.
- Fecha inválida -> no guardar.
- Progreso inválido -> no guardar.
- Guardado ya iniciado -> impedir duplicación.
- Todos los datos válidos -> crear una única actividad.

Casos relacionados:

- CP-MF-001.
- CP-MF-004.
- CP-MF-005.
- CP-MF-006.
- CP-MF-007.
- CP-MF-008.

---

# 6. Transición de estados

Para las pruebas de navegación se consideran los siguientes estados observables:

- LISTA
- CREAR
- DETALLE

## Secuencia válida principal

LISTA -> CREAR -> LISTA

El usuario abre el formulario, registra correctamente una actividad y vuelve a la lista.

## Secuencia válida de consulta

LISTA -> DETALLE -> LISTA

El usuario abre una actividad existente y posteriormente regresa a la lista.

## Secuencia alterna

LISTA -> CREAR -> LISTA

El usuario entra al formulario y regresa sin completar el proceso de creación.

## Transiciones inválidas

### Transición inválida 1

LISTA -> Guardar directamente -> LISTA

No debe ser posible ejecutar la acción Guardar desde la pantalla de lista porque esta acción pertenece al formulario de creación.

### Transición inválida 2

DETALLE -> Guardar nueva actividad -> DETALLE

La pantalla de detalle no debe permitir ejecutar directamente la acción de guardar una nueva actividad.

El modelo completo se encuentra en:

`documentacion/semana3_decision_estados.md`

---

# 7. Escenarios derivados de caso de uso

## Flujo principal

### CU-MF-01 - Crear actividad correctamente

1. El aprendiz abre la lista de actividades.
2. Selecciona la opción para crear una actividad.
3. La aplicación muestra el formulario.
4. El aprendiz ingresa datos válidos.
5. Selecciona Guardar.
6. La aplicación valida la información.
7. Se crea una sola actividad.
8. El usuario regresa a la lista.

Caso relacionado:

CP-MF-001.

---

## Flujo alterno 1

### Datos inválidos en el formulario

1. El aprendiz abre el formulario.
2. Ingresa información que incumple alguna regla.
3. Selecciona Guardar.
4. La aplicación detecta la información inválida.
5. Se muestra el mensaje de validación correspondiente.
6. La actividad no se crea.

Casos relacionados:

- CP-MF-004.
- CP-MF-005.
- CP-MF-006.
- CP-MF-007.

---

## Flujo alterno 2

### Intento de guardado repetido

1. El aprendiz completa correctamente el formulario.
2. Pulsa Guardar.
3. Intenta ejecutar nuevamente la acción mientras el guardado se encuentra en curso.
4. La aplicación controla la acción repetida.
5. Se registra una única actividad.

Caso relacionado:

CP-MF-008.

---

## Flujo de consulta

### CU-MF-02 - Consultar detalle

1. El aprendiz visualiza la lista.
2. Selecciona una actividad existente.
3. La aplicación abre la pantalla de detalle.
4. Se muestra la información de la actividad.
5. El aprendiz regresa a la lista.

Casos relacionados:

- CP-MF-010.
- CP-MF-012.

---

## Flujo de excepción

### Actividad inexistente

1. Se solicita el detalle de un identificador inexistente.
2. La aplicación intenta localizar la actividad.
3. No encuentra el identificador solicitado.
4. Se muestra un mensaje controlado.
5. La aplicación no se cierra inesperadamente.

Caso relacionado:

CP-MF-011.

---

# 8. Cobertura obtenida

Con las técnicas documentadas se obtiene cobertura sobre:

- Datos válidos.
- Datos inválidos.
- Límites inferiores y superiores.
- Valores vecinos de los límites.
- Reglas combinadas de validación.
- Prevención de duplicación.
- Navegación entre pantallas.
- Transiciones válidas e inválidas.
- Flujo principal.
- Flujos alternos.
- Flujo de excepción.

Cada técnica se relaciona con casos identificados mediante códigos CP-MF, permitiendo mantener la trazabilidad con los artefactos de pruebas de MiFormacionCTMA2.