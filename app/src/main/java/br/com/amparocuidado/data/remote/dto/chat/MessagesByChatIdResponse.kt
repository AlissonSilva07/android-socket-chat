package br.com.amparocuidado.data.remote.dto.chat

data class MessagesByChatIdResponse (
    val id: Int,
    val mensagem: String? = null,
    val id_chat: Int,
    val nome: String,
    val created_by: Int? = null,
    val updated_by: Int? = null,
    val deleted_by: Int? = null,
    val created_at: String? = null,
    val updated_at: String? = null,
    val deleted_at: String? = null,
    val image: String? = null,
    val mimetype: String? = null,
    val originalname: String? = null,
    val arquivo: String? = null,
    val pdf: ByteArray? = null,
    val type: String? = null
)