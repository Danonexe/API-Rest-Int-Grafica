package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viewmodel.CompletarTareaViewModel
import viewmodel.CompletarTareaState

@Composable
fun CompletarTarea() {
    val viewModel = remember { CompletarTareaViewModel() }
    val completarTareaState by viewModel.completarTareaState.collectAsState()

    var idTarea by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    LaunchedEffect(completarTareaState) {
        when (val state = completarTareaState) {
            is CompletarTareaState.Success -> {
                dialogMessage = "Tarea completada exitosamente"
                showDialog = true
                // Limpiar campo después de éxito
                idTarea = ""
            }
            is CompletarTareaState.Error -> {
                dialogMessage = state.message
                showDialog = true
            }
            else -> {}
        }
    }

    MaterialTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Completar Tarea", style = MaterialTheme.typography.h6)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = idTarea,
                    onValueChange = { idTarea = it },
                    label = { Text("ID de Tarea") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.completarTarea(idTarea)
                    },
                    enabled = completarTareaState !is CompletarTareaState.Loading
                ) {
                    if (completarTareaState is CompletarTareaState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colors.onPrimary
                        )
                    } else {
                        Text("Marcar como Completada")
                    }
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Completar Tarea") },
                text = { Text("200 OK") },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}