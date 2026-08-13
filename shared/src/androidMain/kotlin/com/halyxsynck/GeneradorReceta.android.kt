package com.halyxsynck

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.pdf.PdfDocument
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import com.halyxsynck.model.RecetaInfo
import java.io.File
import java.io.FileOutputStream

actual class GeneradorReceta(private val context: Context) {

    private fun dibujarCorazon(canvas: Canvas, cx: Float, cy: Float, size: Float, paint: Paint) {
        val path = Path()
        path.moveTo(cx, cy + size * 0.3f)
        path.cubicTo(cx - size, cy - size * 0.6f, cx - size * 0.5f, cy - size * 1.3f, cx, cy - size * 0.5f)
        path.cubicTo(cx + size * 0.5f, cy - size * 1.3f, cx + size, cy - size * 0.6f, cx, cy + size * 0.3f)
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun dibujarJeringa(canvas: Canvas, cx: Float, cy: Float, size: Float, paint: Paint) {
        canvas.save()
        canvas.rotate(-45f, cx, cy)
        canvas.drawRect(cx - size * 0.15f, cy - size, cx + size * 0.15f, cy + size * 0.6f, paint)
        canvas.drawRect(cx - size * 0.3f, cy - size * 1.15f, cx + size * 0.3f, cy - size, paint)
        canvas.drawRect(cx - size * 0.08f, cy + size * 0.6f, cx + size * 0.08f, cy + size, paint)
        canvas.restore()
    }

    private fun dibujarPulso(canvas: Canvas, cx: Float, cy: Float, size: Float, paint: Paint) {
        val path = Path()
        path.moveTo(cx - size, cy)
        path.lineTo(cx - size * 0.4f, cy)
        path.lineTo(cx - size * 0.2f, cy - size * 0.8f)
        path.lineTo(cx + size * 0.1f, cy + size * 0.8f)
        path.lineTo(cx + size * 0.3f, cy)
        path.lineTo(cx + size, cy)
        canvas.drawPath(path, paint)
    }

    actual fun generarYCompartir(receta: RecetaInfo, nombreDoctor: String, especialidad: String) {

        val documento = PdfDocument()
        // Horizontal: ancho 842, alto 595 (A4 apaisado)
        val paginaInfo = PdfDocument.PageInfo.Builder(842, 595, 1).create()
        val pagina = documento.startPage(paginaInfo)
        val canvas: Canvas = pagina.canvas

        canvas.drawColor(Color.WHITE)

        val paintMarco = Paint().apply {
            color = Color.rgb(126, 87, 194)
            style = Paint.Style.STROKE
            strokeWidth = 6f
        }
        canvas.drawRect(18f, 18f, 824f, 577f, paintMarco)
        val paintMarcoFino = Paint().apply {
            color = Color.rgb(126, 87, 194)
            style = Paint.Style.STROKE
            strokeWidth = 1.2f
        }
        canvas.drawRect(26f, 26f, 816f, 569f, paintMarcoFino)

        val paintCorazonAgua = Paint().apply { color = Color.argb(22, 229, 57, 53); isAntiAlias = true }
        val paintJeringaAgua = Paint().apply { color = Color.argb(20, 25, 118, 210); isAntiAlias = true }
        val paintPulsoAgua = Paint().apply {
            color = Color.argb(30, 126, 87, 194)
            style = Paint.Style.STROKE
            strokeWidth = 5f
            isAntiAlias = true
        }

        dibujarCorazon(canvas, 760f, 100f, 50f, paintCorazonAgua)
        dibujarCorazon(canvas, 90f, 500f, 40f, paintCorazonAgua)
        dibujarJeringa(canvas, 90f, 150f, 42f, paintJeringaAgua)
        dibujarJeringa(canvas, 760f, 480f, 46f, paintJeringaAgua)
        dibujarPulso(canvas, 420f, 540f, 55f, paintPulsoAgua)
        dibujarPulso(canvas, 700f, 280f, 42f, paintPulsoAgua)

        val paintBarra = Paint().apply {
            color = Color.rgb(126, 87, 194)
            style = Paint.Style.FILL
        }
        canvas.drawRoundRect(45f, 40f, 400f, 105f, 14f, 14f, paintBarra)

        val paintTituloBlanco = Paint().apply {
            color = Color.WHITE
            textSize = 22f
            isFakeBoldText = true
            isAntiAlias = true
        }
        canvas.drawText("HALYX SYNC", 62f, 72f, paintTituloBlanco)

        val paintSubtituloBlanco = Paint().apply {
            color = Color.WHITE
            textSize = 12f
            isAntiAlias = true
        }
        canvas.drawText("Receta médica", 62f, 92f, paintSubtituloBlanco)

        // Datos del paciente arriba a la derecha del encabezado
        val paintTextoNormal = Paint().apply { color = Color.rgb(40, 40, 40); textSize = 12f; isAntiAlias = true }
        val paintTextoNegrita = Paint().apply { color = Color.rgb(20, 20, 20); textSize = 13f; isFakeBoldText = true; isAntiAlias = true }
        val paintMorado = Paint().apply { color = Color.rgb(126, 87, 194); textSize = 14f; isFakeBoldText = true; isAntiAlias = true }
        val paintGris = Paint().apply { color = Color.rgb(120, 120, 120); textSize = 10f; isAntiAlias = true }

        var yDatos = 55f
        canvas.drawText("Paciente: ${receta.pacienteNombre}", 430f, yDatos, paintTextoNegrita); yDatos += 18f
        canvas.drawText("Edad: ${receta.edad} años", 430f, yDatos, paintTextoNormal); yDatos += 18f
        canvas.drawText("Médico: Dr. $nombreDoctor", 430f, yDatos, paintTextoNormal); yDatos += 18f
        canvas.drawText("Especialidad: $especialidad  ·  Fecha: ${FechaHoy.obtener()}", 430f, yDatos, paintTextoNormal)

        // Medicamentos, en dos columnas si hay varios
        var y = 150f
        canvas.drawText("MEDICAMENTOS RECETADOS", 62f, y, paintMorado)
        y += 10f
        canvas.drawLine(62f, y, 780f, y, Paint().apply { color = Color.rgb(220, 220, 235); strokeWidth = 1.5f })
        y += 26f

        val colX = listOf(62f, 440f)
        var colIndex = 0
        var yCol = mutableListOf(y, y)

        receta.medicamentos.forEach { med ->
            val x = colX[colIndex]
            var yy = yCol[colIndex]

            canvas.drawText("•  ${med.nombre} — ${med.dosis}", x, yy, paintTextoNegrita); yy += 16f
            canvas.drawText("    Horario: ${med.horario}", x, yy, paintTextoNormal); yy += 14f
            if (med.padecimiento.isNotBlank()) {
                canvas.drawText("    Para: ${med.padecimiento}", x, yy, paintTextoNormal); yy += 14f
            }
            if (med.observaciones.isNotBlank()) {
                canvas.drawText("    Obs: ${med.observaciones}", x, yy, paintTextoNormal); yy += 14f
            }
            yy += 14f

            yCol[colIndex] = yy
            colIndex = (colIndex + 1) % 2
        }

        // Firma, abajo a la derecha
        val yFirma = 540f
        canvas.drawLine(600f, yFirma, 780f, yFirma, Paint().apply { color = Color.rgb(90, 90, 90); strokeWidth = 1.2f })
        canvas.drawText("Firma del médico", 600f, yFirma + 16f, paintGris)

        canvas.drawText("Generado por Halyx Sync", 62f, 565f, paintGris)

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