package br.com.amparocuidado.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

enum class ButtonVariant {
    DEFAULT,
    DISABLED,
    MUTED,
    DESTRUCTIVE
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    title: String,
    variant: ButtonVariant,
    disabled: Boolean,
    onClick: () -> Unit,
    icon: @Composable (() -> Unit)? = null
) {

    val backgroundColor = when (variant) {
        ButtonVariant.DEFAULT -> MaterialTheme.colorScheme.primary
        ButtonVariant.DISABLED -> MaterialTheme.colorScheme.secondary
        ButtonVariant.MUTED -> MaterialTheme.colorScheme.inverseOnSurface
        ButtonVariant.DESTRUCTIVE -> MaterialTheme.colorScheme.error
    }

    val contentColor = when (variant) {
        ButtonVariant.DISABLED -> MaterialTheme.colorScheme.onSurface
        ButtonVariant.MUTED -> MaterialTheme.colorScheme.onSurface
        ButtonVariant.DESTRUCTIVE -> MaterialTheme.colorScheme.surface
        else -> MaterialTheme.colorScheme.surface
    }

    Button(
        onClick = {
            if (!disabled) {
                onClick()
            }
        },
        colors = ButtonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = MaterialTheme.colorScheme.onBackground,
            disabledContentColor = MaterialTheme.colorScheme.background,
        ),
        enabled = variant != ButtonVariant.DISABLED,
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(8.dp))
        if (icon != null) {
            icon()
        }
    }
}