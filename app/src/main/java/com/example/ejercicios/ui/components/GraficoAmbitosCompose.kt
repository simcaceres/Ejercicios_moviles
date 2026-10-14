package com.example.ejercicios.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

@Composable
fun GraficoAmbitosCompose(
    neuro: Float,
    dbt: Float,
    animo: Float,
    adicciones: Float,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            BarChart(context).apply {
                description.isEnabled = false
                legend.isEnabled = false

                val labels = listOf("Neuro", "DBT", "Ánimo", "Adicciones")
                xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                xAxis.granularity = 1f
                xAxis.setDrawGridLines(false)
            }
        },
        update = { chart ->
            val entries = listOf(
                BarEntry(0f, neuro),
                BarEntry(1f, dbt),
                BarEntry(2f, animo),
                BarEntry(3f, adicciones)
            )

            val dataSet = BarDataSet(entries, "Puntajes por Ámbito").apply {
                valueTextSize = 12f
            }

            chart.data = BarData(dataSet)
            chart.invalidate()
        }
    )
}