# Semana 6 - Documentación Final de Persistencia Local

## Proyecto: MiFormacionCTMA2

## 1. Objetivo

Implementar persistencia local en MiFormacionCTMA2 utilizando Room 3 y DataStore, aplicando una arquitectura con separación de responsabilidades y manteniendo una fuente única de verdad para los datos de la aplicación.

Durante esta semana se reemplazó el manejo de actividades únicamente en memoria por almacenamiento persistente mediante Room.

También se implementó DataStore para conservar preferencias del usuario, una migración controlada de la base de datos y pruebas instrumentadas para verificar el funcionamiento de la capa de persistencia.

---

## 2. Arquitectura implementada

La aplicación utiliza el siguiente flujo principal:

Compose
↓
ViewModel
↓
Repository
↓
Room
↓
SQLite

Para las preferencias del usuario se utiliza:

Compose
↓
ViewModel
↓
PreferencesRepository
↓
DataStore

Room funciona como fuente principal de verdad para las actividades almacenadas.

La interfaz no accede directamente a los DAO ni ejecuta consultas SQL.

---

## 3. Componentes principales

### Interfaz - Compose

La interfaz permite:

- visualizar las actividades;
- crear nuevas actividades;
- editar actividades existentes;
- eliminar actividades;
- seleccionar el criterio de ordenamiento.

Los cambios realizados en los datos se reflejan nuevamente en la interfaz mediante Flow.

### ActividadViewModel

El ViewModel actúa como intermediario entre la interfaz y los repositorios.

Sus principales responsabilidades son:

- observar las actividades;
- insertar actividades;
- actualizar actividades;
- eliminar actividades;
- observar el criterio de ordenamiento;
- guardar cambios en la preferencia de ordenamiento.

### ActividadRepository

El Repository proporciona a la capa superior acceso a las operaciones relacionadas con las actividades sin exponer directamente el DAO a la interfaz.

El flujo utilizado es:

UI → ViewModel → Repository → DAO → Room

### PreferencesRepository

Gestiona el acceso a Preferences DataStore.

Permite almacenar y observar el criterio de ordenamiento seleccionado por el usuario.

Los criterios implementados son:

- Días
- Título
- Progreso

---

## 4. Persistencia con Room

La aplicación utiliza una base de datos denominada:

`mi_formacion_ctma.db`

Las entidades principales son:

### ActividadEntity

Campos:

- id
- titulo
- descripcion
- progreso
- diasRestantes
- prioridad
- categoriaId
- resuelto

### CategoriaEntity

Campos:

- id
- nombre

La relación entre ambas entidades se realiza mediante:

`ActividadEntity.categoriaId → CategoriaEntity.id`

La clase `ActividadConCategoria` permite recuperar una actividad junto con la categoría relacionada.

---

## 5. DAO

### ActividadDao

Incluye operaciones para:

- insertar;
- actualizar;
- eliminar;
- consultar todas las actividades;
- consultar por ID;
- buscar por título;
- eliminar todos los registros;
- consultar actividades junto con su categoría.

Las consultas observables utilizan Flow para propagar automáticamente los cambios.

### CategoriaDao

Permite:

- insertar categorías;
- observar categorías;
- consultar una categoría por ID;
- eliminar una categoría por ID.

---

## 6. Fuente única de verdad

Room constituye la fuente principal de verdad para las actividades.

No se mantiene una segunda colección principal en memoria que compita con la base de datos.

Cuando ocurre una modificación:

Usuario
↓
Compose
↓
ViewModel
↓
Repository
↓
Room
↓
Flow
↓
ViewModel
↓
Compose

Esto permite que los cambios sean reflejados en la interfaz sin realizar una recarga manual de los datos.

---

## 7. Persistencia de preferencias con DataStore

Se implementó Preferences DataStore para conservar el criterio de ordenamiento.

La preferencia se almacena mediante la clave:

`orden_actividades`

La aplicación puede ordenar las actividades por:

- días restantes;
- título;
- progreso.

Se verificó que la preferencia permanece almacenada después de cerrar y volver a ejecutar la aplicación.

---

## 8. Evolución de la base de datos

La base de datos cuenta con dos versiones de esquema exportadas:

- versión 1;
- versión 2.

Los esquemas se encuentran en el directorio:

`app/schemas/com.sofia.miformacionctma.data.local.AppDatabase/`

Archivos:

- `1.json`
- `2.json`

### Versión 1

La entidad de actividades no contenía el campo:

`resuelto`

### Versión 2

Se incorporó:

`resuelto`

como campo obligatorio con valor predeterminado:

`false`

---

## 9. Migración 1 → 2

Se implementó `MIGRATION_1_2`.

La migración agrega la nueva columna mediante una instrucción equivalente a:

`ALTER TABLE actividades ADD COLUMN resuelto INTEGER NOT NULL DEFAULT 0`

El valor `0` representa `false`.

La migración se registra al construir la base de datos y no se utiliza una estrategia de migración destructiva.

Se verificó que los registros creados antes de la migración permanecen disponibles después de actualizar el esquema.

---

## 10. Pruebas instrumentadas del DAO

Se implementaron seis pruebas instrumentadas sobre `ActividadDao`.

Pruebas ejecutadas:

1. `insertarActividad_yConsultarPorId`
2. `actualizarActividad`
3. `eliminarActividad`
4. `buscarActividadPorTitulo`
5. `eliminarTodasLasActividades`
6. `obtenerActividadConCategoria`

Resultado obtenido:

`6 passed / 6 tests`

Las pruebas verifican:

