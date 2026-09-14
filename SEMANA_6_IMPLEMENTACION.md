# Semana 6 - Persistencia local y fuente única de verdad

## Implementación realizada

Esta entrega agrega la capa de persistencia de la Semana 6 sin reemplazar el trabajo de las Semanas 2, 3 y 4 ni modificar el README principal.

### Room
- Room 3.0.3 con KSP.
- `ReporteEntity` y `CategoriaEntity`.
- Relación `Reporte -> Categoria` mediante clave foránea.
- `ReporteDao` y `CategoriaDao` con CRUD, consultas observables, búsqueda y consulta relacional.
- `AppDatabase` versión 2.
- `DatabaseProvider` con una única instancia.
- `RoomReporteRepository` como puerta de entrada de los datos.
- Driver SQLite empaquetado (`BundledSQLiteDriver`).

### DataStore
- `PreferenciasRepository` con Preferences DataStore.
- Preferencia de categoría, orden y modo de visualización.
- Exposición mediante `Flow`.

### Migración
- Esquema versión 1 incluido.
- Migración explícita 1 -> 2.
- La columna `resuelto` se agrega con valor predeterminado `false`/`0`.
- No se utiliza migración destructiva.
- Esquema versión 2 incluido.

### Pruebas
- Prueba instrumentada de CRUD, búsqueda y relación.
- Prueba instrumentada de migración 1 -> 2.

## Arquitectura

```text
Compose / ViewModel
        |
        v
Repository
   |         |
   v         v
 Room     DataStore
   |         |
   +---- Flow ----+
                 |
                 v
                 UI
```

## Datos persistentes y efímeros

| Dato | Almacenamiento |
|---|---|
| Reportes | Room |
| Categorías | Room |
| Filtro de categoría | DataStore |
| Orden | DataStore |
| Modo de visualización | DataStore |
| Texto no guardado del formulario | Estado de UI |

## Nota

El README principal se conserva sin cambios porque forma parte de las entregas anteriores. Este documento permite identificar de forma separada las decisiones y componentes agregados en la Semana 6.
