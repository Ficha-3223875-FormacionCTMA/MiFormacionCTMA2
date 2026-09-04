# Semana 2 - Matriz de riesgos

**Escala:** Probabilidad e impacto de 1 a 5.  
**Exposición = Probabilidad × Impacto.**

| ID | Riesgo | Prob. | Impacto | Exposición | Prioridad | Respuesta |
|---|---|---:|---:|---:|---|---|
| RSK-MFC-01 | Progreso fuera del rango 0-100 | 3 | 4 | 12 | Alta | Validar entrada y probar límites |
| RSK-MFC-02 | Estado incorrecto de una actividad vencida/completada | 3 | 4 | 12 | Alta | Pruebas de reglas y valores límite |
| RSK-MFC-03 | Lista de actividades no visible al iniciar | 2 | 5 | 10 | Alta | Prueba funcional y de interfaz |
| RSK-MFC-04 | Selección de actividad entrega información incorrecta | 2 | 4 | 8 | Media | Prueba de interacción y trazabilidad |
| RSK-MFC-05 | Resumen muestra promedio incorrecto | 2 | 3 | 6 | Media | Pruebas unitarias con listas conocidas |
| RSK-MFC-06 | Diferencias visuales entre tamaños de pantalla | 2 | 2 | 4 | Baja | Prueba responsive en emulador/dispositivo |

**Priorización:** se prueban primero las reglas que afectan el estado, el progreso y la disponibilidad de la información principal.
