package com.example.ejercicios.DAO

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "resultados_quiz")
data class ResultadoEntity(
    @PrimaryKey(autoGenerate = true)
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