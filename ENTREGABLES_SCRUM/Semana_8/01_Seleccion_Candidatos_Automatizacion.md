# Selección de candidatos para automatización — Semana 8

## 1. Objetivo

Seleccionar casos de prueba de las Semanas 3 y 4 que sean adecuados para automatización, teniendo en cuenta su repetibilidad, valor para la regresión, facilidad de ejecución y posibilidad de obtener resultados observables.

La selección busca priorizar pruebas que puedan ejecutarse de manera rápida, repetible y mantenible.

---

## 2. Criterios utilizados para seleccionar candidatos

Se consideraron como candidatos para automatización los casos que cumplen una o varias de las siguientes características:

- Son repetitivos.
- Pueden ejecutarse de forma determinista.
- Tienen entradas y resultados esperados claramente definidos.
- Son importantes para la funcionalidad del sistema.
- Permiten detectar regresiones.
- Pueden ejecutarse sin depender de información sensible.
- Tienen resultados que pueden comprobarse mediante aserciones.

Los casos principalmente visuales o exploratorios se mantienen como pruebas manuales cuando la automatización no aporta suficiente valor.

---

## 3. Casos de prueba recuperados de las Semanas 3 y 4

| ID | Semana | Caso de prueba | Tipo | Resultado esperado | Candidato a automatización |
|---|---|---|---|---|---|
| CP-01 | 3 | Crear actividad con datos válidos | Positivo | La actividad se crea correctamente | Sí |
| CP-02 | 3 | Crear actividad sin título | Negativo | El sistema muestra error de validación | Sí |
| CP-03 | 3 | Crear actividad con descripción válida | Positivo | La información se acepta correctamente | Sí |
| CP-04 | 3 | Registrar progreso dentro del límite permitido | Límite | El progreso es aceptado | Sí |
| CP-05 | 3 | Registrar progreso fuera del límite permitido | Negativo/Límite | El sistema rechaza el valor | Sí |
| CP-06 | 3 | Cambio de estado de una actividad | Transición de estado | El estado cambia correctamente | Sí |
| CP-07 | 3 | Consultar una actividad existente | Positivo | Se muestran sus datos | Sí |
| CP-08 | 4 | Crear actividad desde el formulario | Positivo | La actividad queda registrada | Sí |
| CP-09 | 4 | Enviar datos inválidos mediante API | Negativo API | La API rechaza la solicitud | Sí |
| CP-10 | 4 | Consultar datos mediante API | API | La respuesta contiene los datos esperados | Sí |
| CP-11 | 4 | Validar navegación entre pantallas | Funcional | La navegación funciona correctamente | No |
| CP-12 | 4 | Exploración visual de la interfaz | Exploratoria | Se identifican problemas visuales o de interacción | No |

---

## 4. Candidatos seleccionados

A partir de los casos anteriores se priorizan los siguientes:

### CP-01 — Crear actividad con datos válidos

**Motivo:** es una operación repetitiva y tiene un resultado claramente verificable.

**Nivel propuesto:** prueba unitaria/integración según la implementación.

**Riesgo relacionado:** que una modificación del código impida registrar correctamente una actividad.

---

### CP-02 — Crear actividad sin título

**Motivo:** permite comprobar una validación negativa de manera repetible.

**Nivel propuesto:** prueba unitaria.

**Riesgo relacionado:** aceptar información incompleta.

---

### CP-05 — Progreso fuera del límite permitido

**Motivo:** corresponde a una prueba de límites y permite verificar que el sistema rechaza valores inválidos.

**Nivel propuesto:** prueba unitaria.

**Riesgo relacionado:** almacenar valores de progreso incorrectos.

---

### CP-06 — Cambio de estado de una actividad

**Motivo:** es una transición de estado que puede verificarse mediante entradas y resultado esperado.

**Nivel propuesto:** prueba unitaria.

**Riesgo relacionado:** permitir transiciones de estado incorrectas.

