package br.com.amparocuidado.presentation.utils

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.Composable

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun isKeyboardOpen(): Boolean {
    val ime = WindowInsets.isImeVisible == true
    return ime
}