package br.com.amparocuidado.domain.repository

import br.com.amparocuidado.data.remote.dto.chat.GetChatsByPacienteResponseDto
import br.com.amparocuidado.data.utils.Resource

interface ChatRepository {

    suspend fun getChatsByPaciente(
        idUsuario: Int,
        situacaoChat: String,
        page: Int,
        size: Int
    ): Resource<GetChatsByPacienteResponseDto>
}