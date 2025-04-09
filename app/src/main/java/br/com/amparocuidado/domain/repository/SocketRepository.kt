package br.com.amparocuidado.domain.repository

import org.json.JSONObject

interface SocketRepository {
    fun joinChat(params: JSONObject)
    fun sendMessage(params: JSONObject)
    fun viewMessage(params: JSONObject)
    fun onNewMessage(callback: (JSONObject) -> Unit)
    fun removeNewMessageListener()
    fun onNewMessageInChat(callback: (JSONObject) -> Unit)
    fun removeNewMessageInChatListener()
    fun leaveChat(params: JSONObject)
}
