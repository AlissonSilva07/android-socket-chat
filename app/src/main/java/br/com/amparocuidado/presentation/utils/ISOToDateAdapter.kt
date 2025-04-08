import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun ISOToDateAdapter(dateString: String): String {
    return try {
        val zonedDateTime = ZonedDateTime.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        "Data inválida"
    }
}