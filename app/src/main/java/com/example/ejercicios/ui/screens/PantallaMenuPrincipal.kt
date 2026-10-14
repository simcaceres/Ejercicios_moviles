package com.example.ejercicios.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ejercicios.DAO.ResultadoEntity
import com.example.ejercicios.viewmodel.EmailHelper

@Composable
fun PantallaMenuPrincipal(
    nombreCompleto: String,
    ultimoResultado: ResultadoEntity?,
    onEmpezarQuiz: () -> Unit,
    onVerResultados: () -> Unit,
    onSalir: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Menú Principal",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Usuario: $nombreCompleto",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onEmpezarQuiz,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("1. Empezar Quiz / Registro")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onVerResultados,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("2. Ver Resultados y Gráficos")
        }

        Spacer(modifier = Modifier.height(12.dp))

        FilledTonalButton(
            onClick = {
                val resultadoAExportar = ultimoResultado ?: ResultadoEntity(
                    nombreUsuario = nombreCompleto,
                    fecha = "14/10/2026",
                    puntajeNeurodesarrollo = 4,
                    puntajeDBT = 3,
                    puntajeAnimo = 5,
                    puntajeAdicciones = 2,
                    puntajeTotal = 14,
                    recomendacion = "Nivel óptimo. Continúa con tus rutinas habituales de autocuidado."
                )
                EmailHelper.enviarResultadoPorCorreo(context, resultadoAExportar)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("3. Descargar / Exportar Reporte")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onSalir,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("4. Salir")
        }
    }
}