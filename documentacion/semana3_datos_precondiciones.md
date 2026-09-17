# Datos de prueba y precondiciones - Semana 3

## Proyecto: MiFormacionCTMA2

Este documento define los datos sintéticos y las precondiciones necesarias para ejecutar de manera reproducible los casos de prueba diseñados para la Semana 3.

---

# 1. Datos sintéticos de prueba

Todos los datos utilizados en las pruebas son ficticios y no corresponden a información personal real.

## Datos válidos

### Actividad válida A

- ID esperado: generado por la aplicación
- Título: Estudiar Kotlin
- Descripción: Repasar funciones y colecciones
- Fecha: fecha futura válida
- Progreso: 50
- Prioridad: ALTA

### Actividad válida B

- Título: Preparar exposición
- Descripción: Revisar presentación de Scrum
- Fecha: fecha actual o futura permitida
- Progreso: 0
- Prioridad: MEDIA

### Actividad válida C

- Título: Finalizar actividad
- Descripción: Completar todos los puntos pendientes
- Fecha: fecha futura válida
- Progreso: 100
- Prioridad: BAJA

---

# 2. Datos inválidos y valores límite

## Título

- Valor inválido inferior: `AB`
- Valor válido mínimo: `ABC`
- Valor válido normal: `Estudiar Kotlin`
- Valor inválido superior: título con más de 80 caracteres

## Progreso

- Valor inválido inferior: `-1`
- Límite mínimo válido: `0`
- Valor válido intermedio: `50`
- Límite máximo válido: `100`
- Valor inválido superior: `101`

## Fecha

- Fecha válida: fecha actual o futura permitida por la aplicación
- Fecha inválida: fecha anterior al día actual
- Formato válido: `yyyy-MM-dd`

## Descripción

- Descripción válida: texto breve menor al máximo permitido
- Descripción opcional: campo vacío
- Descripción inválida: texto superior a 240 caracteres

## Identificador de actividad

- ID válido: identificador correspondiente a una actividad existente
- ID inválido: `999`

---

# 3. Precondiciones generales

Para ejecutar los casos de prueba se deben cumplir las siguientes condiciones:

1. La aplicación MiFormacionCTMA2 debe iniciar correctamente.
2. El emulador Android debe estar funcionando.
3. La aplicación debe mostrar la pantalla de lista de actividades.
4. Las funcionalidades de navegación deben estar disponibles.
5. El usuario debe poder acceder al formulario de creación.
6. Las validaciones del formulario deben estar activas.
7. Para las pruebas de detalle debe existir al menos una actividad en la lista.
8. Para las pruebas de recreación debe ser posible girar o recrear la Activity.
9. Los datos utilizados deben ser sintéticos.
10. No se deben utilizar datos personales reales.

---

# 4. Precondiciones por caso de prueba

| Caso | Precondición principal |
|---|---|
| CP-MF-001 | Usuario ubicado en la pantalla Crear actividad. |
| CP-MF-002 | Formulario abierto y demás campos válidos. |
| CP-MF-003 | Formulario abierto y demás campos válidos. |
| CP-MF-004 | Formulario abierto y demás campos válidos excepto progreso. |
| CP-MF-005 | Formulario abierto y demás campos válidos excepto progreso. |
| CP-MF-006 | Formulario abierto y demás campos válidos excepto título. |
| CP-MF-007 | Formulario abierto y demás campos válidos excepto fecha. |
| CP-MF-008 | Formulario completamente válido antes de pulsar Guardar. |
| CP-MF-009 | Formulario abierto con datos ingresados pero aún no guardados. |
| CP-MF-010 | Existe al menos una actividad visible en la lista. |
| CP-MF-011 | Navegación configurada y se intenta consultar un ID inexistente. |
| CP-MF-012 | Usuario ubicado en la pantalla de detalle de una actividad existente. |

---

# 5. Reglas de prueba relacionadas

Las pruebas se diseñaron teniendo en cuenta las siguientes reglas del proyecto:

- El título debe cumplir una longitud mínima y máxima permitida.
- El progreso debe estar entre 0 y 100.
- La fecha no debe ser anterior a la fecha permitida por la aplicación.
- Una actividad válida debe crearse una sola vez.
- Un identificador inexistente debe producir un estado controlado.
- La navegación debe regresar al destino anterior sin duplicar pantallas.
- Los datos simples del formulario deben conservarse durante la recreación de la Activity mediante el mecanismo implementado en la aplicación.

---

# 6. Relación con técnicas de prueba

Los datos anteriores permiten aplicar:

- Partición de equivalencia.
- Análisis de valores límite.
- Tabla de decisión.
- Transición de estados.
- Escenarios positivos y negativos.
- Casos derivados del flujo de uso.

Estos datos permitirán posteriormente ejecutar los casos de forma repetible y decidir claramente si el resultado es PASS o FAIL.