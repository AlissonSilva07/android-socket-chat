package br.com.amparocuidado.data.mapper

import br.com.amparocuidado.data.local.chats.ChatEntity
import br.com.amparocuidado.data.remote.dto.chat.ChatResponse
import br.com.amparocuidado.domain.model.Chat

fun ChatResponse.toChat(): Chat {
    return Chat(
        id = id,
        nomePaciente = nome_paciente,
        ultimaMensagem = ultima_mensagem ?: "Sem mensagens.",
        ultimoEnvio = ultimo_envio,
        createdAt = created_at,
        visualizada = visualizada,
        quantidade = quantidade,
        nomeEnfermeiro = nome_enfermeiro,
        situacaoChat = situacao_chat,
        idBoletim = id_boletim,
        idEnfermeiro = id_enfermeiro,
        nomeUltimoEnvio = null
    )
}

fun ChatResponse.toEntity(): ChatEntity {
    return ChatEntity(
        id = id,
        nomePaciente = nome_paciente,
        ultimaMensagem = ultima_mensagem ?: "Sem mensagens.",
        ultimoEnvio = ultimo_envio,
        createdAt = created_at,
        visualizada = visualizada,
        quantidade = quantidade,
        nomeEnfermeiro = nome_enfermeiro,
        situacaoChat = situacao_chat,
        idBoletim = id_boletim,
        idEnfermeiro = id_enfermeiro,
        nomeUltimoEnvio = null
    )
}

fun ChatEntity.toChat(): Chat {
    return Chat(
        id = id,
        nomePaciente = nomePaciente,
        ultimaMensagem = ultimaMensagem,
        ultimoEnvio = ultimoEnvio,
        createdAt = createdAt,
        visualizada = visualizada,
        quantidade = quantidade,
        nomeEnfermeiro = nomeEnfermeiro,
        situacaoChat = situacaoChat,
        idBoletim = idBoletim,
        idEnfermeiro = idEnfermeiro,
        nomeUltimoEnvio = nomeUltimoEnvio,
    )
}

fun ChatEntity.toChatResponse(): ChatResponse {
    return ChatResponse(
        id = id,
        nome_paciente = nomePaciente,
        ultima_mensagem = ultimaMensagem ?: "Sem conversas",
        ultimo_envio = ultimoEnvio,
        created_at = createdAt,
        visualizada = visualizada.toString(),
        quantidade = quantidade,
        nome_enfermeiro = nomeEnfermeiro,
        situacao_chat = situacaoChat,
        id_boletim = idBoletim,
        id_enfermeiro = idEnfermeiro
    )
}