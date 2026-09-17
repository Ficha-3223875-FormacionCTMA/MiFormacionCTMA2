# Tabla de decisión y transición de estados - Semana 3

## Proyecto: MiFormacionCTMA2

---

# 1. Tabla de decisión - Guardar una actividad

La decisión de permitir el guardado depende de que los datos ingresados sean válidos y de que no exista un guardado en curso.

| Condición / Acción | R1 | R2 | R3 | R4 | R5 |
|---|---|---|---|---|---|
| Título válido | No | Sí | Sí | Sí | Sí |
| Fecha válida | - | No | Sí | Sí | Sí |
| Progreso válido | - | - | No | Sí | Sí |
| Guardado en curso | - | - | - | Sí | No |
| Permitir guardar | No | No | No | No | Sí |
| Mostrar error de título | Sí | No | No | No | No |
| Mostrar error de fecha | No | Sí | No | No | No |
| Mostrar error de progreso | No | No | Sí | No | No |
| Crear actividad | No | No | No | No | Sí |

## Interpretación de las reglas

### R1 - Título inválido
Si el título no cumple las reglas de validación, la actividad no puede guardarse.

**Caso relacionado:** CP-MF-006.

### R2 - Fecha inválida
Si el título es válido pero la fecha es anterior a la permitida o tiene formato incorrecto, se impide el guardado.

**Caso relacionado:** CP-MF-007.

### R3 - Progreso inválido
Si título y fecha son válidos pero el progreso está fuera del rango permitido, la actividad no puede guardarse.

**Casos relacionados:** CP-MF-004 y CP-MF-005.

### R4 - Guardado ya iniciado
Aunque los datos sean válidos, si ya existe un proceso de guardado en curso no debe permitirse una segunda creación.

**Caso relacionado:** CP-MF-008.

### R5 - Formulario completamente válido
Si todos los datos cumplen las reglas y no existe un guardado en curso, se permite crear una sola actividad.

**Casos relacionados:** CP-MF-001, CP-MF-002 y CP-MF-003.

---

# 2. Modelo de transición de estados

Para esta práctica se modela el flujo observable de navegación y creación de actividades en MiFormacionCTMA2.

| Estado actual | Evento | Estado esperado | ¿Válida? |
|---|---|---|---|
| LISTA | Pulsar "Crear actividad" | CREAR | Sí |
| CREAR | Ingresar o modificar datos | CREAR | Sí |
| CREAR | Guardar formulario válido | LISTA | Sí |
| CREAR | Volver sin guardar | LISTA | Sí |
| LISTA | Pulsar una actividad existente | DETALLE | Sí |
| DETALLE | Pulsar "Volver" | LISTA | Sí |
| LISTA | Guardar actividad directamente | LISTA | No |
| DETALLE | Intentar guardar una nueva actividad | DETALLE | No |

## Secuencia válida principal

```text
LISTA
  ↓ Crear actividad
CREAR
  ↓ Completar datos válidos
CREAR
  ↓ Guardar
LISTA