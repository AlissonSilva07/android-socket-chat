package br.com.amparocuidado.data.repository

import android.util.Log
import br.com.amparocuidado.data.remote.api.UserApi
import br.com.amparocuidado.data.utils.Resource
import br.com.amparocuidado.data.utils.UserManager
import br.com.amparocuidado.domain.model.User
import br.com.amparocuidado.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val userManager: UserManager
): UserRepository {
    override suspend fun getUser(cdPessoaFisica: String): Resource<User> {
        return try {
            val response = userApi.getUser(cdPessoaFisica)
            if (response.isSuccessful && response.body() != null) {
                val data = response.body()!!
                userManager.saveUser(data)
                Resource.Success(data)
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