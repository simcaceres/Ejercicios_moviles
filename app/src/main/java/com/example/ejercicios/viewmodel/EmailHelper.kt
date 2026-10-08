package com.example.ejercicios.viewmodel

import android.content.Context
import android.content.Intent
import com.example.ejercicios.model.ResultadoQuiz

object EmailHelper {
    fun enviarResultadoPorCorreo(context: Context, resultado: ResultadoQuiz) {
        val mensaje = """
            Hola, adjunto mi informe de autoevaluación:

            Usuario: ${resultado.nombreUsuario}
            Fecha: ${resultado.fecha}
            Puntuación: ${resultado.puntuacionTotal} / 50
            Nivel de Estado: ${resultado.nivelEstres}
            Recomendación: ${resultado.recomendacion}
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_SUBJECT, "Resultado Autoevaluación de Bienestar")
            putExtra(Intent.EXTRA_TEXT, mensaje)
        }

        context.startActivity(Intent.createChooser(intent, "Enviar correo con:"))
    }
}