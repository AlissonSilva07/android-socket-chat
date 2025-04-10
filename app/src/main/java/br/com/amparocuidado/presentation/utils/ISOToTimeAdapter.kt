package br.com.amparocuidado.presentation.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun ISOToTimeAdapter(isoDate: String): String {
    return try {
        val offsetDateTime = OffsetDateTime.parse(isoDate)
        val zonedDateTime = offsetDateTime.atZoneSameInstant(ZoneId.of("America/Sao_Paulo"))
        zonedDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    } catch (e: Exception) {
        "--:--"
    }
}