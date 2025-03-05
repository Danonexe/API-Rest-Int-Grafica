package model

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Tarea(
    val _id : String?,
    val autor : String?,
    val objetivo : String?,
    val fecha : Date?,
    val completada: Boolean = false
)