package br.com.amparocuidado.data.remote.api

import br.com.amparocuidado.data.remote.dto.chat.GetChatsByPacienteResponseDto
import br.com.amparocuidado.data.remote.dto.chat.MessagesByChatIdResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming

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

    @Streaming
    @GET("chat/arquivo/{fileName}")
    suspend fun getChatImagesByUrl(
        @Path(value = "fileName", encoded = true) fileName: String,
    ): Response<ResponseBody>

    @Multipart
    @POST("chat/envio-arquivo")
    suspend fun postChatImage(
        @Part("arquivo") file: MultipartBody.Part,
        @Part("id_chat") mensagem: RequestBody,
        @Part("nome") nome: RequestBody
    ): Response<Unit>
}