package br.com.amparocuidado.data.repository

import br.com.amparocuidado.data.socket.SocketManager
import br.com.amparocuidado.domain.repository.SocketRepository
import org.json.JSONObject
import javax.inject.Inject

class SocketRepositoryImpl @Inject constructor(
    private val socketManager: SocketManager
): SocketRepository {
    override fun joinChat(params: JSONObject) {
        socketManager.getSocket().emit("join_chat", params)
    }

    override fun viewMessage(params: JSONObject) {
        socketManager.getSocket().emit("view_message", params)
    }

    override fun onNewMessage(callback: (JSONObject) -> Unit) {
        socketManager.getSocket().on("new_message") { args ->
            val data = args[0] as JSONObject
            callback(data)
        }
    }

    override fun removeNewMessageListener() {
        socketManager.getSocket().off("new_message")
    }

    override fun leaveChat(params: JSONObject) {
        socketManager.getSocket().emit("leave_chat", params)
    }
}