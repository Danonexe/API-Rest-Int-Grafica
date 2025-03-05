package viewmodel

import api.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class EliminarTareaState {
    object Idle : EliminarTareaState()
    object Loading : EliminarTareaState()
    object Success : EliminarTareaState()
    data class Error(val message: String) : EliminarTareaState()
}

class EliminarTareaViewModel {
    private val _eliminarTareaState = MutableStateFlow<EliminarTareaState>(EliminarTareaState.Idle)
    val eliminarTareaState: StateFlow<EliminarTareaState> = _eliminarTareaState.asStateFlow()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun eliminarTarea(idTarea: String) {
        coroutineScope.launch {
            _eliminarTareaState.value = EliminarTareaState.Loading
            try {
                val result = ApiClient.eliminarTarea(idTarea)
                result.fold(
                    onSuccess = {
                        _eliminarTareaState.value = EliminarTareaState.Success
                    },
                    onFailure = { error ->
                        _eliminarTareaState.value = EliminarTareaState.Error(error.message ?: "Error desconocido")
                    }
                )
            } catch (e: Exception) {
                _eliminarTareaState.value = EliminarTareaState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}