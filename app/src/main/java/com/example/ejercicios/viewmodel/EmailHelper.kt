package com.example.ejercicios.viewmodel

import android.content.Context
import android.content.Intent
import com.example.ejercicios.DAO.ResultadoEntity

object EmailHelper {

    fun enviarResultadoPorCorreo(context: Context, resultado: ResultadoEntity) {
        val mensaje = """
            Hola, adjunto mi informe de autorregistro (Lemac DataLab):
            
            Usuario: ${resultado.nombreUsuario}
            Fecha: ${resultado.fecha}
            
            --- Resumen por Ámbitos ---
            - Neurodesarrollo: ${resultado.puntajeNeurodesarrollo} pts
            - Habilidades DBT: ${resultado.puntajeDBT} pts
            - Estado de Ánimo: ${resultado.puntajeAnimo} pts
            - Adicciones / Control: ${resultado.puntajeAdicciones} pts
            
            Puntuación Total: ${resultado.puntajeTotal}
            Recomendación: ${resultado.recomendacion}
            
            * Este registro es un ejercicio académico de autorreporte y no constituye un diagnóstico clínico.
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_SUBJECT, "Resultado Auto-evaluación - ${resultado.nombreUsuario}")
            putExtra(Intent.EXTRA_TEXT, mensaje)
        }

        context.startActivity(Intent.createChooser(intent, "Enviar correo con:"))
    }
}