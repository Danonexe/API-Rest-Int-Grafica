import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import viewmodel.LoginState
import viewmodel.LoginViewModel


@Composable
fun login() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val viewModel = remember { LoginViewModel() }
    val loginState by viewModel.loginState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                dialogMessage = "Inicio de sesión exitoso"
                showDialog = true
            }
            is LoginState.Error -> {
                dialogMessage = (loginState as LoginState.Error).message
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
                Text("API Tareas", fontSize = 32.sp)
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
                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { viewModel.login(username, password) },
                    enabled = loginState !is LoginState.Loading
                ) {
                    if (loginState is LoginState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colors.onPrimary
                        )
                    } else {
                        Text("Iniciar sesión")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { println("Registrarte") }) {
                    Text("Registrarte")
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { println("Atrás") }) {
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
