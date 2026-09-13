# Semana 4 - Plan de ejecución de pruebas

## Proyecto: MiFormacionCTMA2

## 1. Objetivo

Convertir los casos de prueba diseñados durante la Semana 3 en un plan concreto de ejecución para la Semana 4, definiendo qué se probará, cómo se observará el resultado, qué evidencia se necesita y cuáles casos harán parte de humo y regresión.

La aplicación principal continúa siendo MiFormacionCTMA2. Los flujos Android se probarán sobre la aplicación real y el requisito de autorización/API se trabajará mediante un entorno académico simulado o mock, dejando explícita esta diferencia.

---

## 2. Artefactos de entrada de Semana 3

Para esta actividad se utilizan:

- Casos de prueba v1.
- Matriz de trazabilidad.
- Datos sintéticos.
- Precondiciones.
- Técnicas de caja negra.
- Tabla de decisión.
- Modelo de transición de estados.
- Resultados de ejecución.
- Registro y reporte de defecto simulado.

---

## 3. Casos seleccionados

La guía exige seleccionar mínimo seis casos, incluyendo dos positivos, dos negativos, uno de autorización y uno de transición de estados.

| ID | Descripción | Tipo | Técnica | Clasificación |
|---|---|---|---|---|
| CP-MF-001 | Crear actividad con datos válidos | Positivo | Partición de equivalencia | ANDROID-UI |
| CP-MF-010 | Consultar detalle de una actividad existente | Positivo | Caso de uso | ANDROID-UI |
| CP-MF-005 | Intentar guardar progreso 101 | Negativo | Valores límite | ANDROID-UI |
| CP-MF-006 | Intentar guardar un título menor al mínimo | Negativo | Partición de equivalencia | ANDROID-UI |
| CP-MF-AUT-001 | Consultar actividad de otro usuario autenticado | Negativo / autorización | Prueba funcional de autorización | API / MOCK |
| CP-MF-012 | Regresar desde detalle hacia la lista | Positivo / transición | Transición de estados | ANDROID-UI |

---

## 4. Justificación de la clasificación

### ANDROID-UI

Los casos CP-MF-001, CP-MF-005, CP-MF-006, CP-MF-010 y CP-MF-012 corresponden a comportamientos observables directamente en la aplicación Android MiFormacionCTMA2.

### API / MOCK

CP-MF-AUT-001 corresponde a una práctica académica de autorización utilizando un servicio simulado o mock.

MiFormacionCTMA2 no implementa actualmente este flujo de autorización mediante una API REST real.

Por esta razón, la evidencia obtenida en este caso demostrará únicamente el comportamiento del entorno simulado utilizado en la práctica.

---

## 5. Resultado observable y datos

### CP-MF-001

**Datos:**
- Título: Estudiar Kotlin
- Descripción: Repasar funciones y colecciones
- Fecha: fecha válida
- Progreso: 50
- Prioridad: ALTA

**Resultado esperado:**

La aplicación crea una sola actividad y la muestra en la lista.

---

### CP-MF-010

**Datos:**
- Actividad existente previamente registrada.

**Resultado esperado:**

La aplicación abre la pantalla de detalle correspondiente a la actividad seleccionada.

---

### CP-MF-005

**Datos:**
- Progreso: 101

**Resultado esperado:**

La aplicación impide el guardado y muestra el mensaje de validación correspondiente.

---

### CP-MF-006

**Datos:**
- Título: AB

**Resultado esperado:**

La aplicación impide el guardado porque el título no cumple el mínimo permitido.

---

### CP-MF-AUT-001

**Datos:**
- Usuario autenticado: aprendizA
- Propietario del recurso: aprendizB
- Actividad: ACT-TEST-002
- Token: sintético
- Ambiente: API/MOCK

**Resultado esperado:**

El servicio simulado responde:

403 Forbidden

y no expone información de la actividad perteneciente a aprendizB.

---

### CP-MF-012

**Datos:**
- Actividad existente.
- Usuario ubicado en la pantalla DETALLE.

**Resultado esperado:**

Al ejecutar la acción de volver, la aplicación regresa correctamente al estado LISTA.

---

## 6. Evidencia requerida

| Caso | Evidencia prevista |
|---|---|
| CP-MF-001 | Captura de la actividad creada en la lista |
| CP-MF-010 | Captura de la pantalla de detalle |
| CP-MF-005 | Captura del mensaje de validación |
| CP-MF-006 | Captura del mensaje de validación |
| CP-MF-AUT-001 | Respuesta del mock, código HTTP y resultado de aserciones Postman |
| CP-MF-012 | Captura o bitácora de la transición DETALLE → LISTA |

