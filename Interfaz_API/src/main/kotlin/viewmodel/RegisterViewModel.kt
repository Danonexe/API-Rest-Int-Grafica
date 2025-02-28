package viewmodels

import api.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import model.Direccion
import model.Register
import states.RegistrationState

class RegisterViewModel {
    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Idle)
    val registrationState: StateFlow<RegistrationState> = _registrationState.asStateFlow()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private fun processErrorMessage(message: String): String {
        return message.replace("BadRequestException:", "")
            .trim()
            .removeSurrounding("\"")
    }

    fun register(
        username: String,
        password: String,
        calle: String,
        num: String,
        municipio: String,
        provincia: String,
        cp: String
    ) {
        coroutineScope.launch {
            try {
                _registrationState.value = RegistrationState.Loading

                val request = Register(
                    username = username,
                    password = password,
                    passwordRepeat = password,
                    direccion = Direccion(
                        calle = calle,
                        num = num,
                        municipio = municipio,
                        provincia = provincia,
                        cp = cp
                    )
                )

                val result = ApiClient.registerUser(request)
                result.fold(
                    onSuccess = {
                        _registrationState.value = RegistrationState.Success
                    },
                    onFailure = { error ->
                        // Aquí procesamos el mensaje de error
                        val errorMessage = processErrorMessage(error.message ?: "Error desconocido")
                        _registrationState.value = RegistrationState.Error(errorMessage)
                    }
                )
            } catch (e: Exception) {
                // Y aquí también
                val errorMessage = processErrorMessage(e.message ?: "Error desconocido")
                _registrationState.value = RegistrationState.Error(errorMessage)
            }
        }
    }
}