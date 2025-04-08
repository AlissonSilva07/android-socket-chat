package br.com.amparocuidado.data.socket

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.WebSocket
import java.net.URISyntaxException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocketManager @Inject constructor() {

    private lateinit var socket: Socket

    fun initSocket() {
        try {
            val opts = IO.Options().apply {
                path = "/amparo_api/socket.io"
                transports = arrayOf(WebSocket.NAME)
                reconnection = true
            }

            socket = IO.socket("https://api.tst.pep.livsaude.com.br", opts)

            socket.on(Socket.EVENT_CONNECT) {
                Log.d("SocketIO", "Connected to server")
            }

            socket.connect()

        } catch (e: URISyntaxException) {
            Log.e("SocketIO", "Socket URI error: ${e.message}")
        }
    }

    fun getSocket(): Socket = socket

    fun disconnect() {
        socket.disconnect()
    }
}
