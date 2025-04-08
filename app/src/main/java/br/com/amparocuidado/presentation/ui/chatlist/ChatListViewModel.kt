package br.com.amparocuidado.presentation.ui.chatlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.amparocuidado.data.mapper.toChat
import br.com.amparocuidado.data.remote.dto.chat.GetChatsByPacienteResponseDto
import br.com.amparocuidado.data.utils.Resource
import br.com.amparocuidado.domain.model.Chat
import br.com.amparocuidado.domain.model.NewMessage
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
class ChatListViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val socketRepository: SocketRepository
) : ViewModel() {

    private val _chatResponse =
        MutableStateFlow<Resource<GetChatsByPacienteResponseDto>>(Resource.Idle)
    val chatResponse = _chatResponse.asStateFlow()

    private val _chatList = MutableStateFlow<List<Chat>?>(null)
    val chatList = _chatList.asStateFlow()

    fun getChatsByPaciente(id: Int) {
        viewModelScope.launch {
            _chatResponse.value = Resource.Loading
            try {
                val response = chatRepository.getChatsByPaciente(
                    idUsuario = id,
                    situacaoChat = "A",
                    page = 1,
                    size = 5
                )
                if (response is Resource.Success) {
                    _chatList.value = response.data.data.map { it.toChat() }
                    joinAllChats(_chatList.value ?: emptyList(), id)
                } else {
                    _chatList.value = null
                }
                _chatResponse.value = response
            } catch (e: Exception) {
                _chatResponse.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun joinAllChats(chats: List<Chat>, userId: Int) {
        for (chat in chats) {
            val params = JSONObject().apply {
                put("id_chat", chat.id)
                put("id_usuario", userId)
            }
            socketRepository.joinChat(params)
        }
    }

    fun observeMessages() {
        socketRepository.onNewMessage { json ->
            try {
                val idChat = json.getInt("id_chat")
                val nome = json.optString("nome", "Conversa")
                val mensagem = json.getString("mensagem")
                val createdAt = json.getString("created_at")
                val quantidade = json.optInt("quantidade", 1)
                val created_by = json.getInt("created_by")

                updateLastMessage(
                    NewMessage(
                        id_chat = idChat,
                        nome = nome,
                        mensagem = mensagem,
                        created_at = createdAt,
                        quantidade = quantidade,
                        created_by = created_by
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun updateLastMessage(message: NewMessage) {
        _chatList.update { currentList ->
            currentList?.toMutableList()?.apply {
                val index = indexOfFirst { it.id == message.id_chat }
                if (index != -1) {
                    val updatedChat = this[index].copy(
                        ultimaMensagem = message.mensagem,
                        createdAt = message.created_at,
                        quantidade = message.quantidade
                    )
                    set(index, updatedChat)
                }
            }
        }
    }

    fun removeMessageListener() {
        socketRepository.removeNewMessageListener()
    }

    fun leaveChat(idChat: Int, idUser: Int, socketId: String) {
        val params = JSONObject().apply {
            put("id_chat", idChat)
            put("id_usuario", idUser)
            put("id_socket", socketId)
        }
        socketRepository.leaveChat(params)
    }
}
