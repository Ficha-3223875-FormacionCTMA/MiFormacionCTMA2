# Semana 3 - Pruebas de Software y SCRUM

## Proyecto: MiFormacionCTMA2

## Actividad de activación

### Historia de usuario seleccionada

**HU-MF-01**

Como aprendiz, quiero crear una actividad formativa para organizar mis compromisos académicos desde la aplicación.

### Criterio de aceptación seleccionado

**CA-MF-01**

Dado que el aprendiz se encuentra en el formulario de creación de actividad,
cuando completa correctamente los campos obligatorios y selecciona guardar,
entonces la aplicación debe crear una sola actividad y mostrarla en la lista.

### Riesgo asociado

**RSK-MF-01 - Creación incorrecta o duplicada de actividades**

Existe el riesgo de que una actividad sea creada con datos inválidos o que se registre más de una vez por acciones repetidas del usuario.

### Prioridad de prueba

**Alta**

La creación de actividades es una función principal de la aplicación y un error puede afectar la organización y confiabilidad de la información mostrada al aprendiz.

## Preguntas de diseño

1. ¿Qué campos del formulario son obligatorios y cuáles son opcionales?
2. ¿Qué valores mínimos y máximos son aceptados para título, descripción y progreso?
3. ¿Qué debe ocurrir si el usuario presiona varias veces el botón Guardar?
4. ¿Qué debe suceder si la fecha ingresada es anterior a la fecha actual?
5. ¿Qué estado debe conservar el formulario cuando ocurre una recreación de la Activity?

## Escenario que debe aprobar

El aprendiz ingresa un título válido, una descripción válida, una fecha permitida, un progreso entre 0 y 100 y selecciona una prioridad. Al guardar, la aplicación crea una única actividad y regresa a la lista.

## Escenario que debe ser rechazado

El aprendiz ingresa un título menor al mínimo permitido, una fecha anterior a la actual o un progreso superior a 100. La aplicación debe impedir el guardado y mostrar mensajes de validación comprensibles.

## Trazabilidad

Los casos de prueba que se diseñen posteriormente deberán relacionarse con:

- Historia de usuario.
- Criterio de aceptación.
- Riesgo.
- Técnica de prueba aplicada.
- Resultado esperado.
- Defecto asociado, si existe.