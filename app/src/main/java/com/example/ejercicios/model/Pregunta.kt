package com.example.ejercicios.model

// Definición de los 4 ámbitos requeridos por Lemac DataLab
enum class Ambito {
    NEURODESARROLLO,
    DBT,
    ANIMO,
    ADICCIONES
}

// Modelo para representar cada pregunta del cuestionario
data class Pregunta(
    val id: Int,
    val texto: String,
    val ambito: Ambito
)