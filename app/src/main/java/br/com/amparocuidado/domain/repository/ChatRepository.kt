package br.com.amparocuidado.domain.repository

import br.com.amparocuidado.data.remote.dto.chat.GetChatsByPacienteResponseDto
import br.com.amparocuidado.data.remote.dto.chat.MessagesByChatIdResponse
import br.com.amparocuidado.data.utils.Resource
import br.com.amparocuidado.domain.model.Chat
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    suspend fun getCachedChats(): Flow<List<Chat>>

    suspend fun getMessagesByChatId(idChat: Int): Resource<List<MessagesByChatIdResponse>>

    suspend fun getChatImagesByUrl(fileName: String): Resource<ByteArray>

    suspend fun postChatImages(form: Triple<MultipartBody.Part, RequestBody, RequestBody>): Resource<Unit>
}