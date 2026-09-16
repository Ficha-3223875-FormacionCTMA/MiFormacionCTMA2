MiFormacionCTMA2



svg



Aplicación móvil desarrollada en Android Studio con Kotlin y Jetpack Compose, orientada a la organización, consulta y seguimiento de actividades de formación de los aprendices del CTMA.



📱 Descripción del proyecto



svg



Mi Formación CTMA es una aplicación móvil desarrollada como parte del proceso de formación en Análisis y Desarrollo de Software (ADSO).



El proyecto permite visualizar actividades formativas, consultar su progreso, conocer su estado y organizar la información de manera sencilla mediante una interfaz desarrollada con Jetpack Compose.



El proyecto se ha desarrollado progresivamente durante las diferentes semanas de formación, incorporando funcionalidades de Android, además de documentación y actividades relacionadas con SCRUM y Pruebas de Software.



🚀 Tecnologías utilizadas



svg



Kotlin



Android Studio



Jetpack Compose



Material 3



Git



GitHub



SCRUM



Pruebas de Software



📂 Estructura del proyecto



svg



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

├── ENTREGABLES\_SCRUM/

│   ├── Semana\_2/

│   ├── Semana\_3/

│   └── README\_SCRUM.md

│

└── README.md





svg



📚 Semanas de Android



svg



🟢 Semana 1 - Introducción al desarrollo Android



svg



Durante la Semana 1 se inició el desarrollo de la aplicación Mi Formación CTMA utilizando Android Studio.



Actividades realizadas



svg



Creación y configuración del proyecto Android.



Configuración del entorno de desarrollo.



Uso de Kotlin.



Creación de la actividad principal.



Ejecución de la aplicación en el emulador.



Reconocimiento de la estructura de un proyecto Android.



Inicio de la interfaz de usuario.



Uso inicial de Jetpack Compose.



Resultado



svg



Se obtuvo la estructura inicial de la aplicación y una primera versión funcional ejecutándose en el emulador de Android.



🔵 Semana 2 - Desarrollo de la interfaz



svg



Durante la Semana 2 se continuó con el desarrollo de la interfaz de Mi Formación CTMA utilizando Jetpack Compose.



Actividades realizadas



svg



Construcción de componentes de interfaz.



Organización del código por paquetes.



Creación de pantallas.



Uso de componentes reutilizables.



Implementación de elementos visuales para mostrar información de las actividades.



Organización de la información de cada actividad.



Pruebas de ejecución en el emulador.



Resultado



svg



La aplicación comenzó a contar con una interfaz organizada para mostrar las actividades de formación.



🟣 Semana 3 - Componentes y pantallas



svg



Durante la Semana 3 se implementaron nuevos componentes y pantallas para mejorar la estructura y presentación de la aplicación.



Componentes desarrollados



svg



TarjetaActividad.kt



PantallaActividades.kt



Actividades realizadas



svg



Creación de tarjetas para mostrar actividades.



Visualización del título de cada actividad.



Visualización de la descripción.



Visualización del progreso.



Visualización del estado.



Visualización de días restantes.



Visualización de prioridad.



Organización de las actividades en pantalla.



Implementación de una interfaz adaptable.



Pruebas en el emulador.



Resultado



svg



La aplicación cuenta con una pantalla funcional para consultar las actividades de formación y visualizar información relacionada con su progreso y estado.



🟡 Semana 4 - Estado, formularios y navegación



Durante la Semana 4 se amplió la aplicación Mi Formación CTMA incorporando manejo de estado, formularios con validaciones y navegación entre las principales pantallas de la aplicación.



Actividades realizadas



Implementación de FormularioActividadUiState para centralizar el estado del formulario.



Manejo del estado de título, descripción, fecha, prioridad y progreso.



Validación del título entre 3 y 80 caracteres.



Validación de la descripción con máximo de 240 caracteres.



Validación de fechas con formato AAAA-MM-DD.



Validación para impedir fechas anteriores al día actual.



