package com.sofia.miformacionctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sofia.miformacionctma.domain.ActividadFormativa
import com.sofia.miformacionctma.domain.Prioridad
import com.sofia.miformacionctma.domain.actividadesUrgentes
import com.sofia.miformacionctma.domain.promedioProgreso

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // Lista de actividades de ejemplo
            val actividades = listOf(
                ActividadFormativa(
                    id = 1,
                    titulo = "Configurar Android Studio",
                    descripcion = "Instalación inicial",
                    progreso = 100,
                    diasRestantes = -2,
                    prioridad = Prioridad.ALTA
                ),

                ActividadFormativa(
                    id = 2,
                    titulo = "Kotlin básico",
                    descripcion = "Variables y funciones",
                    progreso = 70,
                    diasRestantes = 1,
                    prioridad = Prioridad.ALTA
                ),

                ActividadFormativa(
                    id = 3,
                    titulo = "Colecciones Kotlin",
                    descripcion = "Listas y filtros",
                    progreso = 40,
                    diasRestantes = 5,
                    prioridad = Prioridad.MEDIA
                )
            )

            // Uso de las reglas de negocio
            val promedio = promedioProgreso(actividades)
            val urgentes = actividadesUrgentes(actividades)

            val resumen = "Promedio: %.1f%% · Urgentes: %d"
                .format(promedio, urgentes.size)

            Surface(modifier = Modifier.fillMaxSize()) {
                PantallaInicio(resumen = resumen)
            }
        }
    }
}

@Composable
fun PantallaInicio(resumen: String) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Mi Formación CTMA",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = resumen)

        Text(text = "Resumen calculado con reglas de negocio Kotlin.")
    }
}