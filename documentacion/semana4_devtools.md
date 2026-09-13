.# Semana 4 – Laboratorio 1: Inspección de un flujo WEB con Chrome DevTools

## Proyecto: MiFormacionCTMA2

### Objetivo

Realizar la inspección de un flujo WEB utilizando Chrome DevTools para observar el comportamiento de una solicitud HTTP, las validaciones de la interfaz, la consola del navegador y el almacenamiento local.

> **Nota:** MiFormacionCTMA2 es una aplicación Android. Para cumplir el laboratorio de la guía se utilizó un entorno académico simulado (`mock_web/index.html`) destinado únicamente a prácticas de pruebas WEB y DevTools.

---

## Ambiente de ejecución


| Elemento          | Valor                  |
| ----------------- | ---------------------- |
| Proyecto          | MiFormacionCTMA2       |
| Herramienta       | Google Chrome DevTools |
| Archivo ejecutado | `mock_web/index.html`  |
| Tipo de entorno   | Mock WEB académico     |
| Datos utilizados  | Sintéticos             |

---

## Caso positivo – CP-MF-001

### Descripción

Se ejecutó el flujo de creación de una actividad utilizando datos válidos.

### Datos enviados

| Campo       | Valor                           |
| ----------- | ------------------------------- |
| Título      | Estudiar Kotlin                 |
| Descripción | Repasar funciones y colecciones |
| Progreso    | 50                              |

### Resultado esperado

La solicitud debe enviarse correctamente y el servidor de prueba debe responder con código **201 Created**.

### Resultado observado

La aplicación simulada ejecutó correctamente la solicitud HTTP y mostró el resultado **PASS - Caso positivo**.

### Evidencias registradas

* `01_devtools_network_caso_positivo.png`
* `02_devtools_headers_post_201.png`
* `03_devtools_payload_caso_positivo.png`

---

## Inspección Network

Se observó la solicitud HTTP generada desde el botón **Probar caso positivo**.

### Información obtenida

| Propiedad   | Valor observado                              |
| ----------- | -------------------------------------------- |
| Método      | POST                                         |
| URL         | `https://jsonplaceholder.typicode.com/posts` |
| Código HTTP | 201 Created                                  |
| Tipo        | Fetch/XHR                                    |

La pestaña **Network** permitió identificar la solicitud realizada por la aplicación simulada y verificar la respuesta del servidor de pruebas.

---

## Inspección Headers

En la pestaña **Headers** se verificó:

* URL de la solicitud.
* Método HTTP.
* Código de respuesta.
* Encabezados enviados y recibidos.

La respuesta confirmó una creación exitosa del recurso.

---

## Inspección Payload

La pestaña **Payload** permitió revisar el cuerpo de la solicitud enviada.

### Payload observado

* titulo = Estudiar Kotlin
* descripcion = Repasar funciones y colecciones
* progreso = 50

Esto permitió validar que la información enviada coincide con los datos definidos en el caso de prueba CP-MF-001.

---

## Caso negativo – CP-MF-005 y CP-MF-006

### Descripción

Se intentó registrar una actividad con datos inválidos.

### Datos utilizados

| Campo    | Valor |
| -------- | ----- |
| Título   | AB    |
| Progreso | 101   |

### Resultado esperado

La aplicación debe impedir el envío de información inválida.

### Resultado observado

El formulario rechazó correctamente la actividad y mostró **PASS - Caso negativo** indicando que los datos no fueron aceptados.

### Evidencia registrada

* `04_devtools_caso_negativo_validacion.png`

---

## Inspección Console

Durante la ejecución del caso negativo se revisó la pestaña **Console**.

### Mensajes observados

* CP-MF-005 / CP-MF-006 – Caso negativo simulado.
* Validación rechazada correctamente.

La consola permitió confirmar que la validación fue ejecutada antes de intentar enviar la solicitud.

### Evidencia registrada

* `05_devtools_console_caso_negativo.png`

---

## Inspección Application

Se revisó el almacenamiento local del navegador.

### Local Storage

| Clave        | Valor              |
| ------------ | ------------------ |
| ultimaPrueba | CP-MF-005/006 PASS |

Este registro confirma que el resultado de la última prueba fue almacenado correctamente en el navegador mediante `localStorage`.

### Evidencia registrada

* `06_devtools_application_localstorage.png`

---

## Resumen de resultados

| Caso      | Resultado |
| --------- | --------- |
| CP-MF-001 | PASS      |
| CP-MF-005 | PASS      |
| CP-MF-006 | PASS      |

Las validaciones del formulario y la inspección mediante Chrome DevTools permitieron comprobar el comportamiento esperado del entorno WEB simulado.

---

## Evidencias utilizadas

| Archivo                                  |
| ---------------------------------------- |
| 01_devtools_network_caso_positivo.png    |
| 02_devtools_headers_post_201.png         |
| 03_devtools_payload_caso_positivo.png    |
| 04_devtools_caso_negativo_validacion.png |
| 05_devtools_console_caso_negativo.png    |
| 06_devtools_application_localstorage.png |

---

## Conclusión

El laboratorio permitió utilizar Chrome DevTools para inspeccionar solicitudes HTTP, validar información enviada en el cuerpo de una petición, revisar mensajes de consola y consultar datos almacenados en `localStorage`. Estas actividades cumplen el objetivo del Laboratorio 1 de la Semana 4 adaptado al proyecto MiFormacionCTMA2 mediante un entorno WEB simulado.
