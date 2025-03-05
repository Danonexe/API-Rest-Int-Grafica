package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viewmodel.CreateTareasViewModel
import viewmodel.CreateTareaState
import model.Tarea
import java.util.*

@Composable
fun CreateTarea() {
    val viewModel = remember { CreateTareasViewModel() }
    val createTareaState by viewModel.createTareaState.collectAsState()

    var autor by remember { mutableStateOf("") }
    var objetivo by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    LaunchedEffect(createTareaState) {
        when (val state = createTareaState) {
            is CreateTareaState.Success -> {
                dialogMessage = "Tarea creada exitosamente"
                showDialog = true
                // Limpiar campos después de éxito
                autor = ""
                objetivo = ""
            }
            is CreateTareaState.Error -> {
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
                Text("Crear Nueva Tarea", style = MaterialTheme.typography.h6)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = autor,
                    onValueChange = { autor = it },
                    label = { Text("Fecha") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = objetivo,
                    onValueChange = { objetivo = it },
                    label = { Text("Objetivo") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val nuevaTarea = Tarea(
                            _id = null,
                            autor = autor,
                            objetivo = objetivo,
                            fecha = Date(),
                            completada = false
                        )
                        viewModel.crearTarea(nuevaTarea)
                    },
                    enabled = createTareaState !is CreateTareaState.Loading
                ) {
                    if (createTareaState is CreateTareaState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colors.onPrimary
                        )
                    } else {
                        Text("Crear Tarea")
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
                title = { Text("Crear Tarea") },
                text = { Text("201 Created") },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}