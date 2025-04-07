package br.com.amparocuidado.domain.repository

import br.com.amparocuidado.data.remote.dto.LoginRequest
import br.com.amparocuidado.data.remote.dto.LoginResponse
import br.com.amparocuidado.data.utils.Resource

interface AuthRepository {
    suspend fun logIn(request: LoginRequest): Resource<LoginResponse>
}