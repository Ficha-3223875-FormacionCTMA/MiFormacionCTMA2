# Ejecución automatizada y resultados — Semana 8

## 1. Objetivo

Registrar los resultados reales de las pruebas automatizadas relacionadas con los candidatos seleccionados durante la Semana 8.

---

## 2. Ambiente de ejecución

Proyecto: Mi Formación CTMA

Tecnologías:

- Kotlin.
- JUnit.
- Kotlin Coroutines Test.
- Retrofit.
- MockWebServer.
- Android Studio.
- Gradle.

Rama de trabajo utilizada: `sofia`.

Comando utilizado para las pruebas unitarias:

`./gradlew.bat :app:testDevDebugUnitTest`

Resultado obtenido:

`BUILD SUCCESSFUL`

---

## 3. Resultados por caso

| ID | Prueba automatizada | Resultado | Observación |
|---|---|---|---|
| CP-01 | datosCompletosHabilitanGuardar / agregar_actividad_utiliza_el_dao | APROBADO | Los datos válidos son aceptados y el repositorio utiliza el DAO |
| CP-02 | tituloMenorA3EsInvalido | PARCIAL | Se comprueba título demasiado corto, pero falta prueba específica de título vacío |
| CP-05 | progreso101EsInvalido y pruebas -1/101 | APROBADO | Los valores fuera de 0..100 son rechazados |
| CP-06 | transicion_de_pendiente_a_en_curso | APROBADO | Se comprueba el cambio de PENDIENTE a EN CURSO |
| CP-09 | Enviar datos inválidos mediante API | NO EJECUTADO | No existe actualmente endpoint de creación adecuado para ejecutar este escenario |
| CP-10 | obtenerActividades_respuesta200 | APROBADO | MockWebServer devuelve datos controlados y estos son interpretados correctamente |
| CP-12 | Exploración visual | MANUAL | Se mantiene fuera de la automatización |

---

## 4. Pruebas adicionales existentes

Aunque no corresponden directamente a todos los candidatos seleccionados, la suite también comprueba:

- Respuesta HTTP 404.
- Respuesta HTTP 500.
- Búsqueda de una actividad inexistente.
- Propagación de errores del DAO.
- Valores límite válidos 0 y 100.
- Estado COMPLETADA para progreso 100.

Estas pruebas aportan valor adicional a la regresión.

---

## 5. Incidencias

Durante la ejecución de la suite unitaria no se registraron fallos de pruebas.

El resultado final de Gradle fue:

`BUILD SUCCESSFUL`

CP-09 no se registra como defecto del producto. Se registra como una limitación de cobertura debido a que la interfaz API disponible en el proyecto no contiene actualmente una operación de creación compatible con el escenario planteado.

CP-02 requiere una prueba adicional para representar exactamente el caso de título vacío.

---

## 6. Conclusión

La ejecución automatizada confirmó correctamente la mayoría de los candidatos priorizados para Semana 8.

Los resultados documentados corresponden a pruebas reales existentes y ejecutadas en el proyecto; no se utilizaron resultados simulados.