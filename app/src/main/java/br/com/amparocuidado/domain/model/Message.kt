package br.com.amparocuidado.domain.model

data class Message(
    val idMensagem: Int,
    val mensagem: String,
    val idChat: Int,
    val createdBy: Int,
    val createdAt: String,
    val nome: String
)
