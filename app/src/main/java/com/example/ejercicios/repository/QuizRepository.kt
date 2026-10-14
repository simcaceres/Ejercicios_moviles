package com.example.ejercicios.repository

import com.example.ejercicios.DAO.ResultadoDao
import com.example.ejercicios.DAO.ResultadoEntity
import kotlinx.coroutines.flow.Flow

class QuizRepository(private val dao: ResultadoDao) {

    val historial: Flow<List<ResultadoEntity>> = dao.obtenerHistorial()

    suspend fun guardarResultado(resultado: ResultadoEntity) {
        dao.insertarResultado(resultado)
    }
}