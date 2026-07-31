package com.halyxsynck

import java.util.Calendar

actual object FechaHoy {
    actual fun obtener(): String {
        val cal = Calendar.getInstance()
        val año = cal.get(Calendar.YEAR)
        val mes = (cal.get(Calendar.MONTH) + 1).toString().padStart(2, '0')
        val dia = cal.get(Calendar.DAY_OF_MONTH).toString().padStart(2, '0')
        return "$año-$mes-$dia"
    }
}