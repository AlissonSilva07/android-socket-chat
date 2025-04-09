package br.com.amparocuidado.presentation.ui.chat

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.amparocuidado.domain.model.Message
import br.com.amparocuidado.presentation.ui.chat.components.ChatBubble
import br.com.amparocuidado.presentation.ui.login.LoginViewModel
import br.com.amparocuidado.presentation.ui.theme.AmparoCuidadoTheme
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.Camera
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.SendHorizontal
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    idChat: String,
    onNavigateBack: () -> Unit,
    chatScreenViewModel: ChatScreenViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    val view = LocalView.current
    val activity = view.context as Activity
    val bottomBarColor = MaterialTheme.colorScheme.inverseOnSurface

    SideEffect {
        val window = activity.window
        window.navigationBarColor = bottomBarColor.toArgb()

        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightNavigationBars = bottomBarColor.luminance() > 0.5f
    }

    LaunchedEffect(idChat) {
        chatScreenViewModel.setChatId(idChat)
    }

    val textMessage by chatScreenViewModel.textMessage.collectAsState()

    val messages by chatScreenViewModel.messages.collectAsState()

    val user by loginViewModel.userData.collectAsState()

    val listState = rememberLazyListState()

    LaunchedEffect(user?.id_usuario) {
        user?.let {
            chatScreenViewModel.getMessagesByChatId(
                idChat = idChat.toInt(),
                idUsuario = it.id_usuario
            )
        }
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    DisposableEffect(Unit) {
        chatScreenViewModel.observeMessages()
        onDispose {
            chatScreenViewModel.removeMessageListener()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack
                    ) {
                        Icon(
                            imageVector = Lucide.ArrowLeft,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
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
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .imePadding()
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                state = listState,
                verticalArrangement = Arrangement.Bottom
            ) {
                itemsIndexed(messages) { index, message ->
                    val isFirst = index == 0 || messages[index - 1].createdBy != message.createdBy
                    val isLast = index == messages.lastIndex || messages[index + 1].createdBy != message.createdBy

                    ChatBubble(
                        message = message,
                        author = user?.id_usuario!!,
                        isFirst = isFirst,
                        isLast = isLast,
                        isPending = message.id == null
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.inverseOnSurface,
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
                            chatScreenViewModel.changeTextMessage(newValue)                        },
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
                        onClick = {
                            val trimmedMessage = textMessage.trim()
                            if (trimmedMessage.isNotEmpty()) {
                                user?.let {
                                    chatScreenViewModel.sendMessage(
                                        idChat = idChat.toInt(),
                                        userId = it.id_usuario,
                                        message = trimmedMessage,
                                        nome = it.nm_pessoa_fisica,
                                        createdAt = Instant.now().toString()
                                    )
                                    chatScreenViewModel.changeTextMessage("")
                                }
                            }
                        },
                        enabled = textMessage.isNotEmpty(),
                    ) {
                        Icon(
                            imageVector = Lucide.SendHorizontal,
                            contentDescription = null,
                            tint = if (textMessage.isNotEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }
}