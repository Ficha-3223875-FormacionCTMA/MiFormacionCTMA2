# MiFormacionCTMA2

Aplicación móvil desarrollada en **Android Studio** con **Kotlin** y **Jetpack Compose**, orientada a la organización, consulta y seguimiento de actividades de formación de los aprendices del CTMA.

## 📱 Descripción del proyecto

**Mi Formación CTMA** es una aplicación móvil desarrollada como parte del proceso de formación en **Análisis y Desarrollo de Software (ADSO)**.

El proyecto permite visualizar actividades formativas, consultar su progreso, conocer su estado y organizar la información de manera sencilla mediante una interfaz desarrollada con Jetpack Compose.

El proyecto se ha desarrollado progresivamente durante las diferentes semanas de formación, incorporando funcionalidades de Android, además de documentación y actividades relacionadas con **SCRUM y Pruebas de Software**.

---

# 🚀 Tecnologías utilizadas

* Kotlin
* Android Studio
* Jetpack Compose
* Material 3
* Git
* GitHub
* SCRUM
* Pruebas de Software

---

# 📂 Estructura del proyecto

```text
MiFormacionCTMA2/
│
├── app/
│   └── src/
│       └── main/
│           └── java/
│               └── com/
│                   └── sofia/
│                       └── miformacionctma/
│                           ├── MainActivity.kt
│                           │
│                           ├── domain/
│                           │
│                           ├── ui/
│                           │   ├── components/
│                           │   │   └── TarjetaActividad.kt
│                           │   │
│                           │   ├── screens/
│                           │   │   └── PantallaActividades.kt
│                           │   │
│                           │   └── theme/
│                           │
│                           └── ...
│
├── ENTREGABLES_SCRUM/
│   ├── Semana_2/
│   ├── Semana_3/
│   └── README_SCRUM.md
│
└── README.md
```

---

# 📚 Semanas de Android

## 🟢 Semana 1 - Introducción al desarrollo Android

Durante la Semana 1 se inició el desarrollo de la aplicación **Mi Formación CTMA** utilizando Android Studio.

### Actividades realizadas

* Creación y configuración del proyecto Android.
* Configuración del entorno de desarrollo.
* Uso de Kotlin.
* Creación de la actividad principal.
* Ejecución de la aplicación en el emulador.
* Reconocimiento de la estructura de un proyecto Android.
* Inicio de la interfaz de usuario.
* Uso inicial de Jetpack Compose.

### Resultado

Se obtuvo la estructura inicial de la aplicación y una primera versión funcional ejecutándose en el emulador de Android.

---

# 🔵 Semana 2 - Desarrollo de la interfaz

Durante la Semana 2 se continuó con el desarrollo de la interfaz de **Mi Formación CTMA** utilizando Jetpack Compose.

### Actividades realizadas

* Construcción de componentes de interfaz.
* Organización del código por paquetes.
* Creación de pantallas.
* Uso de componentes reutilizables.
* Implementación de elementos visuales para mostrar información de las actividades.
* Organización de la información de cada actividad.
* Pruebas de ejecución en el emulador.

### Resultado

La aplicación comenzó a contar con una interfaz organizada para mostrar las actividades de formación.

---

# 🟣 Semana 3 - Componentes y pantallas

Durante la Semana 3 se implementaron nuevos componentes y pantallas para mejorar la estructura y presentación de la aplicación.

### Componentes desarrollados

* `TarjetaActividad.kt`
* `PantallaActividades.kt`

### Actividades realizadas

* Creación de tarjetas para mostrar actividades.
* Visualización del título de cada actividad.
* Visualización de la descripción.
* Visualización del progreso.
* Visualización del estado.
* Visualización de días restantes.
* Visualización de prioridad.
* Organización de las actividades en pantalla.
* Implementación de una interfaz adaptable.
* Pruebas en el emulador.

### Resultado

La aplicación cuenta con una pantalla funcional para consultar las actividades de formación y visualizar información relacionada con su progreso y estado.

---

# 🧪 SCRUM Y PRUEBAS DE SOFTWARE

