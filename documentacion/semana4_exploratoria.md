# Semana 4 - Prueba Exploratoria

## Proyecto: MiFormacionCTMA2

## 1. Objetivo

Realizar una sesión de prueba exploratoria sobre el flujo principal de creación, validación, consulta y navegación de actividades en MiFormacionCTMA2, buscando comportamientos inesperados que puedan no estar cubiertos completamente por los casos de prueba previamente diseñados.

---

## 2. Charter de prueba exploratoria

### Misión

Explorar el flujo de gestión de actividades para identificar posibles errores relacionados con:

- ingreso de datos;
- validaciones;
- creación de actividades;
- navegación entre pantallas;
- consulta de detalles;
- conservación del estado;
- comportamiento ante acciones repetidas.

### Alcance

La exploración se concentra en:

1. Pantalla de lista de actividades.
2. Formulario de creación.
3. Validaciones del formulario.
4. Guardado de actividades.
5. Pantalla de detalle.
6. Navegación entre LISTA, CREAR y DETALLE.

### Fuera de alcance

La API/MOCK utilizada durante el laboratorio de Postman no forma parte de esta sesión exploratoria de la aplicación Android real.

---

## 3. Timebox

Duración definida:

**25 minutos**

La sesión se organiza de la siguiente manera:

| Tiempo | Actividad |
|---|---|
| 0-5 min | Exploración del formulario y campos |
| 5-10 min | Validaciones y valores límite |
| 10-15 min | Guardado y acciones repetidas |
| 15-20 min | Navegación y detalle |
| 20-25 min | Estado, observaciones y registro de resultados |

---

## 4. Datos de prueba

Se utilizan exclusivamente datos sintéticos.

### Actividad válida

- Título: Estudiar Kotlin
- Descripción: Repasar funciones y colecciones
- Progreso: 50
- Prioridad: Alta
- Fecha: fecha válida

### Datos inválidos

- Título: AB
- Progreso: 101
- Fecha anterior a la fecha actual

### Valores límite

- Progreso: 0
- Progreso: 100
- Título con 3 caracteres
- Título cercano al máximo permitido

---

## 5. Riesgos observados durante la exploración

Se presta especial atención a los siguientes riesgos:

- creación duplicada por pulsaciones repetidas;
- aceptación de valores fuera de rango;
- pérdida del borrador al recrear la Activity;
- navegación incorrecta;
- apertura de una actividad inexistente;
- pérdida inesperada de información;
- cierre inesperado de la aplicación.

---

## 6. Preguntas orientadoras

Durante la sesión se plantean las siguientes preguntas:

1. ¿Qué ocurre si el usuario intenta guardar información incompleta?
2. ¿Qué ocurre con valores exactamente en los límites permitidos?
3. ¿Se puede crear accidentalmente una actividad dos veces?
4. ¿Se conserva el formulario si se recrea la Activity?
5. ¿La navegación siempre lleva a la pantalla esperada?
6. ¿Qué ocurre al solicitar una actividad inexistente?
7. ¿La aplicación muestra mensajes comprensibles ante datos inválidos?
8. ¿Se produce algún cierre inesperado durante estos flujos?

---

## 7. Notas de exploración

### Formulario

Se revisaron los campos requeridos y las validaciones principales del formulario.

La aplicación impidió guardar datos inválidos cuando el título no cumplía el mínimo requerido o el progreso estaba fuera del rango permitido.

**Resultado: PASS**

---

### Valores límite

Se consideraron los límites definidos para el progreso entre 0 y 100.

Los valores fuera del rango son tratados como inválidos según las reglas definidas para el formulario.

**Resultado: PASS**

---

### Guardado de actividad

Se comprobó el flujo de creación utilizando información válida.

La actividad se agregó correctamente y fue visible posteriormente en la lista.

**Resultado: PASS**

---

### Doble guardado

Se revisó el comportamiento al intentar ejecutar repetidamente la acción de guardar.

El control `guardando` evita que una misma operación produzca múltiples actividades por una pulsación repetida.

**Resultado: PASS**

---

### Navegación

Se exploró la transición entre:

LISTA → CREAR → LISTA → DETALLE → LISTA

Las transiciones evaluadas permitieron regresar correctamente a las pantallas esperadas.

**Resultado: PASS**

---

### Consulta de detalle

Se verificó la apertura de una actividad existente.

La pantalla mostró correctamente el detalle correspondiente.

**Resultado: PASS**

---

### Identificador inexistente

Se revisó el comportamiento ante un identificador de actividad inexistente.

La aplicación mostró un estado controlado indicando que la actividad solicitada no existe y no presentó cierre inesperado.

**Resultado: PASS**

---

### Conservación del borrador

Se revisó la conservación de información del formulario durante la recreación de la Activity.

El uso de `rememberSaveable` permitió conservar el borrador durante esta recreación.

**Resultado: PASS**

---

## 8. Hallazgos

Durante la sesión exploratoria no se identificó un defecto real reproducible adicional.

Los comportamientos críticos revisados coincidieron con las reglas y resultados previamente definidos.

No se registran nuevos defectos reales derivados de esta sesión.

---

## 9. Evidencias relacionadas

Para respaldar los comportamientos explorados se reutilizan las evidencias existentes:

- `evidencias/semana4/02-detalle-actividad.png`
- `evidencias/semana4/04-validaciones-formulario.png`
- `evidencias/semana4/06-actividad-guardada.png`
- `evidencias/semana4/07-remember-saveable-rotacion.png`
- `evidencias/semana4/08-id-inexistente.png`
- `evidencias/semana4/09-doble-guardado-controlado.png`

---

## 10. Acciones posteriores

Debido a que no se encontraron defectos nuevos reproducibles:

- no se crea un nuevo reporte de defecto;
- se conservan los casos existentes;
- los flujos críticos continúan hacia regresión;
- se mantienen identificados los riesgos relacionados con validación, navegación y estado.

---

## 11. Resultado de la sesión

**Resultado general: PASS**

Duración definida: **25 minutos**

Defectos nuevos reproducibles: **0**

La aplicación mostró un comportamiento estable en los flujos incluidos dentro del alcance de la sesión exploratoria.

---

## 12. Conclusión

La prueba exploratoria permitió complementar los casos estructurados mediante una revisión orientada a riesgos y comportamientos del usuario.

La sesión cubrió creación, validaciones, navegación, detalle, acciones repetidas y conservación del estado. No se identificaron nuevos defectos reproducibles y el producto puede continuar con la prueba de regresión de la Semana 4.