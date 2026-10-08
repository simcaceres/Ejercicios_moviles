package com.example.ejercicios.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ejercicios.model.Pregunta
import com.example.ejercicios.model.ResultadoQuiz
import com.example.ejercicios.repository.QuizRepository
import kotlinx.coroutines.launch

class QuizViewModel(private val repository: QuizRepository) : ViewModel() {

    // Estado del Formulario
    var nombre by mutableStateOf("")
    var apellido by mutableStateOf("")

    // Estado del Quiz (ID Pregunta -> Opción Seleccionada 1 a 5)
    var respuestas = mutableStateMapOf<Int, Int>()

    // Estado del Resultado Actual
    var resultadoActual by mutableStateOf<ResultadoQuiz?>(null)

    // Lista de Preguntas
    val preguntas = listOf(
        Pregunta(1, "¿Te has sentido abrumado o estresado recientemente?"),
        Pregunta(2, "¿Has tenido dificultades para concentrarte en tus tareas?"),
        Pregunta(3, "¿Sientes fatiga o falta de energía frecuentemente?"),
        Pregunta(4, "¿Has experimentado cambios repentinos en tu estado de ánimo?"),
        Pregunta(5, "¿Te cuesta conciliar o mantener el sueño por las noches?"),
        Pregunta(6, "¿Sientes frustración o irritabilidad ante imprevistos simples?"),
        Pregunta(7, "¿Te resulta difícil desconectarte del trabajo o estudios?"),
        Pregunta(8, "¿Has dejado de disfrutar actividades que antes te gustaban?"),
        Pregunta(9, "¿Sientes tensión muscular o molestias físicas asociadas al estrés?"),
        Pregunta(10, "¿Sientes que tus niveles de ansiedad interfieren con tu rutina?")
    )

    fun esFormularioValido(): Boolean {
        return nombre.trim().isNotBlank() && apellido.trim().isNotBlank()
    }

    fun calcularYGuardarResultado() {
        val total = respuestas.values.sum()
        val (nivel, rec) = when {
            total <= 20 -> "Bajo / Estacionario" to "Nivel óptimo. Continúa con tus rutinas habituales de autocuidado."
            total <= 35 -> "Moderado" to "Se observan indicadores leves de cansancio. Te sugerimos realizar pausas activas."
            else -> "Elevado" to "Nivel de tensión o malestar alto. Se recomienda consultar con un profesional o especialista."
        }

        val nuevoResultado = ResultadoQuiz(
            nombreUsuario = "$nombre $apellido",
            fecha = "05/10/2026",
            puntuacionTotal = total,
            nivelEstres = nivel,
            recomendacion = rec
        )

        resultadoActual = nuevoResultado

        viewModelScope.launch {
            repository.guardarResultado(nuevoResultado)
        }
    }
}