Además del desarrollo Android, el proyecto incluye los productos correspondientes a las actividades de **SCRUM y Pruebas de Software**.

Los entregables se encuentran dentro de:

```text
ENTREGABLES_SCRUM/
```

---

# 🟠 Semana 2 - SCRUM y Pruebas de Software

Durante la Semana 2 se trabajó en la definición de los requisitos, criterios de aceptación, planificación de pruebas y gestión inicial de riesgos.

## Historias de usuario

Se definieron historias de usuario relacionadas con las principales funcionalidades de la aplicación:

* Consultar actividades.
* Consultar estado y progreso.
* Seleccionar una actividad.

Cada historia cuenta con sus respectivos criterios de aceptación.

## Criterios de aceptación

Se establecieron criterios para determinar cuándo una funcionalidad cumple con lo esperado.

También se definieron criterios relacionados con aspectos no funcionales, como el comportamiento y rendimiento esperado de la aplicación.

## Product Backlog

Se organizó un Product Backlog con las funcionalidades principales del proyecto, estableciendo prioridades para su desarrollo.

## Definition of Ready

Se establecieron condiciones para determinar cuándo una historia de usuario cuenta con la información necesaria para ser trabajada.

## Definition of Done

Se establecieron condiciones para considerar terminada una historia de usuario, incluyendo desarrollo, revisión y pruebas.

## Matriz de riesgos

Se identificaron riesgos relacionados con:

* Funcionamiento de la aplicación.
* Validación de datos.
* Interfaz de usuario.
* Pruebas.
* Integración.
* Cumplimiento de requisitos.

Cada riesgo cuenta con una respuesta o estrategia de tratamiento.

## Plan de pruebas

Se elaboró el **Plan de Pruebas versión 1**, donde se definieron:

* Objetivo.
* Alcance.
* Fuera de alcance.
* Base de pruebas.
* Riesgos.
* Estrategia.
* Ambiente de pruebas.
* Datos de prueba.
* Roles.
* Criterios de entrada y salida.
* Entregables.
* Cronograma.

## Matriz de trazabilidad

Se inició la trazabilidad entre:

```text
Historia de usuario
        ↓
Criterio de aceptación
        ↓
Riesgo
        ↓
Caso de prueba
```

## Revisión entre pares

Se incluyó una revisión de los productos elaborados para identificar posibles mejoras, inconsistencias o elementos pendientes.

---

# 🔴 Semana 3 - SCRUM y Pruebas de Software

Durante la Semana 3 se transformaron los requisitos definidos anteriormente en casos de prueba y técnicas de diseño de pruebas.

## Casos de prueba

Se diseñaron casos de prueba para comprobar diferentes comportamientos de la aplicación.

Se incluyeron:

* Pruebas positivas.
* Pruebas negativas.
* Pruebas de valores límite.
* Pruebas de partición de equivalencia.
* Pruebas de transición de estados.
* Escenarios de caso de uso.
* Pruebas de regresión.

## Partición de equivalencia

Se analizaron diferentes grupos de valores para el progreso de una actividad:

* Valores menores que 0.
* Valores entre 0 y 100.
* Valores mayores que 100.

Esto permite identificar entradas válidas e inválidas.

## Valores límite

Se definieron valores cercanos a los límites para realizar las pruebas:

```text
-1
0
1
99
100
101
```

Estos valores permiten comprobar el comportamiento de las reglas de validación.

## Tabla de decisión

Se elaboró una tabla de decisión para analizar los diferentes estados de una actividad:

* Pendiente.
* En curso.
* Completada.
* Vencida.

## Transición de estados

Se documentaron posibles cambios de estado de las actividades.

Ejemplo:

```text
PENDIENTE
    ↓
EN CURSO
    ↓
COMPLETADA
```

También se analizaron situaciones relacionadas con actividades vencidas.

## Casos de uso

Se documentó un caso de uso relacionado con la consulta y selección de actividades.

## Datos de prueba

Se definieron datos ficticios para realizar las diferentes pruebas de la aplicación.

## Gestión de defectos

Se documentó el proceso para registrar defectos, incluyendo:

