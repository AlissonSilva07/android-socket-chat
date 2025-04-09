package br.com.amparocuidado.presentation.ui.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.amparocuidado.data.mapper.toMessage
import br.com.amparocuidado.data.remote.dto.chat.MessagesByChatIdResponse
import br.com.amparocuidado.data.utils.Resource
import br.com.amparocuidado.domain.model.FileData
import br.com.amparocuidado.domain.model.Message
import br.com.amparocuidado.domain.model.NewMessageInChat
import br.com.amparocuidado.domain.repository.ChatRepository
import br.com.amparocuidado.domain.repository.SocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val socketRepository: SocketRepository
) : ViewModel() {

    private val _messagesResponse =
        MutableStateFlow<Resource<List<MessagesByChatIdResponse>>>(Resource.Idle)
    val messagesResponse = _messagesResponse.asStateFlow()

    private val _textMessage = MutableStateFlow<String>("")
    val textMessage = _textMessage.asStateFlow()

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()

    fun changeTextMessage(text: String) {
        _textMessage.value = text
    }

    fun getMessagesByChatId(idChat: Int, idUsuario: Int) {
        viewModelScope.launch {
            _messagesResponse.value = Resource.Loading
            try {
                val response = chatRepository.getMessagesByChatId(idChat)
                if (response is Resource.Success) {
                    try {
                        val messages = response.data.map { it.toMessage() }
                        _messages.value = messages
                        joinChat(idChat, idUsuario)
                    } catch (e: Exception) {
                        Log.e("ChatListViewModel", "Error mapping chats: ${e.message}", e)
                    }
                } else {
                    _messages.value = emptyList()
                }
                _messagesResponse.value = response
            } catch (e: Exception) {
                _messagesResponse.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun joinChat(idChat: Int, userId: Int) {
        val params = JSONObject().apply {
            put("id_chat", idChat)
            put("id_usuario", userId)
        }
        socketRepository.joinChat(params)
    }

    fun sendMessage(
        idChat: Int,
        userId: Int,
        message: String,
        nome: String,
        createdAt: String
    ) {
        val params = JSONObject().apply {
            put("id_chat", idChat)
            put("created_by", userId)
            put("created_at", createdAt)
            put("mensagem", message)
            put("nome", nome)
        }

        socketRepository.sendMessage(params)
    }

    fun observeMessages() {
        socketRepository.onNewMessageInChat { json ->
            try {
                val idMensagem = json.getInt("id_mensagem")
                val mensagem = json.getString("mensagem")
                val idChat = json.getInt("id_chat")
                val createdBy = json.getInt("created_by")
                val createdAt = json.getString("created_at")
                val nome = json.optString("nome", "Conversa")
                val sender = if (json.isNull("sender")) null else json.getString("sender")

                val file: FileData? = if (!json.isNull("file")) {
                    try {
                        val fileJson = json.getJSONObject("file")
                        FileData(
                            fileName = fileJson.getString("file_name"),
                            fileType = fileJson.getString("file_type"),
                            fileContent = fileJson.getString("file_content")
                        )
                    } catch (e: Exception) {
                        Log.e("ChatVM", "Error parsing file JSON: ${e.message}", e)
                        null
                    }
                } else {
                    null
                }

                insertNewMessage(
                    NewMessageInChat(
                        idMensagem = idMensagem,
                        mensagem = mensagem,
                        idChat = idChat,
                        createdBy = createdBy,
                        createdAt = createdAt,
                        nome = nome,
                        sender = sender,
                        file = file
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun insertNewMessage(message: NewMessageInChat) {
        _messages.update { currentList ->
            currentList.toMutableList().apply {
                val exists = any { it.id == message.idMensagem }
                if (!exists) {
                    add(message.toMessage())
                }
            }
        }
    }

    fun removeMessageListener() {
        socketRepository.removeNewMessageInChatListener()
    }
}