---

### CP-09 — Enviar datos inválidos mediante API

**Motivo:** es un caso negativo de API y puede ejecutarse repetidamente con datos controlados.

**Nivel propuesto:** prueba de API/HTTP.

**Riesgo relacionado:** que el servicio acepte solicitudes inválidas.

---

### CP-10 — Consultar datos mediante API

**Motivo:** permite verificar automáticamente código de respuesta y contenido de la respuesta.

**Nivel propuesto:** prueba de API/HTTP.

**Riesgo relacionado:** que el servicio entregue información incorrecta o una respuesta inesperada.

---

## 5. Requisito: transición de estado

El caso seleccionado para representar una transición de estado es:

**CP-06 — Cambio de estado de una actividad**

La prueba debe comprobar que, al producirse una acción válida, la actividad pasa del estado inicial al estado esperado.

Este caso es adecuado para automatización porque la entrada, la transición esperada y el resultado pueden definirse de forma precisa.

---

## 6. Requisito: caso negativo de API

El caso seleccionado es:

**CP-09 — Enviar datos inválidos mediante API**

La prueba debe enviar datos inválidos y comprobar que el servicio no los acepta.

Entre las comprobaciones esperadas se encuentran:

- Código HTTP esperado para una solicitud inválida.
- Mensaje o estructura de error.
- Ausencia de creación de información incorrecta.

No se utilizarán credenciales ni secretos reales en las pruebas.

---

## 7. Caso que se mantiene manual

Se mantiene como prueba manual:

**CP-12 — Exploración visual de la interfaz**

### Justificación

Este caso se mantiene manual porque requiere observar aspectos visuales y de interacción que no se consideran el objetivo principal de las pruebas unitarias o de API.

La revisión manual permite detectar problemas relacionados con:

- Presentación de elementos.
- Distribución visual.
- Facilidad de interacción.
- Comportamiento percibido por el usuario.

Por esta razón, automatizar completamente este caso no aporta el mismo valor que automatizar las validaciones deterministas.

---

## 8. Relación con trazabilidad, riesgos y defectos

| ID | Historia/Criterio relacionado | Riesgo | Posible defecto detectado | Automatización |
|---|---|---|---|---|
| CP-01 | Registro correcto de actividad | No registrar la actividad | La actividad no aparece después de guardar | Sí |
| CP-02 | Validación de campos obligatorios | Aceptar información incompleta | Se permite guardar sin título | Sí |
| CP-05 | Validación de límites | Aceptar valores fuera de rango | Se almacena un progreso inválido | Sí |
| CP-06 | Gestión de estados | Cambio de estado incorrecto | La actividad pasa a un estado no permitido | Sí |
| CP-09 | Validación de API | Aceptar solicitudes inválidas | La API responde como si la solicitud fuera válida | Sí |
| CP-10 | Consulta de información | Respuesta incorrecta | La API devuelve datos inconsistentes | Sí |
| CP-12 | Interfaz y experiencia de usuario | Problemas visuales | Elementos mal ubicados o difíciles de utilizar | Manual |

---

## 9. Priorización para la Semana 8

Los casos seleccionados servirán como base para los laboratorios de automatización de la Semana 8.

La prioridad será:

1. Automatizar la transición de estado.
2. Automatizar un caso de límites/equivalencia.
3. Automatizar validaciones unitarias.
4. Automatizar casos de API.
5. Mantener las pruebas visuales/exploratorias que requieran intervención manual.

La selección se realizará evitando automatizar casos únicamente por cantidad y priorizando aquellos que aporten valor a la regresión y puedan ejecutarse de manera repetible.

---

## 10. Observación

Esta selección representa la planificación de los casos candidatos. La implementación de las pruebas automatizadas se realizará en los laboratorios correspondientes de la Semana 8.

No se consideran resultados de ejecución hasta que las pruebas sean implementadas y ejecutadas.