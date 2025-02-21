package viewmodel

import api.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun login(username: String, password: String) {
        coroutineScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val result = ApiClient.loginUser(username, password)
                result.fold(
                    onSuccess = { _loginState.value = LoginState.Success },
                    onFailure = { error -> _loginState.value = LoginState.Error(error.message ?: "Error desconocido") }
                )
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}
