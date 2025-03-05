package viewmodel

import api.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class TareasState {
    object Idle : TareasState()
    object Loading : TareasState()
    data class Success(val tareas: String) : TareasState()
    data class Error(val message: String) : TareasState()
}

class TareasViewModel {
    private val _tareasState = MutableStateFlow<TareasState>(TareasState.Idle)
    val tareasState: StateFlow<TareasState> = _tareasState.asStateFlow()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun obtenerTareas() {
        coroutineScope.launch {
            _tareasState.value = TareasState.Loading
            try {
                val result = ApiClient.getTareas()
                result.fold(
                    onSuccess = { tareas ->
                        _tareasState.value = TareasState.Success(tareas)
                    },
                    onFailure = { error ->
                        _tareasState.value = TareasState.Error(error.message ?: "Error desconocido")
                    }
                )
            } catch (e: Exception) {
                _tareasState.value = TareasState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}