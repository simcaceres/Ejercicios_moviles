package com.example.ejercicios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.ejercicios.DAO.ResultadoEntity
import com.example.ejercicios.ui.screens.*
import com.example.ejercicios.ui.theme.EjerciciosTheme

enum class Pantalla {
    BIENVENIDA,
    MENU_PRINCIPAL,
    QUIZ,
    RESULTADOS
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EjerciciosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavegacionApp()
                }
            }
        }
    }
}

@Composable
fun NavegacionApp() {
    var pantallaActual by remember { mutableStateOf(Pantalla.BIENVENIDA) }
    var nombreUsuario by remember { mutableStateOf("") }
    var apellidoUsuario by remember { mutableStateOf("") }
    var ultimoResultado by remember { mutableStateOf<ResultadoEntity?>(null) }

    when (pantallaActual) {
        Pantalla.BIENVENIDA -> {
            PantallaBienvenida(
                onComenzarClick = { nombre, apellido ->
                    nombreUsuario = nombre
                    apellidoUsuario = apellido
                    pantallaActual = Pantalla.MENU_PRINCIPAL
                }
            )
        }
        Pantalla.MENU_PRINCIPAL -> {
            PantallaMenuPrincipal(
                nombreCompleto = "$nombreUsuario $apellidoUsuario",
                ultimoResultado = ultimoResultado,
                onEmpezarQuiz = { pantallaActual = Pantalla.QUIZ },
                onVerResultados = { pantallaActual = Pantalla.RESULTADOS },
                onSalir = { pantallaActual = Pantalla.BIENVENIDA }
            )
        }
        Pantalla.QUIZ -> {
            PantallaQuiz(
                nombreUsuario = "$nombreUsuario $apellidoUsuario",
                onQuizFinalizado = { resultado ->
                    ultimoResultado = resultado
                    pantallaActual = Pantalla.RESULTADOS
                }
            )
        }
        Pantalla.RESULTADOS -> {
            val resultadoAMostrar = ultimoResultado ?: ResultadoEntity(
                nombreUsuario = "$nombreUsuario $apellidoUsuario",
                fecha = "14/10/2026",
                puntajeNeurodesarrollo = 4,
                puntajeDBT = 3,
                puntajeAnimo = 5,
                puntajeAdicciones = 2,
                puntajeTotal = 14,
                recomendacion = "Nivel óptimo. Continúa con tus rutinas habituales de autocuidado."
            )

            PantallaResultados(
                resultado = resultadoAMostrar,
                onVolver = { pantallaActual = Pantalla.MENU_PRINCIPAL }
            )
        }
    }
}