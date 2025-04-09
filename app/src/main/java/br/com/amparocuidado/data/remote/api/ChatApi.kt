package br.com.amparocuidado.data.remote.api

import br.com.amparocuidado.data.remote.dto.chat.GetChatsByPacienteResponseDto
import br.com.amparocuidado.data.remote.dto.chat.MessagesByChatIdResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatApi {

    @GET("chat")
    suspend fun getChatsByPaciente(
        @Query("id_usuario") idUsuario: Int,
        @Query("situacao_chat") situacaoChat: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<GetChatsByPacienteResponseDto>

    @GET("chat/historico/{id_chat}")
    suspend fun getMessagesByChatId(
        @Path("id_chat") idChat: Int
    ): Response<List<MessagesByChatIdResponse>>
}