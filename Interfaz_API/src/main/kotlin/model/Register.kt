package model

import kotlinx.serialization.Serializable

@Serializable
data class Register(
    val username: String,
    val password: String,
    val passwordRepeat: String,
    val rol: String = "USER",
    val direccion: Direccion
)