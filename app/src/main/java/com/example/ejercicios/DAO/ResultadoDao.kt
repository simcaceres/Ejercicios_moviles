package com.example.ejercicios.DAO

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultadoDao {
    @Insert
    suspend fun insertarResultado(resultado: ResultadoEntity)

    @Query("SELECT * FROM resultados_quiz ORDER BY id DESC")
    fun obtenerHistorial(): Flow<List<ResultadoEntity>>
}