package com.example.ejercicios.repository

import com.example.ejercicios.DAO.ResultadoDao
import com.example.ejercicios.DAO.ResultadoEntity
import com.example.ejercicios.model.ResultadoQuiz
import kotlinx.coroutines.flow.Flow

class QuizRepository(private val dao: ResultadoDao) {
    val historial: Flow<List<ResultadoEntity>> = dao.obtenerHistorial()

    suspend fun guardarResultado(resultado: ResultadoQuiz) {
        dao.insertarResultado(
            ResultadoEntity(
                nombreUsuario = resultado.nombreUsuario,
                fecha = resultado.fecha,
                puntuacionTotal = resultado.puntuacionTotal,
                nivelEstres = resultado.nivelEstres,
                recomendacion = resultado.recomendacion
            )
        )
    }
}