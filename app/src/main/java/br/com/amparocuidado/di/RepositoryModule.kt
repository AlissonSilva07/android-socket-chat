package br.com.amparocuidado.di

import br.com.amparocuidado.data.local.chats.ChatDao
import br.com.amparocuidado.data.remote.api.AuthApi
import br.com.amparocuidado.data.remote.api.ChatApi
import br.com.amparocuidado.data.remote.api.UserApi
import br.com.amparocuidado.data.repository.AuthRepositoryImpl
import br.com.amparocuidado.data.repository.ChatRepositoryImpl
import br.com.amparocuidado.data.repository.SocketRepositoryImpl
import br.com.amparocuidado.data.repository.UserRepositoryImpl
import br.com.amparocuidado.data.socket.SocketManager
import br.com.amparocuidado.data.utils.TokenManager
import br.com.amparocuidado.data.utils.UserManager
import br.com.amparocuidado.domain.repository.AuthRepository
import br.com.amparocuidado.domain.repository.ChatRepository
import br.com.amparocuidado.domain.repository.SocketRepository
import br.com.amparocuidado.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApi: AuthApi,
        tokenManager: TokenManager
    ): AuthRepository = AuthRepositoryImpl(authApi, tokenManager)

    @Provides
    @Singleton
    fun provideUserRepository(
        userApi: UserApi,
        userManager: UserManager
    ): UserRepository = UserRepositoryImpl(userApi, userManager)

    @Provides
    @Singleton
    fun provideChatRepository(
        chatApi: ChatApi,
        chatDao: ChatDao
    ): ChatRepository = ChatRepositoryImpl(chatApi, chatDao)

    @Provides
    @Singleton
    fun provideSocketRepository(
        socketManager: SocketManager
    ): SocketRepository {
        return SocketRepositoryImpl(socketManager)
    }
}