Validación del progreso entre 0 y 100.



Habilitación de Guardar actividad únicamente cuando los datos son válidos.



Uso de rememberSaveable para conservar el estado del formulario.



Implementación de ActividadesViewModel para administrar la lista de actividades.



Creación de nuevas actividades desde el formulario.



Cálculo de los días restantes de una actividad.



Implementación de la pantalla de detalle de una actividad.



Implementación de navegación con NavHost y NavController.



Navegación Lista → Nueva actividad → Lista.



Navegación Lista → Detalle → Atrás → Lista.



Uso del identificador de la actividad para acceder al detalle.



Prevención del guardado cuando el formulario no cumple las reglas.



Implementación de pruebas unitarias para las validaciones principales.



Componentes agregados o modificados



MainActivity.kt



ActividadesViewModel.kt



FormularioActividad.kt



FormularioActividadUiState.kt



DetalleActividadScreen.kt



PantallaActividades.kt



FormularioActividadTest.kt



Resultado



La aplicación ahora permite consultar las actividades existentes, abrir su detalle, crear nuevas actividades mediante un formulario, validar los datos ingresados y regresar a la lista después de guardar.



La funcionalidad mantiene la organización del proyecto y continúa utilizando Kotlin, Jetpack Compose y Material 3.



🧪 SCRUM Y PRUEBAS DE SOFTWARE



svg



Además del desarrollo Android, el proyecto incluye los productos correspondientes a las actividades de SCRUM y Pruebas de Software.



Los entregables se encuentran dentro de:



ENTREGABLES\_SCRUM/





svg



🟠 Semana 2 - SCRUM y Pruebas de Software



svg



Durante la Semana 2 se trabajó en la definición de los requisitos, criterios de aceptación, planificación de pruebas y gestión inicial de riesgos.



Historias de usuario



svg



Se definieron historias de usuario relacionadas con las principales funcionalidades de la aplicación:



Consultar actividades.



Consultar estado y progreso.



Seleccionar una actividad.



Cada historia cuenta con sus respectivos criterios de aceptación.



Criterios de aceptación



svg



Se establecieron criterios para determinar cuándo una funcionalidad cumple con lo esperado.



También se definieron criterios relacionados con aspectos no funcionales, como el comportamiento y rendimiento esperado de la aplicación.



Product Backlog



svg



Se organizó un Product Backlog con las funcionalidades principales del proyecto, estableciendo prioridades para su desarrollo.



Definition of Ready



svg



Se establecieron condiciones para determinar cuándo una historia de usuario cuenta con la información necesaria para ser trabajada.



Definition of Done



svg



Se establecieron condiciones para considerar terminada una historia de usuario, incluyendo desarrollo, revisión y pruebas.



Matriz de riesgos



svg



Se identificaron riesgos relacionados con:



Funcionamiento de la aplicación.



Validación de datos.



Interfaz de usuario.



Pruebas.



Integración.



Cumplimiento de requisitos.



Cada riesgo cuenta con una respuesta o estrategia de tratamiento.



Plan de pruebas



svg



Se elaboró el Plan de Pruebas versión 1, donde se definieron:



Objetivo.



Alcance.



Fuera de alcance.



Base de pruebas.



Riesgos.



Estrategia.



Ambiente de pruebas.



Datos de prueba.



Roles.



Criterios de entrada y salida.



Entregables.



Cronograma.



Matriz de trazabilidad



svg



Se inició la trazabilidad entre:



Historia de usuario

&#x20;       ↓

Criterio de aceptación

&#x20;       ↓

Riesgo

&#x20;       ↓

Caso de prueba





svg



Revisión entre pares



svg



Se incluyó una revisión de los productos elaborados para identificar posibles mejoras, inconsistencias o elementos pendientes.



🔴 Semana 3 - SCRUM y Pruebas de Software



svg



Durante la Semana 3 se transformaron los requisitos definidos anteriormente en casos de prueba y técnicas de diseño de pruebas.



