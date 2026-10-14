package com.example.ejercicios.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ejercicios.DAO.ResultadoEntity
import com.example.ejercicios.ui.components.GraficoAmbitosCompose
import com.example.ejercicios.viewmodel.EmailHelper

@Composable
fun PantallaResultados(
    resultado: ResultadoEntity,
    onVolver: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Resumen por Ámbitos",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Usuario: ${resultado.nombreUsuario}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        GraficoAmbitosCompose(
            neuro = resultado.puntajeNeurodesarrollo.toFloat(),
            dbt = resultado.puntajeDBT.toFloat(),
            animo = resultado.puntajeAnimo.toFloat(),
            adicciones = resultado.puntajeAdicciones.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Recomendación:", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = resultado.recomendacion, style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        FilledTonalButton(
            onClick = { EmailHelper.enviarResultadoPorCorreo(context, resultado) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Descargar / Exportar Informe")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al Menú")
        }
    }
}