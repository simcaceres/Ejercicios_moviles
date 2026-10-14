package com.example.ejercicios.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicios.DAO.ResultadoEntity
import com.example.ejercicios.model.Ambito
import com.example.ejercicios.model.Pregunta
import com.example.ejercicios.repository.QuizRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class QuizViewModel(private val repository: QuizRepository) : ViewModel() {

    var nombre by mutableStateOf("")
    var apellido by mutableStateOf("")

    // Respuestas seleccionadas: Map<ID_Pregunta, Valor_1_a_5>
    var respuestas = mutableStateMapOf<Int, Int>()

    val preguntas = listOf(
        // NEURODESARROLLO
        Pregunta(1, "¿Lograste organizar tus tareas y rutinas diarias?", Ambito.NEURODESARROLLO),
        Pregunta(2, "¿Presentaste dificultades para mantener la concentración?", Ambito.NEURODESARROLLO),

        // DBT (Habilidades Dialéctico-Conductuales)
        Pregunta(3, "¿Aplicaste alguna habilidad de autorregulación ante el malestar?", Ambito.DBT),
        Pregunta(4, "¿Lograste gestionar emociones intensas sin actuar por impulso?", Ambito.DBT),

        // ESTADO DE ÁNIMO
        Pregunta(5, "¿Cómo estuvo tu nivel de energía y ánimo durante el día?", Ambito.ANIMO),
        Pregunta(6, "¿Experimentaste cambios bruscos en tu estado de ánimo?", Ambito.ANIMO),

        // ADICCIONES / CONTROL DE IMPULSOS
        Pregunta(7, "¿Sentiste impulsos o deseos intensos (craving) durante el día?", Ambito.ADICCIONES),
        Pregunta(8, "¿Pudiste ejecutar acciones protectoras ante un detonante?", Ambito.ADICCIONES)
    )

    fun esFormularioValido(): Boolean {
        return nombre.trim().isNotBlank() && apellido.trim().isNotBlank()
    }

    private fun calcularPuntajeAmbito(ambito: Ambito): Int {
        return preguntas
            .filter { it.ambito == ambito }
            .sumOf { respuestas[it.id] ?: 0 }
    }

    fun calcularYGuardarResultado() {
        val pNeuro = calcularPuntajeAmbito(Ambito.NEURODESARROLLO)
        val pDbt = calcularPuntajeAmbito(Ambito.DBT)
        val pAnimo = calcularPuntajeAmbito(Ambito.ANIMO)
        val pAdicciones = calcularPuntajeAmbito(Ambito.ADICCIONES)

        val total = pNeuro + pDbt + pAnimo + pAdicciones

        val recomendacionText = when {
            total <= 16 -> "Bajo / Estacionario: Nivel óptimo. Continúa con tus rutinas de autocuidado."
            total <= 28 -> "Moderado: Se observan indicadores leves. Te sugerimos revisar habilidades DBT."
            else -> "Elevado: Nivel de tensión alto. Se sugiere aplicar estrategias de autorregulación y consultar a un profesional."
        }

        val fechaActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

        val nuevoResultado = ResultadoEntity(
            nombreUsuario = "${nombre.trim()} ${apellido.trim()}",
            fecha = fechaActual,
            puntajeNeurodesarrollo = pNeuro,
            puntajeDBT = pDbt,
            puntajeAnimo = pAnimo,
            puntajeAdicciones = pAdicciones,
            puntajeTotal = total,
            recomendacion = recomendacionText
        )

        viewModelScope.launch {
            repository.guardarResultado(nuevoResultado)
        }
    }
}