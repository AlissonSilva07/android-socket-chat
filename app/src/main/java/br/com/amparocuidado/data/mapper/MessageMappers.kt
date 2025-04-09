package br.com.amparocuidado.data.mapper

import br.com.amparocuidado.data.remote.dto.chat.MessagesByChatIdResponse
import br.com.amparocuidado.domain.model.Message
import br.com.amparocuidado.domain.model.NewMessageInChat

fun MessagesByChatIdResponse.toMessage(): Message {
    return Message(
        id = id,
        mensagem = mensagem,
        idChat = id_chat,
        nome = nome ?: "Conversa",
        createdBy = created_by,
        updatedBy = updated_by,
        deletedBy = deleted_by,
        createdAt = created_at,
        updatedAt = updated_at,
        deletedAt = deleted_at,
        image = image,
        mimetype = mimetype,
        originalname = originalname,
        arquivo = arquivo,
        pdf = pdf,
        type = type
    )
}

fun NewMessageInChat.toMessage(): Message {
    return Message(
        id = idMensagem,
        mensagem = mensagem,
        idChat = idChat,
        nome = nome,
        createdBy = createdBy,
        updatedBy = null,
        deletedBy = null,
        createdAt = createdAt,
        updatedAt = null,
        deletedAt = null,
        image = null,
        mimetype = file?.fileType,
        originalname = file?.fileName,
        arquivo = file?.fileContent,
        pdf = null,
        type = null
    )
}