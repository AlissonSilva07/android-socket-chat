package br.com.amparocuidado.presentation.ui.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.amparocuidado.domain.model.Message
import br.com.amparocuidado.presentation.ui.chat.components.ChatBubble
import br.com.amparocuidado.presentation.ui.theme.AmparoCuidadoTheme
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Camera
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.SendHorizontal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    var textMessage by remember { mutableStateOf("") }

    val messages = remember { mutableListOf<Message>(
        Message(
            idMensagem = 1,
            mensage = "Olá, enfermeira. Estou com uma dor muito forte no meu abdômen.",
            idChat = 22,
            createdBy = 10,
            createdAt = "2025-04-07",
            nome = "Enfereiro"
        ),
        Message(
            idMensagem = 2,
            mensage = "Olá! Sinto muito por você estar sentindo dor. Pode me dizer há quanto tempo você está assim e se a dor é constante ou vem e vai?",
            idChat = 22,
            createdBy = 17,
            createdAt = "2025-04-07",
            nome = "Alisson"
        ),
        Message(
            idMensagem = 3,
            mensage = "Começou há umas três horas. A dor é constante e parece estar aumentando.",
            idChat = 22,
            createdBy = 10,
            createdAt = "2025-04-07",
            nome = "Enfereiro"
        )
    ) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                navigationIcon = {
                    Icon(
                        imageVector = Lucide.ArrowLeft,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                },
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Conversa",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    Icon(
                        imageVector = Lucide.ArrowLeft,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
                .fillMaxSize()
                .imePadding()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Bottom
            ) {
                items(messages) { message ->
                    ChatBubble(
                        message = message,
                        author = 10
                    )
                    Spacer(Modifier.height(16.dp))
                }
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.inverseOnSurface,
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Lucide.Camera,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                    OutlinedTextField(
                        value = textMessage,
                        onValueChange = { newValue ->
                            textMessage = newValue                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.inverseOnSurface,
                            unfocusedBorderColor = MaterialTheme.colorScheme.inverseOnSurface,
                            cursorColor = MaterialTheme.colorScheme.onSurface
                        ),
                        placeholder = {
                            Text(
                                text = "Digite uma mensagem...",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.secondary,
                                fontWeight = FontWeight.Normal
                            )
                        },
                        modifier = Modifier.weight(1f),

                    )
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Lucide.SendHorizontal,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ChatSceenPreview() {
    AmparoCuidadoTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        ChatScreen()
    }
}