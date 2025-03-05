package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import viewmodel.TareasViewModel
import viewmodel.TareasState

@Composable
fun getTareas() {
    val viewModel = remember { TareasViewModel() }
    val tareasState by viewModel.tareasState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    LaunchedEffect(tareasState) {
        when (val state = tareasState) {
            is TareasState.Success -> {
                dialogMessage = state.tareas
                showDialog = true
            }
            is TareasState.Error -> {
                dialogMessage = state.message
                showDialog = true
            }
            else -> {}
        }
    }

    MaterialTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { viewModel.obtenerTareas() },
                    enabled = tareasState !is TareasState.Loading
                ) {
                    if (tareasState is TareasState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colors.onPrimary
                        )
                    } else {
                        Text("Obtener Tareas")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { }) {
                    Text("Volver")
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Tareas") },
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