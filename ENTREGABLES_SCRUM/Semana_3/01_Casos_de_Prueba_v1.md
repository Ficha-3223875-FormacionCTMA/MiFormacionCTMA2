# Semana 3 - Casos de prueba v1

**Producto:** Mi Formación CTMA  
**Base:** HU-MFC-01, HU-MFC-02 y HU-MFC-03  
**Datos:** sintéticos.

| ID | Ref. | Técnica | Tipo | Precondición | Datos | Resultado esperado | Prioridad |
|---|---|---|---|---|---|---|---|
| CP-MFC-001 | HU-MFC-01/CA-01/RSK-MFC-03 | Caso de uso | Positiva | App iniciada con actividades | 10 actividades | Se muestran las actividades con sus datos principales | Alta |
| CP-MFC-002 | HU-MFC-01/CA-02/RSK-MFC-05 | Equivalencia | Positiva | Lista con actividades | Progresos 100,80,40,20,0 | Cantidad y promedio coinciden con los datos | Media |
| CP-MFC-003 | HU-MFC-01/CA-03/RSK-MFC-02 | Transición de estados | Positiva | Actividad disponible | Progreso 100 | Estado `COMPLETADA` | Alta |
| CP-MFC-004 | HU-MFC-02/CA-04/RSK-MFC-01 | Valores límite | Positiva | Actividad creada | Progreso 0 | Se acepta y muestra 0% | Alta |
| CP-MFC-005 | HU-MFC-02/CA-04/RSK-MFC-01 | Valores límite | Positiva | Actividad creada | Progreso 100 | Se acepta y muestra 100% | Alta |
| CP-MFC-006 | HU-MFC-02/CA-04/RSK-MFC-01 | Partición | Negativa | Validación disponible | Progreso 101 | Se rechaza como valor fuera de rango | Alta |
| CP-MFC-007 | HU-MFC-02/CA-04/RSK-MFC-01 | Partición | Negativa | Validación disponible | Progreso -1 | Se rechaza como valor fuera de rango | Alta |
| CP-MFC-008 | HU-MFC-02/CA-05/RSK-MFC-02 | Partición | Positiva | Actividad disponible | Progreso 20; días -1 | Estado `VENCIDA` | Alta |
| CP-MFC-009 | HU-MFC-02/CA-06/RSK-MFC-02 | Partición | Positiva | Actividad disponible | Progreso 40; días 3 | Estado `EN CURSO` | Media |
| CP-MFC-010 | HU-MFC-03/CA-07/RSK-MFC-04 | Caso de uso | Positiva | Tarjeta visible | Actividad ID 4 | Callback recibe la actividad ID 4 | Media |
| CP-MFC-011 | HU-MFC-03/CA-08/RSK-MFC-04 | Caso de uso | Positiva | Tarjeta visible | Título `Entregar evidencia` | Se informa el título seleccionado | Media |
| CP-MFC-012 | HU-MFC-03/CA-09/RSK-MFC-04 | Regresión | Negativa | Actividad visible | Seleccionar actividad | La selección no modifica progreso ni días restantes | Media |

## Casos adicionales derivados de técnicas

- Particiones de progreso: inválido inferior, válido y válido superior.
- Límites: 0 y 100; vecinos -1 y 101.
- Estados: PENDIENTE → EN CURSO → COMPLETADA y escenarios de vencimiento.
- Caso de uso: flujo de consulta y selección de actividad.
