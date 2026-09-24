# Informe de cierre de automatización — Semana 8

## 1. Propósito

Consolidar el trabajo realizado durante la Semana 8 relacionado con selección, implementación, ejecución y trazabilidad de pruebas automatizadas del proyecto Mi Formación CTMA.

---

## 2. Trabajo realizado

Durante la semana se recuperaron casos definidos en semanas anteriores y se analizaron como candidatos para automatización.

La selección priorizó pruebas:

- repetibles;
- deterministas;
- con entradas controladas;
- con resultados verificables;
- útiles para regresión.

Las pruebas visuales y exploratorias continuaron siendo manuales cuando la automatización no aportaba suficiente valor.

---

## 3. Automatización disponible

Se identificó cobertura automatizada sobre:

- creación y validación de actividades;
- reglas de límites de progreso;
- transición de estados;
- acceso al repositorio mediante dobles de prueba;
- consulta de servicios mediante Retrofit;
- respuestas HTTP mediante MockWebServer;
- escenarios de error HTTP.

---

## 4. Resultado de ejecución

La suite de pruebas unitarias fue ejecutada mediante Gradle.

Comando:

`./gradlew.bat :app:testDevDebugUnitTest`

Resultado:

`BUILD SUCCESSFUL`

Los casos automatizados implementados no presentaron fallos durante esa ejecución.

---

## 5. Cobertura de los candidatos

| Estado | Cantidad |
|---|---:|
| Cubiertos | 4 |
| Parcialmente cubiertos | 1 |
| Pendientes por funcionalidad/API | 1 |
| Manuales | 1 |

CP-02 se mantiene parcialmente cubierto hasta incorporar el escenario exacto de título vacío.

CP-09 permanece pendiente porque la API actualmente disponible no contiene una operación adecuada para enviar datos inválidos de creación.

---

## 6. Defectos y hallazgos

No se detectaron defectos funcionales en los casos automatizados que fueron ejecutados correctamente.

Se identificaron dos brechas de cobertura:

- falta una prueba específica para título vacío;
- falta disponibilidad de un endpoint compatible con CP-09.

Estas brechas se documentan como pendientes de automatización y no como resultados aprobados.

---

## 7. Valor para regresión

Las pruebas implementadas pueden volver a ejecutarse después de cambios en el proyecto y permiten detectar regresiones en:

- reglas de validación;
- límites del progreso;
- cálculo de estados;
- persistencia mediante repositorio;
- interpretación de respuestas HTTP.

Esto convierte la suite en una base de regresión reutilizable.

---

## 8. Conclusión

La Semana 8 permitió pasar de una selección teórica de candidatos a una revisión de la cobertura automatizada existente y a una ejecución real de la suite.

Los resultados quedaron documentados manteniendo trazabilidad entre caso, riesgo, prueba y resultado.

No se registraron como aprobados casos que no pudieron ejecutarse realmente.