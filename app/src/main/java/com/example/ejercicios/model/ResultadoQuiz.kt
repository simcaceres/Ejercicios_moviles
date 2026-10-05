package com.example.ejercicios.model

data class ResultadoQuiz(
    val id: Int = 0,
    val nombreUsuario: String,
    val fecha: String,
    val puntuacionTotal: Int,
    val nivelEstres: String,
    val recomendacion: String
)
