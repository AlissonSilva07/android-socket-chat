package br.com.amparocuidado.presentation.ui.chatlist

import ISOToDateAdapter
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.amparocuidado.presentation.components.ButtonVariant
import br.com.amparocuidado.presentation.components.CustomButton
import br.com.amparocuidado.presentation.ui.chatlist.components.ChatListCard
import br.com.amparocuidado.presentation.ui.login.LoginViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen(
    onNavigateToChat: () -> Unit = {},
    chatViewModel: ChatListViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val user = loginViewModel.userData.collectAsState()
    val chatList = chatViewModel.chatList.collectAsState()

    LaunchedEffect(user.value?.id_usuario) {
        user.value?.let {
            chatViewModel.getChatsByPaciente(id = it.id_usuario)
        }
    }

    DisposableEffect(Unit) {
        chatViewModel.observeMessages()
        onDispose {
            chatViewModel.removeMessageListener()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Chats de ${user.value?.nm_pessoa_fisica}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                    }
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
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            CustomButton(
                title = "Iniciar Nova Conversa",
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                variant = ButtonVariant.DEFAULT,
                disabled = false
            )
            Text(
                text = "Histórico de Conversas",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
            if (chatList.value != null) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(chatList.value ?: emptyList()) { chat ->
                        ChatListCard(
                            onNavigateToChat = onNavigateToChat,
                            modifier = Modifier.fillMaxWidth(),
                            title = chat.nomeEnfermeiro,
                            date = ISOToDateAdapter(chat.createdAt),
                            lastMessage = chat.ultimaMensagem,
                            quantity = chat.quantidade
                        )
                    }
                }
            } else {
                Text(
                    text = "Nenhuma conversa encontrada.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

        }
    }
}