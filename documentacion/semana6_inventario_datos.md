# Semana 6 - Inventario de Datos

## Proyecto: MiFormacionCTMA2

## 1. Objetivo

Clasificar los datos utilizados por MiFormacionCTMA2 según el mecanismo de persistencia adecuado, diferenciando entre:

- Room para datos estructurados y persistentes.
- DataStore para preferencias pequeñas del usuario.
- Estado efímero para información temporal de la interfaz.

Esta clasificación permite mantener una fuente única de verdad y evitar duplicidad innecesaria de información.

---

## 2. Datos almacenados en Room

Room se utiliza como fuente principal de verdad para la información estructurada de la aplicación.

### Actividad

La entidad `ActividadEntity` almacena:

- id
- titulo
- descripcion
- progreso
- diasRestantes
- prioridad
- categoriaId
- resuelto

Los registros permanecen almacenados aunque la aplicación se cierre y vuelva a ejecutarse.

El campo `categoriaId` permite relacionar una actividad con una categoría.

El campo `resuelto` fue incorporado en la versión 2 del esquema de la base de datos mediante una migración desde la versión 1, utilizando como valor predeterminado `false`.

### Categoría

La entidad `CategoriaEntity` almacena:

- id
- nombre

Las categorías pueden relacionarse con las actividades mediante `categoriaId`.

### Justificación

Room es utilizado porque esta información:

- es estructurada;
- debe persistir entre ejecuciones;
- requiere operaciones CRUD;
- necesita consultas y búsquedas;
- es observada mediante Flow;
- contiene relaciones entre entidades.

Por estas razones, Room constituye la fuente principal de verdad para las actividades y categorías.

---

## 3. Datos almacenados en DataStore

DataStore se utiliza para almacenar el criterio de ordenamiento seleccionado por el usuario.

Los criterios implementados son:

- Días
- Título
- Progreso

La preferencia seleccionada permanece almacenada después de cerrar y volver a ejecutar la aplicación.

### Justificación

El criterio de ordenamiento:

- es una preferencia pequeña;
- no corresponde a una entidad del negocio;
- debe mantenerse entre ejecuciones;
- puede observarse mediante Flow.

Por esta razón se almacena mediante Preferences DataStore y no como una tabla de Room.

---

## 4. Estado efímero de la interfaz

La aplicación también utiliza información temporal que no necesita almacenarse permanentemente.

Ejemplos:

- datos introducidos mientras se completa un formulario;
- estado de navegación entre la lista y el formulario;
- actividad seleccionada temporalmente para edición;
- estados temporales utilizados por Compose.

Estos datos pueden administrarse mediante mecanismos de estado de Compose y ViewModel según corresponda.

---

## 5. Matriz de clasificación

| Dato | Room | DataStore | Estado efímero | Justificación |
|---|---|---|---|---|
| Actividades | Sí | No | No | Información estructurada y persistente |
| Categorías | Sí | No | No | Información relacionada con las actividades |
| Título | Sí | No | No | Campo persistente de una actividad |
| Descripción | Sí | No | No | Campo persistente de una actividad |
| Progreso | Sí | No | No | Campo persistente de una actividad |
| Días restantes | Sí | No | No | Campo persistente de una actividad |
| Prioridad | Sí | No | No | Campo persistente de una actividad |
| categoriaId | Sí | No | No | Permite relacionar actividad y categoría |
| Estado resuelto | Sí | No | No | Campo incorporado en la versión 2 del esquema |
| Criterio de ordenamiento | No | Sí | No | Preferencia persistente del usuario |
| Datos del formulario sin guardar | No | No | Sí | Información temporal de la interfaz |
| Actividad seleccionada para editar | No | No | Sí | Estado temporal de navegación y edición |

---

## 6. Fuente única de verdad

La arquitectura utilizada es:

Compose → ViewModel → Repository → Room / DataStore

Room actúa como fuente única de verdad para las actividades almacenadas.

Los cambios realizados mediante el Repository se reflejan en Room y son observados mediante Flow para actualizar nuevamente la interfaz.

No se mantiene una segunda lista principal en memoria que compita con los datos persistidos.

---

## 7. Separación de responsabilidades

La interfaz Compose:

- no ejecuta SQL;
- no accede directamente a los DAO;
- no crea directamente la base de datos;
- no accede directamente a DataStore.

El flujo principal utilizado es:

Compose → ViewModel → Repository → Room

Para las preferencias:

Compose → ViewModel → PreferencesRepository → DataStore

Esta separación mantiene desacopladas la interfaz, la lógica de presentación y la persistencia.

---

## 8. Evolución del esquema

La base de datos utiliza actualmente la versión 2.

La evolución realizada fue:

Versión 1:
- actividades sin el campo `resuelto`.

Versión 2:
- incorporación del campo `resuelto`;
- valor predeterminado `false`;
- conservación de los registros existentes.

La migración `MIGRATION_1_2` permite actualizar la estructura sin utilizar migraciones destructivas.

La conservación de datos y el valor inicial del nuevo campo fueron comprobados mediante una prueba instrumentada de migración.

---

## 9. Verificación realizada

Durante la implementación se verificó:

- persistencia de actividades después de cerrar y volver a abrir la aplicación;
- creación, consulta, actualización y eliminación de actividades;
- búsqueda de actividades por título;
- relación entre actividades y categorías;
- persistencia del criterio de ordenamiento mediante DataStore;
- migración de Room de la versión 1 a la versión 2 sin pérdida de datos;
- almacenamiento real mediante Database Inspector;
- ejecución satisfactoria de las pruebas instrumentadas del DAO.

---

## 10. Conclusión

MiFormacionCTMA2 utiliza Room para los datos estructurados y persistentes, DataStore para el criterio de ordenamiento del usuario y estado de Compose para información temporal de la interfaz.

La implementación mantiene una arquitectura basada en Repository, utiliza Flow para observar los cambios y permite conservar la información entre ejecuciones.

Además, la base de datos cuenta con una migración controlada de la versión 1 a la versión 2 y pruebas instrumentadas para validar las operaciones principales de persistencia.