# Ejecución de pruebas de humo, exploratorias y de regresión — Semana 4

## 1. Objetivo

Documentar tres tipos de pruebas utilizados para verificar el
funcionamiento de la aplicación:

- Pruebas de humo.
- Pruebas exploratorias.
- Pruebas de regresión.

Estas pruebas complementan los casos definidos en las semanas anteriores.

## 2. Pruebas de humo

Las pruebas de humo permiten verificar rápidamente que las funciones
principales de la aplicación están disponibles y que el sistema puede
utilizarse para continuar con pruebas más detalladas.

### Casos considerados

| ID | Prueba | Resultado esperado | Resultado obtenido | Estado |
|---|---|---|---|---|
| HUM-01 | Iniciar aplicación | La aplicación inicia correctamente | Registrar al ejecutar | Pendiente |
| HUM-02 | Abrir pantalla principal | La pantalla se muestra correctamente | Registrar al ejecutar | Pendiente |
| HUM-03 | Acceder a actividades | Se muestran las actividades | Registrar al ejecutar | Pendiente |
| HUM-04 | Abrir formulario | El formulario se muestra | Registrar al ejecutar | Pendiente |

## 3. Pruebas exploratorias

Las pruebas exploratorias permiten revisar el comportamiento de la
aplicación sin limitarse exclusivamente a los casos previamente definidos.

Durante la exploración se pueden revisar:

- Navegación.
- Campos de formularios.
- Validaciones.
- Botones.
- Mensajes mostrados al usuario.
- Valores inesperados.
- Comportamiento ante entradas incorrectas.
- Persistencia de información cuando corresponda.

### Registro exploratorio

| ID | Área explorada | Acción | Observación | Estado |
|---|---|---|---|---|
| EXP-01 | Navegación | Recorrer las pantallas | Registrar al ejecutar | Pendiente |
| EXP-02 | Formulario | Probar campos obligatorios | Registrar al ejecutar | Pendiente |
| EXP-03 | Formulario | Probar valores inválidos | Registrar al ejecutar | Pendiente |
| EXP-04 | Actividades | Crear y consultar información | Registrar al ejecutar | Pendiente |
| EXP-05 | Mensajes | Revisar mensajes de error | Registrar al ejecutar | Pendiente |

## 4. Pruebas de regresión

Las pruebas de regresión se realizan después de cambios en el software
para comprobar que las funcionalidades existentes continúan funcionando.

Para este proyecto se pueden repetir los casos funcionales más
importantes después de realizar modificaciones.

### Casos de regresión

| ID | Funcionalidad | Resultado esperado | Resultado obtenido | Estado |
|---|---|---|---|---|
| REG-01 | Inicio de aplicación | La aplicación inicia | Registrar al ejecutar | Pendiente |
| REG-02 | Listado de actividades | La información se muestra | Registrar al ejecutar | Pendiente |
| REG-03 | Crear actividad | La actividad se registra | Registrar al ejecutar | Pendiente |
| REG-04 | Validaciones | Las reglas se mantienen | Registrar al ejecutar | Pendiente |
| REG-05 | Consulta de actividad | Se muestra la información correcta | Registrar al ejecutar | Pendiente |

## 5. Orden recomendado de ejecución

Se recomienda ejecutar las pruebas en el siguiente orden:

1. Pruebas de humo.
2. Pruebas funcionales.
3. Pruebas exploratorias.
4. Corrección de defectos encontrados.
5. Pruebas de regresión.

Este orden permite identificar primero problemas básicos antes de
realizar pruebas más detalladas.

## 6. Evidencias

Las evidencias pueden incluir:

- Capturas del emulador.
- Capturas de la aplicación.
- Capturas de formularios.
- Capturas de validaciones.
- Capturas de respuestas WEB/API.
- Capturas de herramientas de desarrollo.

Cada evidencia debe asociarse con el ID de la prueba correspondiente.

## 7. Registro de defectos

Cuando una prueba falle se debe registrar el defecto encontrado.

El registro debe contener como mínimo:

- Identificador del defecto.
- Caso de prueba relacionado.
- Descripción.
- Pasos para reproducirlo.
- Resultado esperado.
- Resultado obtenido.
- Evidencia.
- Estado.

## 8. Resultado de ejecución

Los resultados de las pruebas deben actualizarse después de realizar
la ejecución real.

No se deben marcar como aprobadas pruebas que todavía no hayan sido
ejecutadas.

Los estados permitidos son:

- Pendiente.
- Aprobado.
- Fallido.
- Bloqueado.

## 9. Cierre

Las pruebas de humo, exploratorias y de regresión permiten obtener
información complementaria sobre la calidad del software.

El cierre de estas pruebas requiere registrar los resultados reales y
conservar las evidencias correspondientes.