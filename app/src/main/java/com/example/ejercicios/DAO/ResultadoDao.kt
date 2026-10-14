package com.example.ejercicios.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultadoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarResultado(resultado: ResultadoEntity)

    @Query("SELECT * FROM resultados_quiz ORDER BY id DESC")
    fun obtenerHistorial(): Flow<List<ResultadoEntity>>
}