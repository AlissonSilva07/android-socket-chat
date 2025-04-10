package br.com.amparocuidado.domain.repository

import br.com.amparocuidado.data.remote.dto.chat.GetChatsByPacienteResponseDto
import br.com.amparocuidado.data.remote.dto.chat.MessagesByChatIdResponse
import br.com.amparocuidado.data.utils.Resource
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.InputStream

interface ChatRepository {

    suspend fun getChatsByPaciente(
        idUsuario: Int,
        situacaoChat: String,
        page: Int,
        size: Int
    ): Resource<GetChatsByPacienteResponseDto>

    suspend fun getMessagesByChatId(idChat: Int): Resource<List<MessagesByChatIdResponse>>

    suspend fun getChatImagesByUrl(fileName: String): Resource<ByteArray>
}