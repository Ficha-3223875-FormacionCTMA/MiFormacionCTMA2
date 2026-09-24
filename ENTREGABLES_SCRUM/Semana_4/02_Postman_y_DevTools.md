# Postman y DevTools — Semana 4

## 1. Objetivo

Utilizar herramientas de apoyo para la ejecución y análisis de pruebas
sobre servicios WEB/API y sobre el comportamiento de la aplicación.

Las herramientas consideradas son Postman y las herramientas para
desarrolladores del navegador (DevTools).

## 2. Postman

Postman permite realizar solicitudes HTTP contra un servicio WEB/API y
observar la respuesta obtenida.

Durante las pruebas se deben revisar:

- Método HTTP.
- URL o endpoint.
- Parámetros.
- Encabezados.
- Cuerpo de la solicitud.
- Código de respuesta.
- Cuerpo de la respuesta.
- Tiempo de respuesta.
- Mensajes de error cuando correspondan.

## 3. Métodos HTTP

Los métodos que pueden utilizarse durante las pruebas incluyen:

| Método | Uso |
|---|---|
| GET | Consultar información |
| POST | Crear información |
| PUT | Actualizar información |
| PATCH | Actualizar parcialmente información |
| DELETE | Eliminar información |

El método utilizado debe corresponder con la operación que ofrece el
servicio.

## 4. Códigos de respuesta

Durante la ejecución se pueden encontrar diferentes códigos HTTP.

| Código | Significado general |
|---|---|
| 200 | Solicitud procesada correctamente |
| 201 | Recurso creado correctamente |
| 400 | Solicitud incorrecta |
| 401 | No autorizado |
| 403 | Acceso prohibido |
| 404 | Recurso no encontrado |
| 500 | Error interno del servidor |

El código observado debe registrarse como parte del resultado real de
la prueba.

## 5. Pruebas con datos válidos

Se deben utilizar datos sintéticos y controlados para comprobar que el
servicio responde correctamente ante solicitudes válidas.

Ejemplos:

- Consultar un recurso existente.
- Crear un registro con información válida.
- Actualizar información existente.
- Consultar información después de una operación.

## 6. Pruebas con datos inválidos

También se deben realizar pruebas con datos que no cumplen las reglas
definidas.

Ejemplos:

- Campos obligatorios vacíos.
- Formatos incorrectos.
- Valores fuera de los límites definidos.
- Identificadores inexistentes.
- Solicitudes incompletas.

El objetivo es comprobar que el sistema maneja correctamente las
situaciones de error.

## 7. DevTools

Las herramientas de desarrollo del navegador permiten observar el
comportamiento de una aplicación web durante su ejecución.

La pestaña Network permite revisar:

- Solicitudes realizadas.
- Método HTTP.
- URL.
- Código de respuesta.
- Tiempo de respuesta.
- Datos enviados.
- Datos recibidos.

La pestaña Console permite identificar mensajes, advertencias y errores
generados durante la ejecución.

## 8. Evidencias

Cuando una prueba requiera evidencia visual se puede realizar una
captura donde se observe:

- Solicitud realizada.
- Respuesta obtenida.
- Código HTTP.
- Mensaje de error.
- Información relevante de Network o Console.

La captura debe relacionarse con el ID del caso de prueba correspondiente.

## 9. Seguridad de las evidencias

No se deben incluir en las capturas:

- Contraseñas.
- Tokens de autenticación.
- Claves privadas.
- Información personal innecesaria.
- Credenciales.
- Datos sensibles.

Las evidencias deben utilizar datos sintéticos o datos autorizados para
las pruebas.

## 10. Registro de ejecución

| ID | Herramienta | Prueba | Resultado esperado | Resultado obtenido | Estado |
|---|---|---|---|---|---|
| WEB-01 | Postman | Consulta GET | Respuesta correcta | Registrar al ejecutar | Pendiente |
| WEB-02 | Postman | Creación POST | Recurso creado | Registrar al ejecutar | Pendiente |
| WEB-03 | Postman | Datos inválidos | Servicio rechaza datos | Registrar al ejecutar | Pendiente |
| WEB-04 | DevTools | Network | Solicitud visible | Registrar al ejecutar | Pendiente |
| WEB-05 | DevTools | Console | Sin errores inesperados | Registrar al ejecutar | Pendiente |

## 11. Conclusión

Postman y DevTools permiten observar el comportamiento de las solicitudes
y respuestas durante las pruebas.

Los resultados definitivos deben registrarse únicamente después de
realizar la ejecución correspondiente.