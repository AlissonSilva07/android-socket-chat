package br.com.amparocuidado.presentation.ui.chat.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.amparocuidado.domain.model.Message
import com.composables.icons.lucide.CheckCheck
import com.composables.icons.lucide.Clock
import com.composables.icons.lucide.Lucide

@Composable
fun ChatBubble(
    modifier: Modifier = Modifier,
    message: Message,
    author: Int,
    isFirst: Boolean,
    isLast: Boolean,
    isPending: Boolean
) {
    val shapeAuthorNotMe = if (isFirst && isLast) {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 4.dp)
    } else if (isFirst) {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 4.dp)
    } else if (isLast) {
        RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 16.dp)
    } else {
        RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 4.dp)
    }

    val shapeAuthorMe = if (isFirst && isLast) {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 4.dp, bottomStart = 16.dp)
    } else if (isFirst) {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 4.dp, bottomStart = 16.dp)
    } else if (isLast) {
        RoundedCornerShape(topStart = 16.dp, topEnd = 4.dp, bottomEnd = 16.dp, bottomStart = 16.dp)
    } else {
        RoundedCornerShape(topStart = 16.dp, topEnd = 4.dp, bottomEnd = 4.dp, bottomStart = 16.dp)
    }

    if (message.createdBy == author) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Spacer(Modifier.weight(0.09f))
            Card(
                modifier = Modifier
                    .weight(1f),
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = shapeAuthorMe,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.TopStart),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = message.mensagem ?: "Mensagem",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.surface,
                            fontWeight = FontWeight.Normal
                        )
                    }

                    if (isPending) {
                        Icon(
                            imageVector = Lucide.Clock,
                            contentDescription = "Sending...",
                            modifier = Modifier
                                .size(16.dp)
                                .align(Alignment.BottomEnd),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    } else {
                        Icon(
                            imageVector = Lucide.CheckCheck,
                            contentDescription = "Sent",
                            modifier = Modifier
                                .size(16.dp)
                                .align(Alignment.BottomEnd),
                            tint = MaterialTheme.colorScheme.scrim
                        )
                    }
                }
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            if (isLast) {
                Card(
                    modifier = Modifier
                        .size(32.dp),
                    colors = CardColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.surface,
                        disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                        disabledContentColor = MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(percent = 100)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = message.nome?.take(1) ?: "",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.surface,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            } else {
                Spacer(modifier = Modifier.width(32.dp))
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContentColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = shapeAuthorNotMe,
                border = BorderStroke(color = MaterialTheme.colorScheme.outline, width = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = message.mensagem ?: "Mensagem",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}