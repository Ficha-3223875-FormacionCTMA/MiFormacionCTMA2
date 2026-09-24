# Plan de Ejecución de Pruebas WEB/API — Semana 4

## 1. Objetivo

Organizar la ejecución de las pruebas definidas en las semanas anteriores,
relacionando cada caso de prueba con su funcionalidad, nivel de prueba,
resultado y evidencia.

El objetivo es obtener resultados verificables y mantener la trazabilidad
durante la ejecución.

## 2. Elementos a verificar

Durante la ejecución se deben revisar:

- Funcionalidades principales de la aplicación.
- Formularios y validaciones.
- Creación y consulta de actividades.
- Persistencia de la información cuando corresponda.
- Comunicación con servicios WEB/API cuando estén disponibles.
- Manejo de respuestas y errores.
- Comportamiento esperado frente a datos válidos e inválidos.

## 3. Secuencia de ejecución

### Paso 1 — Preparación

Antes de ejecutar las pruebas:

- Verificar que la aplicación pueda iniciarse.
- Verificar que el entorno de prueba esté disponible.
- Preparar los datos sintéticos necesarios.
- Identificar los casos de prueba que serán ejecutados.
- Tener disponibles las herramientas necesarias.

### Paso 2 — Pruebas de humo

Ejecutar primero las pruebas básicas para verificar que la aplicación
funciona y que las funcionalidades principales están disponibles.

### Paso 3 — Pruebas funcionales

Ejecutar los casos de prueba relacionados con las historias de usuario y
criterios de aceptación definidos anteriormente.

### Paso 4 — Pruebas WEB/API

Cuando exista un servicio WEB/API disponible, verificar:

- Método HTTP utilizado.
- URL o endpoint.
- Parámetros enviados.
- Código de respuesta.
- Información recibida.
- Manejo de errores.

### Paso 5 — Regresión

Después de realizar cambios, repetir los casos relevantes para verificar
que las funcionalidades existentes continúen funcionando.

## 4. Registro de resultados

Cada ejecución debe registrar el resultado real obtenido.

| ID | Elemento probado | Nivel | Resultado esperado | Resultado obtenido | Estado | Evidencia |
|---|---|---|---|---|---|---|
| CP-01 | Inicio de aplicación | Humo | La aplicación inicia correctamente | Registrar al ejecutar | Pendiente | Captura |
| CP-02 | Crear actividad | Funcional | La actividad se registra correctamente | Registrar al ejecutar | Pendiente | Captura |
| CP-03 | Validación de formulario | Funcional | Se muestran las validaciones correspondientes | Registrar al ejecutar | Pendiente | Captura |
| CP-04 | Consulta de información | Funcional | Se muestra la información registrada | Registrar al ejecutar | Pendiente | Captura |
| CP-05 | Servicio WEB/API | API | El servicio responde según lo esperado | Registrar al ejecutar | Pendiente | Postman |
| CP-06 | Regresión | Regresión | Las funcionalidades existentes continúan funcionando | Registrar al ejecutar | Pendiente | Captura |

## 5. Evidencias

Las evidencias deben permitir comprobar el resultado de cada prueba.

Se pueden utilizar:

- Capturas de pantalla de la aplicación.
- Capturas del emulador.
- Capturas de Postman.
- Capturas de DevTools.
- Registros de consola cuando sean necesarios.
- Reportes generados por las herramientas de prueba.

Cada evidencia debe relacionarse con el ID del caso de prueba correspondiente.

## 6. Criterios para registrar un resultado

Un caso de prueba debe marcarse como:

### APROBADO

Cuando el comportamiento observado coincide con el resultado esperado.

### FALLIDO

Cuando el comportamiento observado no coincide con el resultado esperado.

### BLOQUEADO

Cuando no es posible ejecutar la prueba debido a una condición externa,
problema del entorno, servicio no disponible u otra dependencia.

### PENDIENTE

Cuando la prueba todavía no ha sido ejecutada.

## 7. Trazabilidad

La ejecución debe mantener la relación:

Historia de Usuario
→ Criterio de Aceptación
→ Caso de Prueba
→ Ejecución
→ Resultado
→ Evidencia

Esta relación permite identificar qué funcionalidad fue validada y con qué
evidencia.

## 8. Observaciones

Los resultados deben registrarse después de ejecutar realmente cada prueba.

No se deben registrar como aprobados casos que todavía no hayan sido
ejecutados ni inventar evidencias.

## 9. Cierre

El plan de ejecución se considera completado cuando los casos seleccionados
hayan sido ejecutados, los resultados registrados y las evidencias asociadas
a cada prueba.