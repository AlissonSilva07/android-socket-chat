package br.com.amparocuidado.data.local.chats

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class ChatEntity(
    @PrimaryKey val localId: String = UUID.randomUUID().toString(),
    val createdAt: String,
    val id: Int,
    val idBoletim: Int,
    val idEnfermeiro: Int,
    val nomeEnfermeiro: String,
    val nomePaciente: String,
    val quantidade: Int = 0,
    val situacaoChat: String,
    val ultimaMensagem: String?,
    val ultimoEnvio: Int,
    val visualizada: String?,
    val nomeUltimoEnvio: String?
)