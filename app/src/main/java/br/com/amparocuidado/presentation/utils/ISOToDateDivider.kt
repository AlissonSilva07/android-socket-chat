package br.com.amparocuidado.presentation.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun ISOToDateDivider(isoDate: String): String {
    val zone = ZoneId.of("America/Sao_Paulo")
    val messageDate = ZonedDateTime.parse(isoDate).withZoneSameInstant(zone).toLocalDate()
    val today = ZonedDateTime.now(zone).toLocalDate()

    return when {
        messageDate.isEqual(today) -> "Hoje"
        messageDate.isEqual(today.minusDays(1)) -> "Ontem"
        else -> messageDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault()))
    }
}