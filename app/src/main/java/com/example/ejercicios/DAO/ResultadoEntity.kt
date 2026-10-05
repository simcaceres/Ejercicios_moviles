package com.example.ejercicios.DAO

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "resultados_quiz")
data class ResultadoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombreUsuario: String,
    val fecha: String,
    val puntuacionTotal: Int,
    val nivelEstres: String,
    val recomendacion: String
)
