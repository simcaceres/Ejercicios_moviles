package com.example.ejercicios.model


data class ResultadoQuiz(
    val id: Int = 0,
    val nombreUsuario: String,
    val fecha: String,
    val puntajeNeurodesarrollo: Int,
    val puntajeDBT: Int,
    val puntajeAnimo: Int,
    val puntajeAdicciones: Int,
    val puntajeTotal: Int,
    val recomendacion: String
)