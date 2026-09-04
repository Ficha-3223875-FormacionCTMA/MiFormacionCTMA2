# Semana 3 - Transición de estados

## Modelo simplificado

| Estado actual | Evento/condición | Estado esperado | Válida |
|---|---|---|---|
| PENDIENTE | progreso pasa a >0 | EN CURSO | Sí |
| EN CURSO | progreso llega a 100 | COMPLETADA | Sí |
| PENDIENTE | días restantes se vuelven negativos | VENCIDA | Sí |
| COMPLETADA | progreso pasa a 99 | No debe abandonar COMPLETADA sin regla explícita | No |
| VENCIDA | se consulta sin corregir la fecha/progreso | VENCIDA | Sí |
| COMPLETADA | marcar como completada otra vez | COMPLETADA | No como nueva transición |

## Secuencia válida

`PENDIENTE → EN CURSO → COMPLETADA`

## Secuencia alterna

`PENDIENTE → VENCIDA`

## Transiciones inválidas a verificar

1. `COMPLETADA → EN CURSO` sin una acción de reapertura definida.
2. `COMPLETADA → VENCIDA` únicamente por cambio de días sin una regla de negocio que permita esa transición.
