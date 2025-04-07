package br.com.amparocuidado.data.repository

import android.util.Log
import br.com.amparocuidado.data.remote.api.AuthApi
import br.com.amparocuidado.data.remote.dto.LoginRequest
import br.com.amparocuidado.data.remote.dto.LoginResponse
import br.com.amparocuidado.data.utils.Resource
import br.com.amparocuidado.data.utils.TokenManager
import br.com.amparocuidado.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor (
    private val authApi: AuthApi,
    private val tokenManager: TokenManager
) : AuthRepository {
    val authToken = tokenManager.authToken

    override suspend fun logIn(request: LoginRequest): Resource<LoginResponse> {
        return try {
            val response = authApi.login(request)
            if (response.isSuccessful && response.body() != null) {
                val userResponse = response.body()!!
                tokenManager.updateAuthToken(userResponse.accessToken)
                Resource.Success(userResponse)
            } else if (response.errorBody() != null) {
                val errorMessage = response.errorBody()!!.charStream().readText()
                Log.e("API_RESPONSE", errorMessage)
                Resource.Error(errorMessage)
            } else {
                Resource.Error("An unknown error occurred")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

}