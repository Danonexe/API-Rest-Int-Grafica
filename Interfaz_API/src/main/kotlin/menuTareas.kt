import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application


@Composable
@Preview
fun menuTareas() {
    MaterialTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Menu Tareas", fontSize = 32.sp)
                Spacer(modifier = Modifier.height(40.dp))

                Button(onClick = { }) {
                    Text("Ver Tareas")
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { }) {
                    Text("Crear Tareas")
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { }) {
                    Text("Marcar Tarea como completada")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { }) {
                    Text("Desmarcar Tarea como completada")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { }) {
                    Text("Eliminar Tarea")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}