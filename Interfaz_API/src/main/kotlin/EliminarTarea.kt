package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viewmodel.EliminarTareaViewModel
import viewmodel.EliminarTareaState

@Composable
fun EliminarTarea() {
    val viewModel = remember { EliminarTareaViewModel() }
    val eliminarTareaState by viewModel.eliminarTareaState.collectAsState()

    var idTarea by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    LaunchedEffect(eliminarTareaState) {
        when (val state = eliminarTareaState) {
            is EliminarTareaState.Success -> {
                dialogMessage = "Tarea eliminada exitosamente"
                showDialog = true
                // Limpiar campo después de éxito
                idTarea = ""
            }
            is EliminarTareaState.Error -> {
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
                Text("Eliminar Tarea", style = MaterialTheme.typography.h6)

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
                        viewModel.eliminarTarea(idTarea)
                    },
                    enabled = eliminarTareaState !is EliminarTareaState.Loading
                ) {
                    if (eliminarTareaState is EliminarTareaState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colors.onPrimary
                        )
                    } else {
                        Text("Eliminar Tarea")
                    }
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Eliminar Tarea") },
                text = { Text(dialogMessage) },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}