---

## 7. Suite de humo

La prueba de humo debe permitir decidir rápidamente si la versión es suficientemente estable para continuar con las demás pruebas.

Se seleccionan cinco casos críticos:

| Caso | Motivo |
|---|---|
| CP-MF-001 | Comprueba que la creación principal funciona |
| CP-MF-005 | Verifica una validación crítica |
| CP-MF-010 | Comprueba acceso al detalle |
| CP-MF-012 | Comprueba navegación básica |
| CP-MF-AUT-001 | Comprueba autorización en el componente simulado |

### Decisión de continuidad

La ejecución podrá continuar si no existen fallos bloqueantes en las funciones críticas.

Los resultados se clasificarán como:

- PASS
- FAIL
- BLOCKED

---

## 8. Candidatos para regresión

Los siguientes seis casos serán candidatos para regresión:

- CP-MF-001
- CP-MF-005
- CP-MF-006
- CP-MF-010
- CP-MF-012
- CP-MF-AUT-001

La regresión permitirá comprobar que cambios posteriores no afecten:

- creación de actividades;
- validación;
- navegación;
- detalle de actividades;
- transición entre pantallas;
- autorización del servicio simulado.

---

## 9. Ambiente de ejecución

### Aplicación Android

- Proyecto: MiFormacionCTMA2
- Herramienta: Android Studio
- Plataforma: Android
- Ambiente: emulador o dispositivo de prueba
- Datos: sintéticos

### Componente API académico

- Tipo: API simulada / mock
- Herramienta prevista: Postman
- Variables:
    - baseUrl
    - token
    - activityId
    - userId
- Datos: exclusivamente sintéticos
- Secretos reales: no permitidos

---

## 10. Preparación antes de ejecutar

Antes de cada caso se debe confirmar:

1. Caso de prueba.
2. Ambiente.
3. Versión evaluada.
4. Precondiciones.
5. Datos sintéticos.
6. Resultado esperado.
7. Evidencia requerida.

---

## 11. Registro durante la ejecución

Cada ejecución deberá registrar:

- Identificador de ejecución.
- Fecha.
- Caso.
- Ambiente.
- Datos utilizados.
- Resultado real.
- Estado PASS, FAIL o BLOCKED.
- Evidencia.
- Defecto asociado, si existe.

---

## 12. Estados de ejecución

### PASS

El resultado observado coincide con el resultado esperado.

### FAIL

Existe una diferencia verificable entre el resultado esperado y el resultado real.

### BLOCKED

La prueba no puede ejecutarse debido a una condición externa documentada.

Una falta de tiempo no se considerará BLOCKED.

---

## 13. Actualización de trazabilidad

La matriz deberá conservar la relación:

Historia
→ Criterio
→ Riesgo
→ Caso
→ Ejecución
→ Resultado real
→ Evidencia
→ Defecto asociado

Durante la Semana 4 se agregarán o actualizarán las columnas:

- Ejecución.
- Resultado real.
- Evidencia.
- Defecto asociado.

---

## 14. Limitaciones

MiFormacionCTMA2 es actualmente una aplicación Android y los flujos seleccionados no utilizan una API REST real.

Por esta razón:

- Las pruebas de interfaz Android corresponden al comportamiento real del proyecto.
- La prueba de autorización se realizará en un ambiente académico simulado.
- Las evidencias del mock no se presentarán como evidencia de una funcionalidad implementada en producción.
- No se utilizarán datos personales, tokens ni cuentas reales.

---

## 15. Resultado esperado del plan

Al finalizar la ejecución se espera contar con:

- seis casos trazables;
- resultados PASS, FAIL o BLOCKED;
- evidencias identificadas;
- una suite de humo;
- candidatos de regresión;
- una prueba de autorización mediante mock;
- actualización de la matriz de trazabilidad;
- información suficiente para construir la bitácora de Semana 4.

---

## 16. Conclusión

El plan permite pasar del diseño de pruebas de la Semana 3 a una ejecución controlada durante la Semana 4.

La adaptación mantiene a MiFormacionCTMA2 como proyecto principal y utiliza simulación únicamente donde la guía exige componentes API que todavía no existen en la aplicación Android.