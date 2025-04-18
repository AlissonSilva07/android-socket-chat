package br.com.amparocuidado.data.remote.api

import br.com.amparocuidado.data.remote.dto.login.LoginRequest
import br.com.amparocuidado.data.remote.dto.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}