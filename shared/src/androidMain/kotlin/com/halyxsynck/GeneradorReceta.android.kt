package com.halyxsynck

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import com.halyxsynck.model.RecetaInfo
import java.io.File
import java.io.FileOutputStream

actual class GeneradorReceta(private val context: Context) {

    actual fun generarYCompartir(receta: RecetaInfo, nombreDoctor: String, especialidad: String) {

        val documento = PdfDocument()
        val paginaInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val pagina = documento.startPage(paginaInfo)
        val canvas: Canvas = pagina.canvas

        // Marcas de agua rojas y moradas, semi-transparentes
        val paintMoradoAgua = Paint().apply {
            color = Color.argb(18, 126, 87, 194)
            style = Paint.Style.FILL
        }
        val paintRojoAgua = Paint().apply {
            color = Color.argb(16, 229, 57, 53)
            style = Paint.Style.FILL
        }
        canvas.drawCircle(480f, 100f, 140f, paintMoradoAgua)
        canvas.drawCircle(80f, 720f, 110f, paintRojoAgua)
        canvas.drawCircle(500f, 750f, 90f, paintRojoAgua)
        canvas.drawCircle(60f, 150f, 70f, paintMoradoAgua)

        // Barra superior morada
        val paintBarra = Paint().apply {
            color = Color.rgb(126, 87, 194)
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, 0f, 595f, 90f, paintBarra)

        val paintTituloBlanco = Paint().apply {
            color = Color.WHITE
            textSize = 22f
            isFakeBoldText = true
        }
        canvas.drawText("HALYX SYNC", 40f, 40f, paintTituloBlanco)

        val paintSubtituloBlanco = Paint().apply {
            color = Color.WHITE
            textSize = 13f
        }
        canvas.drawText("Receta médica", 40f, 65f, paintSubtituloBlanco)

        var y = 140f
        val paintTextoNormal = Paint().apply { color = Color.BLACK; textSize = 13f }
        val paintTextoNegrita = Paint().apply { color = Color.BLACK; textSize = 13f; isFakeBoldText = true }
        val paintRojo = Paint().apply { color = Color.rgb(229, 57, 53); textSize = 14f; isFakeBoldText = true }

        canvas.drawText("Paciente: ${receta.pacienteNombre}", 40f, y, paintTextoNegrita); y += 22f
        canvas.drawText("Edad: ${receta.edad} años", 40f, y, paintTextoNormal); y += 22f
        canvas.drawText("Médico: Dr. $nombreDoctor", 40f, y, paintTextoNormal); y += 22f
        canvas.drawText("Especialidad: $especialidad", 40f, y, paintTextoNormal); y += 22f
        canvas.drawText("Fecha: ${FechaHoy.obtener()}", 40f, y, paintTextoNormal); y += 40f

        canvas.drawText("MEDICAMENTOS RECETADOS", 40f, y, paintRojo)
        y += 10f
        canvas.drawLine(40f, y, 555f, y, Paint().apply { color = Color.LTGRAY; strokeWidth = 1f })
        y += 30f

        receta.medicamentos.forEach { med ->
            canvas.drawText("• ${med.nombre} — ${med.dosis}", 40f, y, paintTextoNegrita); y += 18f
            canvas.drawText("   Horario: ${med.horario}", 50f, y, paintTextoNormal); y += 16f
            if (med.padecimiento.isNotBlank()) {
                canvas.drawText("   Para: ${med.padecimiento}", 50f, y, paintTextoNormal); y += 16f
            }
            if (med.observaciones.isNotBlank()) {
                canvas.drawText("   Obs: ${med.observaciones}", 50f, y, paintTextoNormal); y += 16f
            }
            y += 14f
        }

        val paintFooter = Paint().apply { color = Color.GRAY; textSize = 10f }
        canvas.drawText("Generado por Halyx Sync", 40f, 800f, paintFooter)

        documento.finishPage(pagina)

        val nombreArchivo = "Receta_${receta.pacienteNombre.replace(" ", "_")}_${FechaHoy.obtener()}.pdf"
        val archivo = File(context.cacheDir, nombreArchivo)
        documento.writeTo(FileOutputStream(archivo))
        documento.close()

        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", archivo)

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(intent, "Compartir receta").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })

    }

}

@Composable
actual fun rememberGeneradorReceta(): GeneradorReceta {
    val context = LocalContext.current
    return GeneradorReceta(context)
}