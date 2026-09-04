# Semana 3 - Escenario de caso de uso

## CU-MFC-01 - Consultar y seleccionar actividad

**Actor principal:** Aprendiz.

**Precondición:** la aplicación está iniciada y existen actividades de prueba.

### Flujo principal
1. El aprendiz abre la aplicación.
2. El sistema muestra las actividades.
3. El aprendiz identifica una actividad.
4. El aprendiz selecciona la tarjeta.
5. El sistema recibe la actividad seleccionada.

### Alterno A - No existen actividades
1. El aprendiz abre la aplicación.
2. La lista está vacía.
3. El sistema muestra el estado vacío.
4. Se ofrece la acción para agregar una actividad.

### Excepción B - Datos inválidos
1. Se intenta procesar una actividad con progreso fuera de 0..100.
2. La regla de validación detecta el valor.
3. El sistema no debe aceptar el dato como válido.

### Excepción C - Selección
1. El aprendiz selecciona una actividad.
2. La aplicación informa el título seleccionado.
3. El progreso y demás datos permanecen sin cambios.
