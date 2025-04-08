package br.com.amparocuidado.domain.repository

import org.json.JSONObject

interface SocketRepository {
    fun joinChat(params: JSONObject)
    fun viewMessage(params: JSONObject)
    fun onNewMessage(callback: (JSONObject) -> Unit)
    fun removeNewMessageListener()
    fun leaveChat(params: JSONObject)
}