* Identificación.
* Descripción.
* Pasos para reproducir.
* Resultado esperado.
* Resultado obtenido.
* Severidad.
* Prioridad.
* Evidencia.
* Estado.

## Ejecución de pruebas

Se preparó una ejecución de pruebas y un registro de resultados para documentar el comportamiento esperado de los casos de prueba.

> **Nota:** Los resultados marcados como simulados en los entregables deben reemplazarse por resultados reales y evidencias cuando las pruebas se ejecuten directamente en Android Studio.

## Trazabilidad actualizada

Se actualizó la relación:

```text
Historia de usuario
        ↓
Criterio de aceptación
        ↓
Riesgo
        ↓
Caso de prueba
        ↓
Resultado
        ↓
Defecto
```

## Ticket de salida

Se documentaron los conocimientos y actividades realizadas durante la Semana 3, incluyendo:

* Valores límite.
* Partición de equivalencia.
* Tabla de decisión.
* Transición de estados.
* Casos de prueba.
* Gestión de defectos.
* Trazabilidad.

---

# 📊 Funcionalidades actuales de la aplicación

La aplicación permite:

* 📋 Visualizar actividades formativas.
* 📈 Consultar el progreso.
* 🟢 Identificar actividades completadas.
* 🟡 Identificar actividades en curso.
* 🔴 Identificar actividades vencidas.
* ⚪ Identificar actividades pendientes.
* 📅 Consultar días restantes.
* ⭐ Identificar prioridad.
* 🔎 Consultar información de las actividades.
* 📊 Calcular el promedio de progreso.
* 📱 Visualizar las actividades mediante una interfaz desarrollada con Jetpack Compose.

---

# 🔗 Trazabilidad del proyecto

Los productos de SCRUM y Pruebas de Software mantienen una relación entre los requisitos y las pruebas:

```text
Requisito
   ↓
Historia de usuario
   ↓
Criterio de aceptación
   ↓
Riesgo
   ↓
Caso de prueba
   ↓
Resultado
   ↓
Defecto
```

Esta trazabilidad permite comprobar que las funcionalidades definidas tengan pruebas asociadas.

---

# 🧑‍💻 Control de versiones

El proyecto utiliza **Git y GitHub** para controlar los cambios realizados durante el desarrollo.

Se utilizan ramas para organizar el trabajo y mantener separados los cambios realizados durante el proceso de desarrollo.

---

# 📁 Entregables SCRUM

Los documentos correspondientes a las Semanas 2 y 3 se encuentran en:

```text
ENTREGABLES_SCRUM/
```

### Semana 2

* Historias de usuario.
* Criterios de aceptación.
* Product Backlog.
* Definition of Ready.
* Definition of Done.
* Matriz de riesgos.
* Plan de pruebas v1.
* Matriz de trazabilidad v1.
* Revisión entre pares.

### Semana 3

* Casos de prueba.
* Partición de equivalencia.
* Valores límite.
* Tabla de decisión.
* Transición de estados.
* Caso de uso.
* Datos de prueba.
* Ejecución simulada.
* Registro de defectos.
* Reporte de defecto.
* Matriz de trazabilidad actualizada.
* Ticket de salida.
* Registro de cambios.

---

# 📌 Estado del proyecto

**Mi Formación CTMA** se encuentra en desarrollo académico como parte del programa de formación **ADSO**.

Actualmente el proyecto cuenta con:

* Desarrollo Android de las Semanas 1, 2 y 3.
* Interfaz desarrollada con Kotlin y Jetpack Compose.
* Componentes reutilizables.
* Pantallas para consultar actividades.
* Reglas para gestionar estados y progreso.
* Documentación de SCRUM de las Semanas 2 y 3.
* Planificación de pruebas.
* Casos de prueba.
* Matrices de trazabilidad.
* Gestión y documentación de defectos.

---

# 👩‍💻 Proyecto académico

**Proyecto:** Mi Formación CTMA
**Tecnología:** Kotlin + Jetpack Compose
**Entorno:** Android Studio
**Metodología:** SCRUM
**Área:** Desarrollo de Software y Pruebas de Software
**Programa:** Análisis y Desarrollo de Software (ADSO)

---
