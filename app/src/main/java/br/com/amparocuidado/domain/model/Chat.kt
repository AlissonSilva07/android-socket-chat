package br.com.amparocuidado.domain.model

data class Chat(
    val createdAt: String,
    val id: Int,
    val idBoletim: Int,
    val idEnfermeiro: Int,
    val nomeEnfermeiro: String,
    val nomePaciente: String,
    val quantidade: Int = 0,
    val situacaoChat: String,
    val ultimaMensagem: String,
    val ultimoEnvio: Int,
    val visualizada: String,
    val nomeUltimoEnvio: String? = null
)