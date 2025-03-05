package viewmodel

import api.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class CompletarTareaState {
    object Idle : CompletarTareaState()
    object Loading : CompletarTareaState()
    object Success : CompletarTareaState()
    data class Error(val message: String) : CompletarTareaState()
}

class CompletarTareaViewModel {
    private val _completarTareaState = MutableStateFlow<CompletarTareaState>(CompletarTareaState.Idle)
    val completarTareaState: StateFlow<CompletarTareaState> = _completarTareaState.asStateFlow()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun completarTarea(idTarea: String) {
        coroutineScope.launch {
            _completarTareaState.value = CompletarTareaState.Loading
            try {
                val result = ApiClient.completarTarea(idTarea)
                result.fold(
                    onSuccess = {
                        _completarTareaState.value = CompletarTareaState.Success
                    },
                    onFailure = { error ->
                        _completarTareaState.value = CompletarTareaState.Error(error.message ?: "Error desconocido")
                    }
                )
            } catch (e: Exception) {
                _completarTareaState.value = CompletarTareaState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}