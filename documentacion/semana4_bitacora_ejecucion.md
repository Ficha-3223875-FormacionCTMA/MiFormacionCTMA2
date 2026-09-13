# Semana 4 - Bitácora Consolidada de Ejecución

## Proyecto: MiFormacionCTMA2

## 1. Objetivo

Consolidar las actividades de prueba realizadas durante la Semana 4, registrando los entornos utilizados, tipos de prueba, resultados obtenidos, evidencias y observaciones relevantes.

Durante la semana se realizaron pruebas sobre la aplicación Android real y sobre entornos académicos simulados WEB/API utilizados exclusivamente para cumplir los ejercicios técnicos de Chrome DevTools y Postman.

---

## 2. Entornos utilizados

### Aplicación real

- Proyecto: MiFormacionCTMA2
- Plataforma: Android
- Tecnología de interfaz: Jetpack Compose
- Tipo de pruebas: funcionales, validación, navegación, estado, smoke, exploratorias y regresión.

### Entorno WEB académico

Se utilizó una página WEB simulada únicamente para practicar Chrome DevTools.

Este entorno no representa la interfaz real de MiFormacionCTMA2.

### Entorno API académico

Se utilizó Postman junto con servicios públicos de prueba para practicar:

- métodos HTTP;
- códigos de respuesta;
- pruebas positivas y negativas;
- assertions;
- autenticación y autorización simuladas;
- ejecución mediante Collection Runner.

Este entorno no representa un backend real implementado en MiFormacionCTMA2.

---

## 3. Resumen de ejecuciones

| Actividad | Entorno | Resultado |
|---|---|---|
| Pruebas funcionales de actividades | Android | PASS |
| Validaciones del formulario | Android | PASS |
| Navegación LISTA-CREAR-DETALLE | Android | PASS |
| Conservación del estado | Android | PASS |
| Control de doble guardado | Android | PASS |
| DevTools caso positivo | WEB simulado | PASS |
| DevTools caso negativo | WEB simulado | PASS |
| Postman - 8 solicitudes | API/MOCK | PASS |
| Collection Runner | API/MOCK | 16/16 PASS |
| Smoke Testing | Android + API/MOCK | 5/5 PASS |
| Prueba exploratoria | Android | PASS |
| Regresión focalizada | Android | 6/6 PASS |

---

## 4. Registro de pruebas Android

Durante las pruebas de la aplicación se verificaron los siguientes comportamientos:

- creación de actividades válidas;
- rechazo de información inválida;
- validación del progreso;
- navegación entre lista, creación y detalle;
- consulta de una actividad existente;
- manejo controlado de identificadores inexistentes;
- prevención del doble guardado;
- conservación del borrador durante la recreación de la Activity.

Los comportamientos evaluados coincidieron con los resultados esperados en los casos ejecutados.

---

## 5. Registro Chrome DevTools

Se ejecutaron pruebas sobre el entorno WEB académico simulado.

### Caso positivo

Se realizó una operación válida y se inspeccionó la solicitud desde la pestaña Network.

Se verificaron:

- solicitud HTTP;
- Headers;
- Payload;
- respuesta obtenida.

Resultado:

**PASS**

### Caso negativo

Se utilizaron datos inválidos para comprobar las validaciones.

Se revisaron:

- Network;
- Console;
- Application/localStorage.

El entorno rechazó correctamente los datos inválidos.

Resultado:

**PASS**

### Evidencias

- `evidencias/semana4_pruebas/01_devtools_network_caso_positivo.png`
- `evidencias/semana4_pruebas/02_devtools_headers_post_201.png`
- `evidencias/semana4_pruebas/03_devtools_payload_caso_positivo.png`
- `evidencias/semana4_pruebas/04_devtools_caso_negativo_validacion.png`
- `evidencias/semana4_pruebas/05_devtools_console_caso_negativo.png`
- `evidencias/semana4_pruebas/06_devtools_application_localstorage.png`

---

## 6. Registro Postman

Se creó la colección:

**MiFormacionCTMA2 - Pruebas Semana 4**

Se configuró el environment:

**MiFormacionCTMA2 - Semana 4 Mock**

Variables utilizadas:

