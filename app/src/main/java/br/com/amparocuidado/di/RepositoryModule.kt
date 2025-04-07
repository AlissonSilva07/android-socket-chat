package br.com.amparocuidado.di

import br.com.amparocuidado.data.remote.api.AuthApi
import br.com.amparocuidado.data.repository.AuthRepositoryImpl
import br.com.amparocuidado.data.utils.TokenManager
import br.com.amparocuidado.domain.repository.AuthRepository
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
}