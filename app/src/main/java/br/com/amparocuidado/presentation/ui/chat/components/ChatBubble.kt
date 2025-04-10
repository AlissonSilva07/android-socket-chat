package br.com.amparocuidado.presentation.ui.chat.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.amparocuidado.domain.model.Message
import br.com.amparocuidado.domain.model.MessageStatus
import br.com.amparocuidado.presentation.ui.theme.AmparoCuidadoTheme
import br.com.amparocuidado.presentation.utils.ISOToTimeAdapter
import br.com.amparocuidado.presentation.utils.toImageBitmap
import com.composables.icons.lucide.CheckCheck
import com.composables.icons.lucide.Clock
import com.composables.icons.lucide.Lucide

@RequiresApi(Build.VERSION_CODES.O)
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
            horizontalArrangement = Arrangement.End,
        ) {
            Spacer(modifier = Modifier.width(40.dp))
            Card(
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
                        .padding(8.dp),
                    contentAlignment = Alignment.BottomEnd,
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(bottom = 16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        message.mensagem?.let {
                            Text(
                                text = message.mensagem ?: "Mensagem",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.surface,
                                fontWeight = FontWeight.Normal
                            )
                        }

                        message.image?.toImageBitmap()?.let { bitmap ->
                            Spacer(modifier = Modifier.padding(top = 8.dp))
                            Image(
                                bitmap = bitmap,
                                contentDescription = "Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = ISOToTimeAdapter(message.createdAt ?: "--:--"),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            fontWeight = FontWeight.Normal
                        )
                        if (isPending) {
                            Icon(
                                imageVector = Lucide.Clock,
                                contentDescription = "Sending...",
                                modifier = Modifier
                                    .size(16.dp),
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        } else {
                            Icon(
                                imageVector = Lucide.CheckCheck,
                                contentDescription = "Sent",
                                modifier = Modifier
                                    .size(16.dp),
                                tint = MaterialTheme.colorScheme.scrim
                            )
                        }
                    }
                }
            }
        }
    } else {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
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
            Spacer(modifier = Modifier.width(8.dp))
            Card(
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContentColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = shapeAuthorNotMe,
                border = BorderStroke(color = MaterialTheme.colorScheme.outline, width = 1.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp),
                    contentAlignment = Alignment.BottomStart,
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(bottom = 16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = message.mensagem ?: "Mensagem",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Normal
                        )
                    }

                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = ISOToTimeAdapter(message.createdAt ?: "--:--"),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun ChatBubblePrev() {
    AmparoCuidadoTheme(
        dynamicColor = false
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ChatBubble(
                message = Message(
                    id = 1,
                    mensagem = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                    idChat = 1,
                    nome = "João",
                    createdBy = 1,
                    createdAt = "2025-04-10T14:35:00Z"
                ),
                modifier = Modifier,
                author = 1,
                isFirst = true,
                isLast = false,
                isPending = false
            )
            ChatBubble(
                message = Message(
                    id = 2,
                    mensagem = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                    idChat = 1,
                    nome = "João",
                    createdBy = 2,
                    createdAt = "2025-04-10T14:38:00Z"
                ),
                modifier = Modifier,
                author = 1,
                isFirst = true,
                isLast = true,
                isPending = false
            )
        }
    }

}