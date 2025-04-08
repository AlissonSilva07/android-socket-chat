package br.com.amparocuidado.data.remote.dto.chat

data class GetChatsByPacienteResponseDto(
    val page: Int,
    val size: Int,
    val total:Int,
    val data: List<ChatResponse>
)
