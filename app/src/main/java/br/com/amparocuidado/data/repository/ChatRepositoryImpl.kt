package br.com.amparocuidado.data.repository

import android.util.Log
import br.com.amparocuidado.data.local.chats.ChatDao
import br.com.amparocuidado.data.mapper.toChat
import br.com.amparocuidado.data.mapper.toEntity
import br.com.amparocuidado.data.remote.api.ChatApi
import br.com.amparocuidado.data.remote.dto.chat.GetChatsByPacienteResponseDto
import br.com.amparocuidado.data.remote.dto.chat.MessagesByChatIdResponse
import br.com.amparocuidado.data.utils.Resource
import br.com.amparocuidado.domain.model.Chat
import br.com.amparocuidado.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatApi: ChatApi,
    private val chatDao: ChatDao
): ChatRepository {
    override suspend fun getChatsByPaciente(
        idUsuario: Int,
        situacaoChat: String,
        page: Int,
        size: Int
    ): Resource<GetChatsByPacienteResponseDto> {
        return try {
            val response = chatApi.getChatsByPaciente(
                idUsuario = idUsuario,
                situacaoChat = situacaoChat,
                page = page,
                size = size
            )
            if (response.isSuccessful && response.body() != null) {
                val data = response.body()!!

                val chats = data.data.map { it.toEntity() }

                chatDao.clearChats()
                chatDao.insertAll(chats)

                Resource.Success(data)
            } else if (response.errorBody() != null) {
                val errorMessage = response.errorBody()!!.charStream().readText()
                Resource.Error(errorMessage)
            } else {
                Resource.Error("An unknown error occurred")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun getCachedChats(): Flow<List<Chat>> {
        return chatDao.getChats().map { chat ->
            chat.map { it.toChat() }
        }
    }

    override suspend fun getMessagesByChatId(idChat: Int): Resource<List<MessagesByChatIdResponse>> {
        return try {
            val response = chatApi.getMessagesByChatId(idChat)
            if (response.isSuccessful && response.body() != null) {
                val data = response.body()!!
                Resource.Success(data)
            } else if (response.errorBody() != null) {
                val errorMessage = response.errorBody()!!.charStream().readText()
                Log.e("API_RESPONSE", errorMessage)
                Resource.Error(errorMessage)
            } else {
                Resource.Error("An unknown error occurred")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun getChatImagesByUrl(fileName: String): Resource<ByteArray> {
        return try {
            val response = chatApi.getChatImagesByUrl(fileName)
            if (response.isSuccessful) {
                val bytes = response.body()?.bytes()
                if (bytes != null) {
                    Resource.Success(bytes)
                } else {
                    Resource.Error("Empty image data")
                }
            } else {
                val error = response.errorBody()?.string() ?: "Unknown error"
                Resource.Error(error)
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun postChatImages(form: Triple<MultipartBody.Part, RequestBody, RequestBody>): Resource<Unit> {
        return try {
            val response = chatApi.postChatImage(
                file = form.first,
                mensagem = form.second,
                nome = form.third
            )
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                val error = response.errorBody()?.string() ?: "Unknown error"
                Resource.Error(error)
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
}