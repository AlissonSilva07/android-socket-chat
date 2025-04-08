package br.com.amparocuidado.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.amparocuidado.data.remote.dto.login.LoginRequest
import br.com.amparocuidado.data.remote.dto.login.LoginResponse
import br.com.amparocuidado.data.utils.Resource
import br.com.amparocuidado.data.utils.TokenManager
import br.com.amparocuidado.data.utils.UserManager
import br.com.amparocuidado.domain.model.User
import br.com.amparocuidado.domain.repository.AuthRepository
import br.com.amparocuidado.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val tokenManager: TokenManager,
    private val userManager: UserManager
) : ViewModel() {

    val authToken: StateFlow<String?> = tokenManager.authToken.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        null
    )

    val userData: StateFlow<User?> = userManager.userFlow.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        null
    )

    private val _loginResponse = MutableStateFlow<Resource<LoginResponse>>(Resource.Idle)
    val loginResponse: StateFlow<Resource<LoginResponse>> = _loginResponse.asStateFlow()

    private val _userResponse = MutableStateFlow<Resource<User>>(Resource.Idle)
    val userResponse: StateFlow<Resource<User>> = _userResponse.asStateFlow()

    private val _usuario = MutableStateFlow("")
    val usuario: StateFlow<String> = _usuario.asStateFlow()

    private val _senha = MutableStateFlow("")
    val senha: StateFlow<String> = _senha.asStateFlow()

    fun updateUsuario(value: String) {
        _usuario.value = value
    }

    fun updateSenha(value: String) {
        _senha.value = value
    }

    fun logIn() {
        viewModelScope.launch {
            _loginResponse.value = Resource.Loading
            try {
                val response = authRepository.logIn(
                    LoginRequest(usuario = _usuario.value, senha = _senha.value)
                )
                _loginResponse.value = response

                if (response is Resource.Success) {
                    val token = response.data.accessToken
                    tokenManager.updateAuthToken(token)
                    getUser(response.data.cdPessoaFisica)
                }
            } catch (e: Exception) {
                _loginResponse.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun getUser(cdPessoaFisica: String) {
        viewModelScope.launch {
            _userResponse.value = Resource.Loading
            try {
                val response = userRepository.getUser(
                    cdPessoaFisica = cdPessoaFisica
                )
                _userResponse.value = response
            } catch (e: Exception) {
                _userResponse.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }
}
