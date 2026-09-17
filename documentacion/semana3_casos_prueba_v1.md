# Casos de Prueba v1 - Semana 3

## Proyecto: MiFormacionCTMA2

Los siguientes casos de prueba fueron diseñados para validar las funcionalidades principales de creación y gestión de actividades formativas.

---

## CP-MF-001 - Crear actividad con datos válidos

**Referencia:** HU-MF-01 / CA-MF-01 / RSK-MF-01  
**Técnica:** Partición de equivalencia  
**Tipo:** Positiva  
**Prioridad:** Alta

**Precondición:**  
El usuario se encuentra en la pantalla "Crear actividad".

**Datos de prueba:**
- Título: Estudiar Kotlin
- Descripción: Repasar funciones y colecciones
- Fecha: fecha futura válida
- Progreso: 50
- Prioridad: ALTA

**Pasos:**
1. Ingresar los datos válidos.
2. Pulsar "Guardar actividad".

**Resultado esperado:**  
Se crea una única actividad y la aplicación regresa a la lista mostrando la nueva actividad.

---

## CP-MF-002 - Crear actividad con progreso mínimo

**Referencia:** HU-MF-01 / CA-MF-01 / RSK-MF-01  
**Técnica:** Análisis de valores límite  
**Tipo:** Positiva  
**Prioridad:** Alta

**Datos de prueba:**
- Progreso: 0

**Resultado esperado:**  
La aplicación acepta el valor 0 y permite guardar la actividad si los demás campos son válidos.

---

## CP-MF-003 - Crear actividad con progreso máximo

**Referencia:** HU-MF-01 / CA-MF-01 / RSK-MF-01  
**Técnica:** Análisis de valores límite  
**Tipo:** Positiva  
**Prioridad:** Alta

**Datos de prueba:**
- Progreso: 100

**Resultado esperado:**  
La aplicación acepta el valor 100 y permite guardar la actividad.

---

## CP-MF-004 - Progreso menor al mínimo

**Referencia:** HU-MF-01 / CA-MF-01 / RSK-MF-01  
**Técnica:** Análisis de valores límite  
**Tipo:** Negativa  
**Prioridad:** Alta

**Datos de prueba:**
- Progreso: -1

**Resultado esperado:**  
La aplicación rechaza el valor y muestra un mensaje indicando que el progreso debe estar entre 0 y 100.

---

## CP-MF-005 - Progreso mayor al máximo

**Referencia:** HU-MF-01 / CA-MF-01 / RSK-MF-01  
**Técnica:** Análisis de valores límite  
**Tipo:** Negativa  
**Prioridad:** Alta

**Datos de prueba:**
- Progreso: 101

**Resultado esperado:**  
La aplicación rechaza el valor y no permite guardar la actividad.

---

## CP-MF-006 - Título demasiado corto

**Referencia:** HU-MF-01 / CA-MF-01 / RSK-MF-01  
**Técnica:** Partición de equivalencia  
**Tipo:** Negativa  
**Prioridad:** Alta

**Datos de prueba:**
- Título: AB

**Resultado esperado:**  
La aplicación rechaza el título y muestra un mensaje indicando la longitud mínima permitida.

---

## CP-MF-007 - Fecha anterior a la actual

**Referencia:** HU-MF-01 / CA-MF-01 / RSK-MF-01  
**Técnica:** Partición de equivalencia  
**Tipo:** Negativa  
**Prioridad:** Alta

**Datos de prueba:**
- Fecha: fecha anterior al día actual

**Resultado esperado:**  
La aplicación impide guardar la actividad y muestra un mensaje de validación.

---

## CP-MF-008 - Doble pulsación en Guardar

**Referencia:** HU-MF-01 / CA-MF-01 / RSK-MF-01  
**Técnica:** Tabla de decisión  
**Tipo:** Negativa / control de duplicidad  
**Prioridad:** Alta

**Precondición:**  
Todos los datos del formulario son válidos.

**Pasos:**
1. Pulsar rápidamente dos veces el botón "Guardar actividad".

**Resultado esperado:**  
Solo se crea una actividad. No deben existir registros duplicados.

---

## CP-MF-009 - Conservar borrador al recrear la Activity

**Referencia:** HU-MF-01 / CA-MF-01  
**Técnica:** Caso de uso / transición de interfaz  
**Tipo:** Positiva  
**Prioridad:** Media

**Pasos:**
1. Abrir el formulario.
2. Escribir datos sin guardar.
3. Girar el dispositivo.
4. Volver a observar el formulario.

**Resultado esperado:**  
Los datos simples ingresados permanecen visibles después de la recreación de la Activity.

---

## CP-MF-010 - Abrir detalle de una actividad existente

**Referencia:** HU-MF-02  
**Técnica:** Caso de uso  
**Tipo:** Positiva  
**Prioridad:** Media

**Precondición:**  
Existe al menos una actividad en la lista.

**Pasos:**
1. Pulsar una tarjeta de actividad.

**Resultado esperado:**  
Se abre la pantalla de detalle correspondiente al identificador seleccionado.

---

## CP-MF-011 - Consultar actividad con ID inexistente

**Referencia:** HU-MF-02  
**Técnica:** Partición de equivalencia  
**Tipo:** Negativa  
**Prioridad:** Media

**Datos de prueba:**
- ID: 999

**Resultado esperado:**  
La aplicación muestra un estado controlado indicando que la actividad no existe y no se produce un cierre inesperado.

---

## CP-MF-012 - Regresar desde detalle a la lista

**Referencia:** HU-MF-02  
**Técnica:** Transición de estados / navegación  
**Tipo:** Positiva  
**Prioridad:** Media

**Precondición:**  
El usuario se encuentra en el detalle de una actividad.

**Pasos:**
1. Pulsar el botón "Volver".

**Resultado esperado:**  
La aplicación regresa a la lista anterior sin crear pantallas duplicadas ni perder la navegación esperada.