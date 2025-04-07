package br.com.amparocuidado.presentation.ui.chatlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.amparocuidado.presentation.components.ButtonVariant
import br.com.amparocuidado.presentation.components.CustomButton
import br.com.amparocuidado.presentation.ui.chatlist.components.ChatListCard
import br.com.amparocuidado.presentation.ui.theme.AmparoCuidadoTheme
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.MessageSquareMore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen(
    onNavigateToChat: () -> Unit = {}
) {
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
                            text = "Chats",
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

            ChatListCard(
                onNavigateToChat = onNavigateToChat,
                modifier = Modifier.fillMaxWidth(),
                title = "Conversa",
                date = "DD/MM/YYYY",
                lastMessage = "Mensagem",
                quantity = 1
            )

        }
    }
}

@Preview
@Composable
private fun ChatsScreenPreview() {
    AmparoCuidadoTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        ChatsScreen()
    }
}