Casos de prueba



svg



Se diseñaron casos de prueba para comprobar diferentes comportamientos de la aplicación.



Se incluyeron:



Pruebas positivas.



Pruebas negativas.



Pruebas de valores límite.



Pruebas de partición de equivalencia.



Pruebas de transición de estados.



Escenarios de caso de uso.



Pruebas de regresión.



Partición de equivalencia



svg



Se analizaron diferentes grupos de valores para el progreso de una actividad:



Valores menores que 0.



Valores entre 0 y 100.



Valores mayores que 100.



Esto permite identificar entradas válidas e inválidas.



Valores límite



svg



Se definieron valores cercanos a los límites para realizar las pruebas:



\-1

0

1

99

100

101





svg



Estos valores permiten comprobar el comportamiento de las reglas de validación.



Tabla de decisión



svg



Se elaboró una tabla de decisión para analizar los diferentes estados de una actividad:



Pendiente.



En curso.



Completada.



Vencida.



Transición de estados



svg



Se documentaron posibles cambios de estado de las actividades.



Ejemplo:



PENDIENTE

&#x20;   ↓

EN CURSO

&#x20;   ↓

COMPLETADA





svg



También se analizaron situaciones relacionadas con actividades vencidas.



Casos de uso



svg



Se documentó un caso de uso relacionado con la consulta y selección de actividades.



Datos de prueba



svg



Se definieron datos ficticios para realizar las diferentes pruebas de la aplicación.



Gestión de defectos



svg



Se documentó el proceso para registrar defectos, incluyendo:



Identificación.



Descripción.



Pasos para reproducir.



Resultado esperado.



Resultado obtenido.



Severidad.



Prioridad.



Evidencia.



Estado.



Ejecución de pruebas



svg



Se preparó una ejecución de pruebas y un registro de resultados para documentar el comportamiento esperado de los casos de prueba.



Nota: Los resultados marcados como simulados en los entregables deben reemplazarse por resultados reales y evidencias cuando las pruebas se ejecuten directamente en Android Studio.



Trazabilidad actualizada



svg



Se actualizó la relación:



Historia de usuario

&#x20;       ↓

Criterio de aceptación

&#x20;       ↓

Riesgo

&#x20;       ↓

Caso de prueba

&#x20;       ↓

Resultado

&#x20;       ↓

Defecto





svg



Ticket de salida



svg



Se documentaron los conocimientos y actividades realizadas durante la Semana 3, incluyendo:



Valores límite.



Partición de equivalencia.



Tabla de decisión.



Transición de estados.



Casos de prueba.



Gestión de defectos.



Trazabilidad.



🟣 Semana 6 - Persistencia local con Room

Durante la Semana 6 se implementó la persistencia local de la información utilizando Room.

Principales implementaciones:

- Base de datos local con Room.
- Entidades y DAOs para las actividades y reportes.
- Repositorios para gestionar el acceso a los datos.
- Mapeadores entre entidades y modelos de dominio.
- Migraciones de la base de datos.
- Pruebas instrumentadas para DAO y migraciones.
- Integración de Room con la aplicación Android.

Rama: feature/semana-06-room


🔵 Semana 7 - Corrutinas y Flow

Durante la Semana 7 se implementó el manejo de operaciones asíncronas y estado reactivo mediante Kotlin Coroutines y Flow.

Principales implementaciones:

- Uso de corrutinas para operaciones asíncronas.
- Uso de Flow para observar cambios de datos.
- Estados reactivos para la interfaz.
- Búsqueda y ordenamiento de actividades.
- Ordenamiento por nombre y progreso.
- Vista de lista y cuadrícula.
- Actualización de información desde el servidor.
- Manejo de estados de carga, éxito y error.
- Pruebas del ViewModel utilizando corrutinas.

Rama: feature/semana-07-coroutines-flow


🟢 Semana 8 - Servicios web con Retrofit

