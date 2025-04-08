package br.com.amparocuidado.domain.repository

import br.com.amparocuidado.data.remote.dto.login.LoginRequest
import br.com.amparocuidado.data.remote.dto.login.LoginResponse
import br.com.amparocuidado.data.utils.Resource

interface AuthRepository {
    suspend fun logIn(request: LoginRequest): Resource<LoginResponse>
}