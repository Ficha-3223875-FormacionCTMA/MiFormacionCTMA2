# Trazabilidad de automatización — Semana 8

## 1. Objetivo

Mantener la relación entre caso de prueba, riesgo, prueba automatizada, resultado y posible defecto.

---

## 2. Matriz de trazabilidad

| ID | Riesgo | Prueba automatizada | Resultado | Defecto |
|---|---|---|---|---|
| CP-01 | No registrar correctamente una actividad | datosCompletosHabilitanGuardar / agregar_actividad_utiliza_el_dao | APROBADO | No detectado |
| CP-02 | Aceptar información incompleta | tituloMenorA3EsInvalido | PARCIAL | Cobertura incompleta: falta título vacío |
| CP-05 | Aceptar progreso fuera del rango permitido | progreso101EsInvalido / progreso_menor_que_0_es_invalido / progreso_mayor_que_100_es_invalido | APROBADO | No detectado |
| CP-06 | Cambio de estado incorrecto | transicion_de_pendiente_a_en_curso | APROBADO | No detectado |
| CP-09 | Aceptar solicitudes inválidas | No implementada | NO EJECUTADO | No aplica; endpoint requerido no disponible |
| CP-10 | Respuesta incorrecta de API | obtenerActividades_respuesta200 | APROBADO | No detectado |
| CP-12 | Problemas visuales o de interacción | Exploración manual | MANUAL | Se registra solo si se encuentra hallazgo |

---

## 3. Trazabilidad resumida

La cadena utilizada durante esta semana es:

Caso de prueba  
↓  
Riesgo asociado  
↓  
Prueba automatizada  
↓  
Resultado real  
↓  
Defecto o pendiente

---

## 4. Pendientes identificados

### CP-02

Agregar una prueba específica que utilice título vacío o compuesto únicamente por espacios.

### CP-09

El caso requiere una operación de API que permita enviar datos para creación o actualización.

La API utilizada actualmente en las pruebas no ofrece ese flujo, por lo que no es correcto simular su ejecución como aprobada.

---

## 5. Criterio de calidad

Un caso solamente se considera automatizado cuando existe:

1. Código de prueba ejecutable.
2. Entrada controlada.
3. Resultado esperado verificable mediante aserción.
4. Ejecución real.
5. Resultado registrado.

Con este criterio se evita presentar como automatizados escenarios que únicamente fueron planificados.