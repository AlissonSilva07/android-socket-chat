package br.com.amparocuidado.domain.model

data class NewMessageInChat(
    val idMensagem: Int,
    val mensagem: String?,
    val idChat: Int,
    val createdBy: Int,
    val createdAt: String,
    val nome: String,
    val sender: String?,
    val file: FileData?
)

data class FileData(
    val fileName: String,
    val fileType: String,
    val fileContent: String
)