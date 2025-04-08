package br.com.amparocuidado.data.remote.dto.chat

data class ChatResponse(
    val created_at: String,
    val id: Int,
    val id_boletim: Int,
    val id_enfermeiro: Int,
    val nome_enfermeiro: String,
    val nome_paciente: String,
    val quantidade: Int,
    val situacao_chat: String,
    val ultima_mensagem: String,
    val ultimo_envio: Int,
    val visualizada: String
)