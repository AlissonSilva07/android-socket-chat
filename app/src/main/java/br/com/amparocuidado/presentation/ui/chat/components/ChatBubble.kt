package br.com.amparocuidado.presentation.ui.chat.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.amparocuidado.domain.model.Message
import br.com.amparocuidado.presentation.ui.theme.AmparoCuidadoTheme

@Composable
fun ChatBubble(
    modifier: Modifier = Modifier,
    message: Message,
    author: Int
) {
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
                shape = RoundedCornerShape(16.dp, 16.dp, 4.dp,16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = message.mensagem,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.surface,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
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
                        text = message.nome.take(1),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.surface,
                        fontWeight = FontWeight.Normal
                    )
                }
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
                shape = RoundedCornerShape(16.dp, 16.dp, 16.dp, 4.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
                border = BorderStroke(color = MaterialTheme.colorScheme.outline, width = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = message.mensagem,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ChatBubbleNotMePreview() {
    AmparoCuidadoTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ChatBubble(
                message = Message(
                    idMensagem = 1,
                    mensagem = "Oi, tudo bem?",
                    idChat = 22,
                    createdBy = 17,
                    createdAt = "2025-04-07",
                    nome = "Enfermeiro"
                ),
                author = 10
            )

            ChatBubble(
                message = Message(
                    idMensagem = 1,
                    mensagem = "Oi, Tudo sim.",
                    idChat = 22,
                    createdBy = 10,
                    createdAt = "2025-04-07",
                    nome = "Alisson"
                ),
                author = 10
            )

        }
    }
}