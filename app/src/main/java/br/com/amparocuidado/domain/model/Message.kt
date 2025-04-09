package br.com.amparocuidado.domain.model

import java.util.UUID

data class Message(
    val id: Int?,
    val localId: String = UUID.randomUUID().toString(),
    val mensagem: String? = null,
    val idChat: Int,
    val nome: String?,
    val createdBy: Int? = null,
    val updatedBy: Int? = null,
    val deletedBy: Int? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val deletedAt: String? = null,
    val image: String? = null,
    val mimetype: String? = null,
    val originalname: String? = null,
    val arquivo: String? = null,
    val pdf: ByteArray? = null,
    val type: String? = null,
    val status: MessageStatus = MessageStatus.PENDING,
)

enum class MessageStatus {
    PENDING, SENT, FAILED
}
