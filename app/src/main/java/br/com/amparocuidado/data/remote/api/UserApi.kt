package br.com.amparocuidado.data.remote.api

import br.com.amparocuidado.domain.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {

    @GET("pessoas/by-cd-pessoa-fisica/{id}")
    suspend fun getUser(
        @Path("id") cdPessoaFisica: String
    ): Response<User>
}