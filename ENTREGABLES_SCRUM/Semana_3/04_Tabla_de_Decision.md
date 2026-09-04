# Semana 3 - Tabla de decisión

## Decisión: determinar estado de una actividad

| Condición / Acción | R1 | R2 | R3 | R4 |
|---|---:|---:|---:|---:|
| Progreso = 100 | Sí | No | No | No |
| Días restantes < 0 | - | Sí | No | No |
| Progreso > 0 | - | - | Sí | No |
| Estado esperado | COMPLETADA | VENCIDA | EN CURSO | PENDIENTE |

### Casos derivados

- **R1 → CP-MFC-003:** progreso 100.
- **R2 → CP-MFC-008:** progreso 20, días -1.
- **R3 → CP-MFC-009:** progreso 40, días 3.
- **R4:** progreso 0 y días no negativos; debe quedar pendiente.
