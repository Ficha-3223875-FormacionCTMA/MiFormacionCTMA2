# Evidencias y trazabilidad — Semana 4

## 1. Objetivo

Relacionar los casos de prueba ejecutados con sus resultados y evidencias,
manteniendo la trazabilidad del proceso de pruebas.

La trazabilidad permite conocer qué requisito o funcionalidad fue
validada mediante cada caso de prueba.

## 2. Cadena de trazabilidad

La relación utilizada es:

Historia de Usuario
→ Criterio de Aceptación
→ Caso de Prueba
→ Ejecución
→ Resultado
→ Evidencia

## 3. Matriz de trazabilidad

| Historia | Criterio | Caso de prueba | Tipo | Resultado | Evidencia |
|---|---|---|---|---|---|
| HU-01 | CA-01 | CP-01 | Humo | Pendiente de ejecución | Pendiente |
| HU-01 | CA-02 | CP-02 | Funcional | Pendiente de ejecución | Pendiente |
| HU-02 | CA-03 | CP-03 | Funcional | Pendiente de ejecución | Pendiente |
| HU-02 | CA-04 | CP-04 | Validación | Pendiente de ejecución | Pendiente |
| HU-03 | CA-05 | CP-05 | API | Pendiente de ejecución | Pendiente |
| HU-03 | CA-06 | CP-06 | Regresión | Pendiente de ejecución | Pendiente |

## 4. Tipos de evidencia

Se pueden utilizar diferentes tipos de evidencia según la prueba.

### Evidencia de aplicación

Captura de pantalla donde se observe el comportamiento de la aplicación.

### Evidencia del emulador

Captura donde se observe la funcionalidad ejecutándose en el dispositivo
virtual.

### Evidencia WEB/API

Captura de Postman mostrando la solicitud y la respuesta.

### Evidencia de DevTools

Captura de Network o Console cuando sea necesaria para demostrar el
comportamiento técnico.

### Evidencia de defectos

Captura relacionada con el comportamiento incorrecto encontrado.

## 5. Reglas para las evidencias

Cada evidencia debe:

- Corresponder a una prueba real.
- Ser legible.
- Mostrar únicamente la información necesaria.
- Tener relación con el caso de prueba.
- Permitir identificar el resultado observado.

No se deben utilizar capturas que no correspondan con la ejecución
registrada.

## 6. Registro de evidencias

| ID evidencia | Caso relacionado | Descripción | Ubicación | Estado |
|---|---|---|---|---|
| EV-01 | CP-01 | Inicio de aplicación | Pendiente | Pendiente |
| EV-02 | CP-02 | Creación de actividad | Pendiente | Pendiente |
| EV-03 | CP-03 | Validación de formulario | Pendiente | Pendiente |
| EV-04 | CP-04 | Consulta de información | Pendiente | Pendiente |
| EV-05 | CP-05 | Solicitud WEB/API | Pendiente | Pendiente |
| EV-06 | CP-06 | Prueba de regresión | Pendiente | Pendiente |

## 7. Gestión de resultados

Después de ejecutar una prueba se debe actualizar:

- Resultado esperado.
- Resultado obtenido.
- Estado.
- Evidencia.
- Observaciones.

Los estados utilizados son:

- Pendiente.
- Aprobado.
- Fallido.
- Bloqueado.

## 8. Defectos

Si una prueba falla, la evidencia debe conservarse junto con la
información necesaria para reproducir el defecto.

El defecto debe relacionarse con:

- Historia de usuario.
- Criterio de aceptación.
- Caso de prueba.
- Evidencia.

## 9. Criterio de cierre

La matriz se considera actualizada cuando los casos ejecutados tienen
registrados sus resultados y las evidencias correspondientes.

Las pruebas todavía no ejecutadas deben permanecer como:

**Pendiente de ejecución**

hasta obtener un resultado real.

## 10. Conclusión

La trazabilidad permite relacionar los requisitos del proyecto con las
pruebas realizadas y sus evidencias.

El registro debe mantenerse actualizado durante todo el proceso de
pruebas.