package br.com.amparocuidado.domain.repository

import br.com.amparocuidado.data.utils.Resource
import br.com.amparocuidado.domain.model.User

interface UserRepository {

    suspend fun getUser(cdPessoaFisica: String): Resource<User>
}