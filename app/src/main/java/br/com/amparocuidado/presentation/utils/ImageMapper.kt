package br.com.amparocuidado.presentation.utils

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

fun ByteArray.toImageBitmap(): ImageBitmap? {
    return try {
        BitmapFactory.decodeByteArray(this, 0, this.size)?.asImageBitmap()
    } catch (e: Exception) {
        null
    }
}