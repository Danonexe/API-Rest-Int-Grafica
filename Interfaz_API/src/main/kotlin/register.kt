
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import states.RegistrationState
import viewmodels.RegisterViewModel

@Composable
fun register() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var calle by remember { mutableStateOf("") }
    var num by remember { mutableStateOf("") }
    var municipio by remember { mutableStateOf("") }
    var provincia by remember { mutableStateOf("") }
    var cp by remember { mutableStateOf("") }

    val viewModel = remember { RegisterViewModel() }
    val registrationState by viewModel.registrationState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    LaunchedEffect(registrationState) {
        when (registrationState) {
            is RegistrationState.Success -> {
                dialogMessage = "Registro exitoso"
                showDialog = true
            }
            is RegistrationState.Error -> {
                dialogMessage = (registrationState as RegistrationState.Error).message
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
                Text("Registro de Usuario", fontSize = 32.sp)
                Spacer(modifier = Modifier.height(40.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Usuario") }
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = calle,
                    onValueChange = { calle = it },
                    label = { Text("Calle") }
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = num,
                    onValueChange = { num = it },
                    label = { Text("Número") }
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = municipio,
                    onValueChange = { municipio = it },
                    label = { Text("Municipio") }
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = provincia,
                    onValueChange = { provincia = it },
                    label = { Text("Provincia") }
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = cp,
                    onValueChange = { cp = it },
                    label = { Text("Código Postal") }
                )
                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        viewModel.register(
                            username,
                            password,
                            calle,
                            num,
                            municipio,
                            provincia,
                            cp
                        )
                    },
                    enabled = registrationState !is RegistrationState.Loading
                ) {
                    if (registrationState is RegistrationState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colors.onPrimary
                        )
                    } else {
                        Text("Registrarte")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { /* Implementar navegación hacia atrás */ }) {
                    Text("Atrás")
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Mensaje") },
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