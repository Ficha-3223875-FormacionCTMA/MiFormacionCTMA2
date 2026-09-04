# Semana 2 - Plan de pruebas v1

## 1. Identificación

- **Producto:** Mi Formación CTMA
- **Versión:** 1.0
- **Alcance de la iteración:** consulta y visualización de actividades formativas.
- **Responsable:** Equipo del proyecto.
- **Fecha:** 04/09/2026.

## 2. Objetivo

Comprobar que las actividades se visualicen correctamente, que las reglas de estado y progreso produzcan resultados coherentes y que la selección de una actividad conserve la información correcta.

## 3. Alcance incluido

- Lista de actividades.
- Tarjetas de actividad.
- Título y descripción.
- Estado.
- Días restantes.
- Porcentaje de progreso.
- Prioridad.
- Resumen de cantidad y promedio.
- Selección de una actividad.

## 4. Fuera de alcance

- Autenticación real.
- Persistencia en servidor.
- Publicación real por parte de un instructor.
- Sincronización con servicios externos.
- Pruebas de carga masiva.

## 5. Base de prueba

- Historias HU-MFC-01, HU-MFC-02 y HU-MFC-03.
- Criterios CA-01 a CA-09.
- Reglas de `ReglasActividad.kt`.
- Modelo `ActividadFormativa`.
- Interfaz de `PantallaActividades` y `TarjetaActividad`.

## 6. Riesgos

Se priorizan RSK-MFC-01, RSK-MFC-02 y RSK-MFC-03 por su impacto sobre la información principal del aprendiz.

## 7. Enfoque

- Pruebas unitarias para reglas de negocio.
- Pruebas funcionales de interfaz.
- Pruebas positivas y negativas.
- Partición de equivalencia.
- Valores límite.
- Tabla de decisión.
- Transición de estados.
- Pruebas de regresión después de cambios.

## 8. Ambiente y datos

- Android Studio.
- Emulador Android o dispositivo de prueba.
- Datos sintéticos de actividades.
- Sin información personal real.

## 9. Roles

- Análisis/diseño: equipo de desarrollo.
- Ejecución: tester/integrante asignado.
- Corrección: developer.
- Aprobación: responsable funcional/Product Owner.

## 10. Criterios

### Entrada
Historias y criterios revisados, aplicación compilable, datos de prueba disponibles y versión identificada.

### Suspensión
Aplicación no inicia, bloqueo que impida ejecutar más del 30% de los casos o ambiente inestable.

### Reanudación
Corrección disponible, aplicación inicia y smoke test aprobado.

### Salida
Todos los casos críticos ejecutados, sin defectos críticos abiertos y resultados registrados.

## 11. Entregables

- Casos de prueba v1.
- Matriz de trazabilidad.
- Registro de defectos.
- Evidencias de ejecución.
- Reporte de defecto.

## 12. Cronograma

- Diseño: Semana 2.
- Revisión y ajustes: cierre de Semana 2.
- Diseño detallado y ejecución simulada: Semana 3.
