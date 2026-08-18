package com.sofia.miformacionctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.domain.Prioridad
import com.sofia.miformacionctma.domain.buscarPorTitulo
import com.sofia.miformacionctma.domain.estadoActividad
import com.sofia.miformacionctma.domain.generarResumen
import com.sofia.miformacionctma.domain.ordenarActividades
import com.sofia.miformacionctma.domain.validarActividad
import com.sofia.miformacionctma.ui.theme.MiFormacionCTMATheme
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val datos = crearDatosPantalla()

        setContent {
            MiFormacionCTMATheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    ContenidoSemana2(
                        datos = datos,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

private data class PantallaDatos(
    val resumen: String,
    val actividadPrioritaria: String,
    val busqueda: String,
    val validacion: String
)

private fun crearDatosPantalla(): PantallaDatos {

    val actividades = listOf(
        ActividadFormativa(
            id = 1L,
            titulo = "Configurar Android Studio",
            descripcion = "Preparar el entorno de desarrollo",
            progreso = 100,
            diasRestantes = -2,
            prioridad = Prioridad.ALTA
        ),
        ActividadFormativa(
            id = 2L,
            titulo = "Kotlin básico",
            descripcion = "Practicar variables y condiciones",
            progreso = 80,
            diasRestantes = 1,
            prioridad = Prioridad.ALTA
        ),
        ActividadFormativa(
            id = 3L,
            titulo = "Null safety",
            descripcion = null,
            progreso = 40,
            diasRestantes = 2,
            prioridad = Prioridad.MEDIA
        ),
        ActividadFormativa(
            id = 4L,
            titulo = "Entregar evidencia",
            descripcion = "Subir las capturas del proyecto",
            progreso = 20,
            diasRestantes = -1,
            prioridad = Prioridad.ALTA
        ),
        ActividadFormativa(
            id = 5L,
            titulo = "Repasar colecciones",
            descripcion = null,
            progreso = 0,
            diasRestantes = 5,
            prioridad = Prioridad.BAJA
        )
    )

    val ordenadas = ordenarActividades(actividades)
    val primera = ordenadas.firstOrNull()

    val actividadPrioritaria = if (primera != null) {

        val descripcion = primera.descripcion
            ?.takeIf { it.isNotBlank() }
            ?: "Sin descripción registrada"

        """
            ${primera.titulo}
            Estado: ${estadoActividad(primera)}
            Prioridad: ${primera.prioridad}
            Días restantes: ${primera.diasRestantes}
            Descripción: $descripcion
        """.trimIndent()

    } else {
        "No hay actividades registradas"
    }

    val coincidencias = buscarPorTitulo(
        actividades = actividades,
        texto = " kotlin "
    )

    val resultadoBusqueda = coincidencias.firstOrNull()?.let {
        "Coincidencia encontrada: ${it.titulo}"
    } ?: "No se encontraron coincidencias"

    val errores = validarActividad(
        titulo = " ",
        progreso = 120
    )

    val resultadoValidacion = if (errores.isEmpty()) {
        "Datos válidos"
    } else {
        errores.joinToString(separator = "\n")
    }

    return PantallaDatos(
        resumen = generarResumen(actividades),
        actividadPrioritaria = actividadPrioritaria,
        busqueda = resultadoBusqueda,
        validacion = resultadoValidacion
    )
}

@Composable
private fun ContenidoSemana2(
    datos: PantallaDatos,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Mi Formación CTMA",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Semana 2 · Fundamentos de Kotlin",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(18.dp))

        TarjetaInformacion(
            titulo = "Resumen de actividades",
            contenido = datos.resumen
        )

        Spacer(modifier = Modifier.height(12.dp))

        TarjetaInformacion(
            titulo = "Actividad prioritaria",
            contenido = datos.actividadPrioritaria
        )

        Spacer(modifier = Modifier.height(12.dp))

        TarjetaInformacion(
            titulo = "Prueba de búsqueda",
            contenido = datos.busqueda
        )

        Spacer(modifier = Modifier.height(12.dp))

        TarjetaInformacion(
            titulo = "Prueba de validación",
            contenido = datos.validacion
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "¿Qué es Scrum?",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Scrum es un marco de trabajo ágil utilizado para desarrollar productos de forma colaborativa e incremental mediante iteraciones llamadas Sprints."
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Roles de Scrum",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text =
                        """
• Product Owner
Representa al cliente, define las prioridades del producto y administra el Product Backlog.

• Scrum Master
Facilita la aplicación de Scrum, elimina impedimentos y apoya al equipo.

• Developers
Equipo de desarrollo encargado de construir y entregar el incremento del producto en cada Sprint.
            """.trimIndent()
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))





        Card {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Artefactos de Scrum",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text =
                        """
• Product Backlog
Lista priorizada de requisitos, funcionalidades y mejoras del producto.

• Sprint Backlog
Conjunto de tareas seleccionadas para desarrollarse durante un Sprint.

• Incremento
Resultado funcional obtenido al finalizar el Sprint y listo para ser entregado.
                """.trimIndent()
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Spacer(modifier = Modifier.height(12.dp))

        Card {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Ceremonias de Scrum",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text =
                        """
• Sprint Planning
Reunión donde se define qué trabajo se realizará durante el Sprint.

• Daily Scrum
Reunión diaria corta para revisar el avance, identificar problemas y coordinar el trabajo.

• Sprint Review
Reunión al finalizar el Sprint donde se presenta y revisa el incremento desarrollado.

• Sprint Retrospective
Reunión donde el equipo analiza qué salió bien, qué puede mejorar y qué cambios aplicará en el siguiente Sprint.
                """.trimIndent()
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))


    }
}

@Composable
private fun TarjetaInformacion(
    titulo: String,
    contenido: String
) {
    Card {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = contenido)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContenidoSemana2Preview() {
    MiFormacionCTMATheme {
        ContenidoSemana2(
            datos = PantallaDatos(
                resumen = """
                    Total de actividades: 5
                    Promedio: 48.0 %
                    Completadas: 1
                    Vencidas: 1
                    Urgentes: 3
                """.trimIndent(),
                actividadPrioritaria = """
                    Entregar evidencia
                    Estado: VENCIDA
                    Prioridad: ALTA
                    Días restantes: -1
                """.trimIndent(),
                busqueda = "Coincidencia encontrada: Kotlin básico",
                validacion = """
                    El título es obligatorio
                    El progreso debe estar entre 0 y 100
                """.trimIndent()
            )
        )
    }
}