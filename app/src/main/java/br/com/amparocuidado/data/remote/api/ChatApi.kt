package br.com.amparocuidado.data.remote.api

import br.com.amparocuidado.data.remote.dto.chat.GetChatsByPacienteResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatApi {

    @GET("chat")
    suspend fun getChatsByPaciente(
        @Query("id_usuario") idUsuario: Int,
        @Query("situacao_chat") situacaoChat: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<GetChatsByPacienteResponseDto>
}