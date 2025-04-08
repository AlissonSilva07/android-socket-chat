package br.com.amparocuidado.presentation.ui.chatlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.amparocuidado.data.mapper.toChat
import br.com.amparocuidado.data.remote.dto.chat.GetChatsByPacienteResponseDto
import br.com.amparocuidado.data.utils.Resource
import br.com.amparocuidado.domain.model.Chat
import br.com.amparocuidado.domain.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val chatRepository: ChatRepository
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
                } else {
                    _chatList.value = null
                }
                _chatResponse.value = response
            } catch (e: Exception) {
                _chatResponse.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }
}