Durante la Semana 8 se implementó el consumo de servicios web REST mediante Retrofit y Kotlin Serialization.

Principales implementaciones:

- Configuración de Retrofit.
- Consumo de servicios REST.
- DTOs para los datos de la API.
- Kotlin Serialization para serialización y deserialización.
- RemoteDataSource para la comunicación con el servidor.
- Integración de datos remotos con el repositorio.
- Integración con Room para la persistencia local.
- Manejo de errores de comunicación y respuestas HTTP.
- Pruebas con MockWebServer.

Rama: feature/semana-08-servicios-web


📊 Funcionalidades actuales de la aplicación



svg



La aplicación permite:



📋 Visualizar actividades formativas.



📈 Consultar el progreso.



🟢 Identificar actividades completadas.



🟡 Identificar actividades en curso.



🔴 Identificar actividades vencidas.



⚪ Identificar actividades pendientes.



📅 Consultar días restantes.



⭐ Identificar prioridad.



🔎 Consultar información de las actividades.



📊 Calcular el promedio de progreso.



📱 Visualizar las actividades mediante una interfaz desarrollada con Jetpack Compose.



🔗 Trazabilidad del proyecto



svg



Los productos de SCRUM y Pruebas de Software mantienen una relación entre los requisitos y las pruebas:



Requisito

&#x20;  ↓

Historia de usuario

&#x20;  ↓

Criterio de aceptación

&#x20;  ↓

Riesgo

&#x20;  ↓

Caso de prueba

&#x20;  ↓

Resultado

&#x20;  ↓

Defecto





svg



Esta trazabilidad permite comprobar que las funcionalidades definidas tengan pruebas asociadas.



🧑‍💻 Control de versiones



svg



El proyecto utiliza Git y GitHub para controlar los cambios realizados durante el desarrollo.



Se utilizan ramas para organizar el trabajo y mantener separados los cambios realizados durante el proceso de desarrollo.



📁 Entregables SCRUM



svg



Los documentos correspondientes a las Semanas 2 y 3 se encuentran en:



ENTREGABLES\_SCRUM/





svg



Semana 2



svg



Historias de usuario.



Criterios de aceptación.



Product Backlog.



Definition of Ready.



Definition of Done.



Matriz de riesgos.



Plan de pruebas v1.



Matriz de trazabilidad v1.



Revisión entre pares.



Semana 3



svg



Casos de prueba.



Partición de equivalencia.



Valores límite.



Tabla de decisión.



Transición de estados.



Caso de uso.



Datos de prueba.



Ejecución simulada.



Registro de defectos.



Reporte de defecto.



Matriz de trazabilidad actualizada.



Ticket de salida.



Registro de cambios.



📌 Estado del proyecto



svg



Mi Formación CTMA se encuentra en desarrollo académico como parte del programa de formación ADSO.



Actualmente el proyecto cuenta con:



Desarrollo Android de las Semanas 1, 2, 3, 4, 6, 7 y 8.



Interfaz desarrollada con Kotlin y Jetpack Compose.



Componentes reutilizables.



Pantallas para consultar actividades y ver su detalle.



Formulario para crear nuevas actividades.



Manejo de estado mediante FormularioActividadUiState.



Validación de título, descripción, fecha y progreso.



ActividadesViewModel para gestionar las actividades.



Navegación entre lista, creación y detalle.



Conservación del estado del formulario mediante rememberSaveable.



Pruebas unitarias para las validaciones de la Semana 4.



Reglas para gestionar estados y progreso.



Documentación de SCRUM de las Semanas 2 y 3.



Planificación de pruebas.



Casos de prueba.



Matrices de trazabilidad.



Gestión y documentación de defectos.



👩‍💻 Proyecto académico



svg



Proyecto: Mi Formación CTMA Tecnología: Kotlin + Jetpack Compose Entorno: Android Studio Metodología: SCRUM Área: Desarrollo de Software y Pruebas de Software Programa: Análisis y Desarrollo de Software (ADSO)

