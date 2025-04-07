package br.com.amparocuidado.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.amparocuidado.data.remote.dto.LoginRequest
import br.com.amparocuidado.data.remote.dto.LoginResponse
import br.com.amparocuidado.data.utils.Resource
import br.com.amparocuidado.data.utils.TokenManager
import br.com.amparocuidado.domain.repository.AuthRepository
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
    tokenManager: TokenManager
): ViewModel() {

    val authToken: StateFlow<String?> = tokenManager.authToken.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        null
    )

    private val _loginResponse = MutableStateFlow<Resource<LoginResponse>>(Resource.Idle)
    val loginResponse = _loginResponse.asStateFlow()

    private val _usuario = MutableStateFlow<String>("")
    val usuario = _usuario.asStateFlow()

    private val _senha = MutableStateFlow<String>("")
    val senha = _senha.asStateFlow()

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
            } catch (e: Exception) {
                _loginResponse.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }
}