- `baseUrl`
- `token`
- `activityId`
- `userId`

Se ejecutaron ocho solicitudes:

1. Consultar actividad existente.
2. Crear actividad válida.
3. Consultar actividad inexistente.
4. Actualizar actividad.
5. Actualizar progreso parcialmente.
6. Eliminar actividad.
7. Solicitud sin autenticación válida.
8. Acceso no autorizado a recurso.

Se utilizaron los métodos:

- GET
- POST
- PUT
- PATCH
- DELETE

También se comprobaron códigos HTTP como:

- 200
- 201
- 401
- 403
- 404

Cada solicitud incluyó assertions para verificar el código HTTP y una condición adicional relacionada con la respuesta.

---

## 7. Resultado Collection Runner

La colección completa fue ejecutada mediante Collection Runner.

### Resultado

- Solicitudes: 8
- Tests: 16
- Passed: 16
- Failed: 0
- Errors: 0

**Resultado general: PASS**

La evidencia correspondiente se encuentra en:

`evidencias/semana4_pruebas/15_postman_runner_resumen.png`

Las pruebas de autenticación y autorización son simulaciones académicas y no representan un sistema de autenticación implementado en la aplicación Android.

---

## 8. Smoke Testing

Se seleccionaron cinco casos críticos para determinar si la versión podía continuar hacia pruebas posteriores.

### Resultado

- Casos ejecutados: 5
- PASS: 5
- FAIL: 0
- BLOCKED: 0

### Decisión

**CONTINUAR**

No se identificaron fallos críticos que impidieran continuar con las pruebas.

Documento relacionado:

`documentacion/semana4_smoke.md`

---

## 9. Prueba Exploratoria

Se definió un charter de prueba exploratoria con un timebox de:

**25 minutos**

El alcance incluyó:

- formulario;
- validaciones;
- valores límite;
- guardado;
- doble guardado;
- navegación;
- detalle;
- identificadores inexistentes;
- conservación del estado.

No se identificaron nuevos defectos reales reproducibles dentro del alcance registrado.

Documento relacionado:

`documentacion/semana4_exploratoria.md`

---

## 10. Regresión Focalizada

Se seleccionaron seis casos:

### Área modificada

- CP-MF-001
- CP-MF-005
- CP-MF-008

### Funciones relacionadas

- CP-MF-009
- CP-MF-010
- CP-MF-012

### Resultado

- Casos ejecutados: 6
- PASS: 6
- FAIL: 0
- BLOCKED: 0

No se observaron regresiones respecto a los resultados anteriores registrados.

Documento relacionado:

`documentacion/semana4_regresion.md`

---

## 11. Defectos

Durante las ejecuciones finales de Semana 4 no se identificaron nuevos defectos reales reproducibles en MiFormacionCTMA2.

El defecto documentado previamente como simulado se conserva únicamente como ejercicio académico y no debe interpretarse como un fallo real de la aplicación.

Los errores temporales presentados por servicios externos durante la preparación de las solicitudes HTTP tampoco se clasifican como defectos del producto.

---

## 12. Evidencias principales

### Android

Las evidencias funcionales se encuentran organizadas en:

`evidencias/semana4/`

### Pruebas de software Semana 4

Las evidencias correspondientes a DevTools y Postman se encuentran en:

`evidencias/semana4_pruebas/`

---

## 13. Resultado consolidado

Las pruebas ejecutadas permitieron comprobar los principales comportamientos funcionales de MiFormacionCTMA2 y desarrollar los ejercicios técnicos de WEB/API requeridos académicamente.

Los resultados consolidados no muestran fallos críticos que impidan continuar.

### Estado final

**APROBADO PARA CONTINUAR**

---

## 14. Conclusión

La ejecución de Semana 4 integró pruebas manuales, Chrome DevTools, Postman, Smoke Testing, pruebas exploratorias y regresión focalizada.

La aplicación Android mantuvo los comportamientos funcionales evaluados, mientras que los entornos WEB/API fueron utilizados de manera separada y claramente identificados como simulaciones académicas.

La información obtenida durante las ejecuciones permite mantener trazabilidad entre los casos de prueba, sus resultados y las evidencias generadas.