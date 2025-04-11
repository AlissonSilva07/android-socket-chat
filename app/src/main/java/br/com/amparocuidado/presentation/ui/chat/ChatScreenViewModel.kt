package br.com.amparocuidado.presentation.ui.chat

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.amparocuidado.data.mapper.toMessage
import br.com.amparocuidado.data.remote.dto.chat.MessagesByChatIdResponse
import br.com.amparocuidado.data.utils.Resource
import br.com.amparocuidado.domain.model.FileData
import br.com.amparocuidado.domain.model.Message
import br.com.amparocuidado.domain.model.MessageStatus
import br.com.amparocuidado.domain.model.NewMessageInChat
import br.com.amparocuidado.domain.repository.ChatRepository
import br.com.amparocuidado.domain.repository.SocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File
import javax.inject.Inject

@OptIn(FlowPreview::class)
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

    private var currentChatId: Int? = null

    fun setChatId(chatId: String) {
        currentChatId = chatId.toInt()
    }

    var capturedImageUri by mutableStateOf<Uri?>(null)
        private set

    fun onImageCaptured(uri: Uri) {
        capturedImageUri = uri
    }

    init {
        viewModelScope.launch {
            _textMessage
                .debounce(200)
                .collectLatest { text ->
                    val isTyping = text.isNotEmpty()
                    val params = JSONObject().apply {
                        put("id_chat", currentChatId)
                        put("isTyping", isTyping)
                    }
                    socketRepository.isTyping(params)
                }
        }
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
                        loadImages(messages)
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

    private fun loadImages(messages: List<Message>) {
        viewModelScope.launch {
            val updatedMessages = messages.map { message ->
                val fileName = message.arquivo ?: return@map message
                Log.d("ChatViewModel", "Loading image for message: $fileName")
                val result = chatRepository.getChatImagesByUrl(fileName)
                if (result is Resource.Success) {
                    message.copy(image = result.data)
                } else {
                    Log.e("ChatViewModel", "Failed to load image")
                    message
                }
            }

            _messages.update {
                it.map { currentMsg ->
                    updatedMessages.find { it.id == currentMsg.id } ?: currentMsg
                }
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
        val localMessage = Message(
            id = null,
            mensagem = message,
            idChat = idChat,
            nome = nome,
            createdBy = userId,
            createdAt = createdAt,
            status = MessageStatus.PENDING
        )

        _messages.update { it + localMessage }

        val params = JSONObject().apply {
            put("id_chat", idChat)
            put("created_by", userId)
            put("created_at", createdAt)
            put("mensagem", message)
            put("nome", nome)
        }

        socketRepository.sendMessage(params)
    }

    fun postChatImage(
        file: File,
        idChat: Int,
        nome: String
    ) {
        viewModelScope.launch {
            _messagesResponse.value = Resource.Loading

            val form = createMultipartBody(file, idChat.toString(), nome)

            try {
                val response = chatRepository.postChatImages(form)
                if (response is Resource.Success) {
                    return@launch
                } else {
                    return@launch
                }
            } catch (e: Exception) {
                _messagesResponse.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun createMultipartBody(
        file: File,
        idChat: String,
        nome: String
    ): Triple<MultipartBody.Part, RequestBody, RequestBody> {
        val requestFile = file
            .asRequestBody("image/*".toMediaTypeOrNull())

        val filePart = MultipartBody.Part.createFormData(
            name = "arquivo",
            filename = file.name,
            body = requestFile
        )

        val mensagem = idChat
            .toRequestBody("text/plain".toMediaTypeOrNull())

        val nome = nome
            .toRequestBody("text/plain".toMediaTypeOrNull())

        return Triple(filePart, mensagem, nome)
    }


    fun observeMessages() {
        socketRepository.onNewMessageInChat { json ->
            try {
                val idMensagem = json.getInt("id_mensagem")
                val mensagem = if (json.has("mensagem") && !json.isNull("mensagem")) {
                    json.getString("mensagem")
                } else {
                    null
                }
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
        val newMsg = message.toMessage()

        _messages.update { currentList ->
            val matchIndex = currentList.indexOfFirst {
                it.status == MessageStatus.PENDING &&
                        it.mensagem == newMsg.mensagem &&
                        it.createdBy == newMsg.createdBy &&
                        it.createdAt == newMsg.createdAt &&
                        it.image == newMsg.image
            }

            val updatedList = currentList.toMutableList()

            if (matchIndex != -1) {
                updatedList[matchIndex] = newMsg.copy(status = MessageStatus.SENT)
            } else if (currentList.none { it.id == newMsg.id }) {
                updatedList.add(newMsg)
            }
            loadImages(updatedList)
            updatedList
        }
    }


    fun removeMessageListener() {
        socketRepository.removeNewMessageInChatListener()
    }
}