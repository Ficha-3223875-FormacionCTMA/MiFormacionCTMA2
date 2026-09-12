# MiFormacionCTMA2

**Primer proyecto Android - Semana 1 ADSO**

## Problema

Los aprendices manejan actividades, fechas y evidencias en diferentes canales, lo que genera olvidos y poca organización. La aplicación Mi Formación CTMA permitirá consultar compromisos y registrar avances desde el celular.

## Usuarios

* **Aprendiz:** consultar actividades y registrar avances.
* **Instructor:** publicar actividades y hacer seguimiento.

## Historias de usuario

1. Como aprendiz quiero ver mis actividades para organizar mi semana.
2. Como aprendiz quiero registrar una evidencia para controlar mis entregas.
3. Como instructor quiero publicar actividades para que los aprendices las consulten.

## Criterios de aceptación

* La lista de actividades debe mostrarse al abrir la app.
* El registro de evidencia debe guardar una descripción básica.
* Las actividades publicadas por el instructor deben ser visibles para el aprendiz.

## Tecnologías utilizadas

* Android Studio
* Kotlin
* Jetpack Compose
* Git y GitHub

## Semana 4 · Estado, formularios y navegación

El incremento de Semana 4 conserva la interfaz de Semana 3 y agrega:
- estado `FormularioActividadUiState` y flujo unidireccional de eventos;
- formulario stateless para crear actividades;
- validación de título, descripción, fecha y progreso;
- `rememberSaveable` para conservar el borrador al recrear la Activity;
- Navigation Compose con destinos Lista, Crear y Detalle;
- navegación por `actividadId` y manejo controlado de actividad inexistente;
- guardado de una sola actividad y retorno mediante `popBackStack()`;
- pruebas unitarias de validaciones y casos límite.
