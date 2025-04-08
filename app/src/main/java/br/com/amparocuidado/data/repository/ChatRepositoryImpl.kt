package br.com.amparocuidado.data.repository

import android.util.Log
import br.com.amparocuidado.data.remote.api.ChatApi
import br.com.amparocuidado.data.remote.dto.chat.GetChatsByPacienteResponseDto
import br.com.amparocuidado.data.utils.Resource
import br.com.amparocuidado.domain.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatApi: ChatApi
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


}