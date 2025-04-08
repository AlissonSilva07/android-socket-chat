package br.com.amparocuidado.domain.model

data class NewMessage(
    val nome: String,
    val mensagem: String,
    val created_by: Int,
    val created_at: String,
    val id_chat: Int,
    val quantidade: Int,
)
