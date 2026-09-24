# Matriz de cobertura de automatización — Semana 8

## 1. Objetivo

Relacionar los casos candidatos seleccionados para automatización con las pruebas realmente implementadas en el proyecto Mi Formación CTMA.

La cobertura se determina a partir del código de pruebas existente en `app/src/test`.

---

## 2. Matriz de cobertura

| ID | Caso | Archivo de prueba | Prueba relacionada | Cobertura |
|---|---|---|---|---|
| CP-01 | Crear actividad con datos válidos | FormularioActividadTest.kt / ActividadRepositoryLab2Test.kt | datosCompletosHabilitanGuardar / agregar_actividad_utiliza_el_dao | Cubierto |
| CP-02 | Crear actividad sin título | FormularioActividadTest.kt | tituloMenorA3EsInvalido | Parcial |
| CP-05 | Progreso fuera del límite permitido | FormularioActividadTest.kt / ReglasActividadTest.kt | progreso101EsInvalido / progreso_menor_que_0_es_invalido / progreso_mayor_que_100_es_invalido | Cubierto |
| CP-06 | Cambio de estado de una actividad | ReglasActividadTest.kt | transicion_de_pendiente_a_en_curso | Cubierto |
| CP-09 | Enviar datos inválidos mediante API | ActividadApiTest.kt | No existe prueba equivalente actualmente | Pendiente |
| CP-10 | Consultar datos mediante API | ActividadApiTest.kt | obtenerActividades_respuesta200 | Cubierto |
| CP-12 | Exploración visual de interfaz | Prueba manual | No aplica automatización | Manual |

---

## 3. Observaciones de cobertura

### CP-01

El proyecto valida datos completos del formulario y también comprueba mediante un repositorio con doble de prueba que una actividad válida sea enviada al DAO.

Estado: **Cubierto**.

### CP-02

Existe una prueba que rechaza un título inferior a tres caracteres.

Sin embargo, el caso original especifica explícitamente una actividad sin título. Por esta razón se considera cobertura parcial hasta agregar una prueba específica con título vacío.

Estado: **Parcial**.

### CP-05

Existen pruebas con valores fuera del rango permitido, incluyendo -1 y 101.

También se verifican los valores límite válidos 0 y 100.

Estado: **Cubierto**.

### CP-06

Existe una prueba automatizada que comprueba el cambio:

`PENDIENTE → EN CURSO`

cuando el progreso cambia de 0 a 1.

Estado: **Cubierto**.

### CP-09

La interfaz remota actual permite principalmente consultar actividades mediante GET.

Las pruebas existentes validan respuestas HTTP 200, 404 y 500, pero no existe actualmente una operación de creación que permita enviar una solicitud con datos inválidos.

Por esta razón este caso no debe declararse aprobado.

Estado: **Pendiente por disponibilidad del endpoint correspondiente**.

### CP-10

MockWebServer devuelve una respuesta HTTP 200 controlada y la prueba verifica el número de actividades, título y progreso recibidos.

Estado: **Cubierto**.

---

## 4. Resumen

Casos seleccionados para automatización: 6.

- Cubiertos: 4.
- Cobertura parcial: 1.
- Pendientes por funcionalidad/API disponible: 1.
- Caso visual conservado manual: CP-12.

La matriz evita marcar como automatizados casos que no cuentan con una comprobación real en el código.