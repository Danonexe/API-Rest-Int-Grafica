package viewmodel

import api.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.Tarea

sealed class CreateTareaState {
    object Idle : CreateTareaState()
    object Loading : CreateTareaState()
    object Success : CreateTareaState()
    data class Error(val message: String) : CreateTareaState()
}

class CreateTareasViewModel {
    private val _createTareaState = MutableStateFlow<CreateTareaState>(CreateTareaState.Idle)
    val createTareaState: StateFlow<CreateTareaState> = _createTareaState.asStateFlow()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun crearTarea(tarea: Tarea) {
        coroutineScope.launch {
            _createTareaState.value = CreateTareaState.Loading
            try {
                val result = ApiClient.createTarea(tarea)
                result.fold(
                    onSuccess = {
                        _createTareaState.value = CreateTareaState.Success
                    },
                    onFailure = { error ->
                        _createTareaState.value = CreateTareaState.Error(error.message ?: "Error desconocido")
                    }
                )
            } catch (e: Exception) {
                _createTareaState.value = CreateTareaState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}