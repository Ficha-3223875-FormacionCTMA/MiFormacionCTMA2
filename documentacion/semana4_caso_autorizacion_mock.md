# Semana 4 - Caso de autorización con API simulada

## Proyecto: MiFormacionCTMA2

> **IMPORTANTE:** Este caso corresponde a una práctica académica con API simulada o mock.
> No representa una funcionalidad de autenticación o autorización actualmente implementada en MiFormacionCTMA2.

---

# 1. Objetivo

Diseñar y ejecutar un caso de prueba de autorización para cumplir el requisito de la Semana 4 relacionado con pruebas WEB/API, diferenciando correctamente autenticación y autorización.

La ejecución se realizará mediante un servicio simulado o mock, sin afirmar que MiFormacionCTMA2 dispone actualmente de una API REST real.

---

# 2. Identificador del caso

**CP-MF-AUT-001**

---

# 3. Nombre del caso

Intento de consulta de una actividad perteneciente a otro usuario autenticado.

---

# 4. Clasificación

**API / MOCK**

---

# 5. Tipo de prueba

**Negativa**

---

# 6. Técnica

Prueba funcional de autorización.

---

# 7. Historia relacionada

**HU-MF-02 - Consultar detalle de una actividad**

Como aprendiz, quiero consultar el detalle de una actividad para revisar su información y regresar posteriormente a la lista.

---

# 8. Criterio académico de autorización

Dado que un usuario se encuentra autenticado, cuando intenta consultar un recurso que pertenece a otro usuario, entonces el sistema debe impedir el acceso y no debe exponer información del recurso.

Este criterio pertenece al escenario académico de API simulada utilizado durante la Semana 4.

No corresponde a un comportamiento actualmente implementado en la aplicación Android MiFormacionCTMA2.

---

# 9. Riesgo asociado

**RSK-MF-AUT-01 - Acceso no autorizado a información de otro usuario**

Existe el riesgo de que un usuario autenticado pueda consultar información asociada a otro usuario si el servicio no valida correctamente la autorización sobre el recurso solicitado.

**Prioridad: Alta**

---

# 10. Precondiciones

1. Existe un servicio API simulado o mock disponible.
2. Existe un usuario de prueba autenticado denominado `aprendizA`.
3. Existe otro usuario sintético denominado `aprendizB`.
4. Existe una actividad sintética asociada a `aprendizB`.
5. El usuario `aprendizA` dispone de un token de prueba válido.
6. Los datos utilizados son exclusivamente sintéticos.

---

# 11. Datos sintéticos

## Usuario autenticado

```text
aprendizA