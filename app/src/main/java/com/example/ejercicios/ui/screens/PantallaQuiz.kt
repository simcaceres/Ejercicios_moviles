package com.example.ejercicios.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ejercicios.DAO.ResultadoEntity
import com.example.ejercicios.model.Ambito
import com.example.ejercicios.model.Pregunta

@Composable
fun PantallaQuiz(
    nombreUsuario: String,
    onQuizFinalizado: (ResultadoEntity) -> Unit
) {
    val preguntas = remember {
        listOf(
            Pregunta(1, "¿Organizaste tus tareas y rutinas diarias correctamente?", Ambito.NEURODESARROLLO),
            Pregunta(2, "¿Tuviste dificultades para mantener la concentración?", Ambito.NEURODESARROLLO),
            Pregunta(3, "¿Aplicaste alguna habilidad de autorregulación emocional?", Ambito.DBT),
            Pregunta(4, "¿Lograste gestionar situaciones difíciles sin actuar por impulso?", Ambito.DBT),
            Pregunta(5, "¿Cómo estuvo tu nivel de energía y ánimo hoy?", Ambito.ANIMO),
            Pregunta(6, "¿Experimentaste cambios bruscos en tu estado de ánimo?", Ambito.ANIMO),
            Pregunta(7, "¿Sentiste impulsos o deseos intensos (craving) hoy?", Ambito.ADICCIONES),
            Pregunta(8, "¿Pudiste ejecutar acciones protectoras ante un detonante?", Ambito.ADICCIONES)
        )
    }

    var indiceActual by remember { mutableStateOf(0) }
    val respuestas = remember { mutableStateMapOf<Int, Int>() }
    val preguntaActual = preguntas[indiceActual]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Pregunta ${indiceActual + 1} de ${preguntas.size}",
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = "Ámbito: ${preguntaActual.ambito.name}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Text(
                    text = preguntaActual.texto,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(20.dp)
                )
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Selecciona una opción (1: Nunca - 5: Siempre):",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(12.dp))

            val opciones = listOf(
                1 to "1 - Nunca / Muy bajo",
                2 to "2 - Rara vez / Bajo",
                3 to "3 - A veces / Moderado",
                4 to "4 - Frecuentemente / Alto",
                5 to "5 - Siempre / Muy alto"
            )

            opciones.forEach { (valor, texto) ->
                OutlinedButton(
                    onClick = {
                        respuestas[preguntaActual.id] = valor
                        if (indiceActual < preguntas.size - 1) {
                            indiceActual++
                        } else {
                            fun calcularPuntaje(amb: Ambito) = preguntas
                                .filter { it.ambito == amb }
                                .sumOf { respuestas[it.id] ?: 0 }

                            val pNeuro = calcularPuntaje(Ambito.NEURODESARROLLO)
                            val pDbt = calcularPuntaje(Ambito.DBT)
                            val pAnimo = calcularPuntaje(Ambito.ANIMO)
                            val pAdicciones = calcularPuntaje(Ambito.ADICCIONES)
                            val total = pNeuro + pDbt + pAnimo + pAdicciones

                            val recomendacion = when {
                                total <= 16 -> "Bajo / Estacionario: Nivel óptimo. Continúa con tus rutinas de autocuidado."
                                total <= 28 -> "Moderado: Se observan indicadores leves. Te sugerimos revisar habilidades DBT."
                                else -> "Elevado: Nivel de tensión alto. Se sugiere aplicar estrategias de autorregulación y consultar a un profesional."
                            }

                            val fechaHoy = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault()).format(java.util.Date())

                            val resultadoFinal = ResultadoEntity(
                                nombreUsuario = nombreUsuario,
                                fecha = fechaHoy,
                                puntajeNeurodesarrollo = pNeuro,
                                puntajeDBT = pDbt,
                                puntajeAnimo = pAnimo,
                                puntajeAdicciones = pAdicciones,
                                puntajeTotal = total,
                                recomendacion = recomendacion
                            )

                            onQuizFinalizado(resultadoFinal)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(texto)
                }
            }
        }
    }
}