- inserción;
- consulta por ID;
- actualización;
- eliminación;
- búsqueda;
- eliminación de registros;
- relación entre actividad y categoría.

---

## 11. Prueba de migración

Se implementó una prueba instrumentada para verificar la evolución de la base de datos de la versión 1 a la versión 2.

La prueba:

1. crea una estructura equivalente a la versión 1;
2. inserta una actividad;
3. ejecuta `MIGRATION_1_2`;
4. consulta nuevamente la actividad;
5. verifica que los datos originales permanezcan;
6. comprueba que `resuelto` tenga inicialmente el valor `false`;
7. vuelve a abrir la base de prueba y comprueba la persistencia.

Resultado:

`1 passed / 1 test`

---

## 12. Database Inspector

Se utilizó Database Inspector de Android Studio para inspeccionar la base de datos durante la ejecución de la aplicación.

Se verificó la existencia de:

`mi_formacion_ctma.db`

y de las tablas:

- actividades;
- categorias;
- room_master_table.

También se observaron directamente registros creados desde la aplicación dentro de la tabla `actividades`.

Esto permitió comprobar visualmente que los datos mostrados en la interfaz se encuentran almacenados en Room.

---

## 13. Casos de prueba y criterios verificados

### PA-01 - Persistencia después de reiniciar

Se creó una actividad, se cerró la aplicación y se volvió a ejecutar.

Resultado:

La actividad continuó disponible.

Estado: APROBADO.

### PA-02 - CRUD reflejado en la interfaz

Se realizaron operaciones de creación, edición y eliminación.

Resultado:

Los cambios fueron reflejados desde Room mediante Flow sin necesidad de una recarga manual.

Estado: APROBADO.

### PA-03 - Consulta de registros

El DAO permite consultar una actividad por ID y representar la ausencia mediante un resultado nulo.

La prueba de consulta por ID se ejecutó satisfactoriamente.

Estado: APROBADO a nivel de persistencia.

### PA-04 - Búsqueda y ordenamiento

Se verificó mediante prueba instrumentada la búsqueda de actividades por título.

También se implementó ordenamiento por días, título y progreso.

Estado: APROBADO.

### PA-05 - Persistencia de preferencias

Se seleccionó el ordenamiento por título, se cerró la aplicación y se volvió a ejecutar.

Resultado:

La selección permaneció almacenada mediante DataStore.

Estado: APROBADO.

### PA-06 - Migración v1 → v2

Se ejecutó una migración de la versión 1 a la versión 2.

Resultado:

Los datos existentes se conservaron y el campo `resuelto` quedó inicialmente en `false`.

Estado: APROBADO.

### PA-07 - DAO

Se ejecutaron seis pruebas instrumentadas.

Resultado:

`6 passed / 6 tests`

Se validaron operaciones CRUD, búsqueda y relación entre entidades.

Estado: APROBADO.

---

## 14. Decisiones técnicas

### Uso de Room 3

Se utiliza Room 3 para gestionar la persistencia estructurada y evitar el manejo manual de SQLite desde la interfaz.

### Uso de Repository

Se utiliza Repository para evitar que Compose acceda directamente a los DAO.

### Uso de Flow

Las consultas principales utilizan Flow para observar los cambios realizados en la base de datos.

### Uso de DataStore

Las preferencias pequeñas del usuario se almacenan mediante Preferences DataStore en lugar de crear tablas adicionales en Room.

### Migración controlada

La evolución de la versión 1 a la versión 2 se realiza mediante una migración explícita.

No se utiliza una migración destructiva, evitando eliminar los datos existentes.

---

## 15. Versiones principales

Durante la implementación se utilizaron, entre otras, las siguientes versiones:

- Room 3: 3.0.3
- Kotlin: 2.2.10
- KSP: 2.2.10-2.0.2
- DataStore: 1.2.1
- SQLite: 2.7.1
- Lifecycle: 2.11.0
- Activity Compose: 1.13.0
- Compose BOM: 2026.02.01

La aplicación utiliza un `minSdk` de 24.

---

## 16. Evidencias

Las evidencias correspondientes a Semana 6 se encuentran organizadas en:

`evidencias/semana6/`

Entre las evidencias obtenidas se encuentran:

- persistencia de actividades con Room;
- persistencia del criterio de ordenamiento con DataStore;
- migración de Room versión 1 a versión 2;
- ejecución de las pruebas instrumentadas del DAO;
- ejecución de la prueba de migración;
- inspección de la base de datos mediante Database Inspector.

---

## 17. Limitaciones y aspectos pendientes

La implementación realizada está orientada a demostrar los conceptos de persistencia local correspondientes a la Semana 6.

La relación entre actividades y categorías se encuentra implementada y probada en la capa de persistencia, aunque su utilización completa en la interfaz puede ampliarse posteriormente.

El campo `resuelto` forma parte de la versión 2 de la base de datos y de las pruebas de migración, aunque actualmente no constituye una función principal de interacción en la interfaz.

Las pruebas implementadas se concentran principalmente en la persistencia y los DAO.

---

## 18. Conclusión

La implementación de Semana 6 permitió transformar MiFormacionCTMA2 en una aplicación con persistencia local real.

Room funciona como fuente principal de verdad para los datos estructurados, mientras que DataStore conserva las preferencias del usuario.

La arquitectura mantiene separación entre Compose, ViewModel, Repository y persistencia.

Finalmente, las pruebas instrumentadas, la migración de esquema y Database Inspector permitieron verificar el funcionamiento de la solución y la conservación de los datos.