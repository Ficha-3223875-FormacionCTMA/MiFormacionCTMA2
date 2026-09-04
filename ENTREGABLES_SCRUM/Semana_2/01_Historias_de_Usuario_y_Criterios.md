# Semana 2 - Historias de usuario y criterios de aceptación

**Proyecto:** Mi Formación CTMA  
**Versión:** 1.0  
**Caso aplicado:** Gestión de actividades formativas del aprendiz

## HU-MFC-01 - Consultar actividades

**Historia:**  
Como aprendiz, quiero consultar mis actividades formativas para organizar mis compromisos y conocer el progreso de cada una.

**Criterios de aceptación**
- **CA-01:** Dado que existen actividades registradas, cuando el aprendiz abre la aplicación, entonces se muestra la lista de actividades con título, descripción, estado, días restantes, progreso y prioridad.
- **CA-02:** Dado que existen actividades, cuando el aprendiz visualiza el resumen, entonces se muestra la cantidad total y el promedio de progreso.
- **CA-03:** Dado que una actividad tiene 100% de progreso, cuando se muestra la tarjeta, entonces su estado se presenta como `COMPLETADA`.

## HU-MFC-02 - Consultar estado y progreso

**Historia:**  
Como aprendiz, quiero identificar el estado y porcentaje de progreso de una actividad para saber cuáles requieren atención.

**Criterios de aceptación**
- **CA-04:** Dado un progreso entre 0 y 100, cuando se muestra la actividad, entonces el progreso se presenta dentro de ese rango.
- **CA-05:** Dado que una actividad tiene días restantes negativos y progreso menor a 100%, cuando se consulta, entonces se identifica como vencida.
- **CA-06:** Dado que una actividad tiene progreso mayor que 0% y no está vencida ni completada, cuando se consulta, entonces se identifica como en curso.

## HU-MFC-03 - Seleccionar una actividad

**Historia:**  
Como aprendiz, quiero seleccionar una actividad para identificar cuál estoy consultando.

**Criterios de aceptación**
- **CA-07:** Dado que una actividad está visible, cuando el aprendiz la selecciona, entonces el sistema recibe la actividad correspondiente.
- **CA-08:** Dado que una actividad tiene título, cuando es seleccionada, entonces se informa el título de la actividad.
- **CA-09:** La interacción de selección no debe alterar el progreso almacenado de la actividad.

## Requisito no funcional medible

**RNF-MFC-01 - Rendimiento:** en un dispositivo Android representativo, la pantalla inicial debe mostrar el contenido disponible sin bloqueo perceptible de la interfaz durante la carga de la lista de actividades de prueba.

## Dependencias

- Modelo `ActividadFormativa`.
- Reglas de negocio de `ReglasActividad.kt`.
- Componente visual `TarjetaActividad`.
- Pantalla `PantallaActividades`.
- Datos de prueba definidos para las pruebas.

## Supuestos

- Los datos usados durante las pruebas son ficticios.
- El porcentaje válido de progreso está entre 0 y 100.
- Una actividad con 100% de progreso se considera completada.
