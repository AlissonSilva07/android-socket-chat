package br.com.amparocuidado.data.mapper

import br.com.amparocuidado.data.remote.dto.chat.ChatResponse
import br.com.amparocuidado.domain.model.Chat

fun ChatResponse.toChat(): Chat {
    return Chat(
        id = id,
        nomePaciente = nome_paciente,
        ultimaMensagem = ultima_mensagem,
        ultimoEnvio = ultimo_envio,
        createdAt = created_at,
        visualizada = visualizada,
        quantidade = quantidade,
        nomeEnfermeiro = nome_enfermeiro,
        situacaoChat = situacao_chat,
        idBoletim = id_boletim,
        idEnfermeiro = id_enfermeiro
    )
}