# Semana 3 - Ticket de salida

## 1. ¿Por qué probar los valores límite?

Porque los errores suelen aparecer en los bordes de un rango. En Mi Formación CTMA se deben comprobar 0 y 100, además de los valores inmediatamente cercanos -1 y 101.

## 2. ¿Cuándo usar una tabla de decisión?

Cuando el resultado depende de varias condiciones que pueden combinarse, como el progreso, los días restantes y las reglas que determinan el estado.

## 3. Ejemplo de severidad baja y prioridad alta

Un problema visual puede tener severidad baja, pero si aparece en una pantalla utilizada durante una presentación o entrega inmediata puede recibir prioridad alta por contexto.

## 4. ¿Qué necesita otra persona para reproducir un defecto?

Identificador, título, ambiente, versión, precondiciones, datos, pasos, resultado esperado, resultado real, severidad, prioridad y evidencia.

## 5. ¿Cómo se llega de historia a defecto?

Historia → criterio de aceptación → riesgo → caso de prueba → resultado → defecto.

## Mejora propuesta

Agregar pruebas automatizadas para las reglas de progreso y estado, especialmente los límites y las transiciones inválidas.
