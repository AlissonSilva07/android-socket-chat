package br.com.amparocuidado.di

import br.com.amparocuidado.data.socket.SocketManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SocketModule {

    @Provides
    @Singleton
    fun provideSocketManager(): SocketManager {
        val socketManager = SocketManager()
        socketManager.initSocket()
        return socketManager
    }
}
