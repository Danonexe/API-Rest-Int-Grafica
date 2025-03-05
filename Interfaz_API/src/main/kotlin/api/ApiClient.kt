package api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.client.statement.bodyAsText
import model.Register
import model.ErrorResponse
import com.google.gson.Gson
import model.Tarea

object ApiClient {
    private const val BASE_URL = "http://localhost:8080"
    private val gson = Gson()

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            gson()
        }
    }

    suspend fun registerUser(request: Register): Result<Unit> {
        return try {
            val response = client.post("$BASE_URL/usuarios/register") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }

            if (response.status.isSuccess()) {
                Result.success(Unit)
            } else {
                val errorBody = response.bodyAsText()
                val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                Result.failure(Exception(errorResponse.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginUser(username: String, password: String): Result<Unit> {
        return try {
            val response = client.post("$BASE_URL/usuarios/login") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("username" to username, "password" to password))
            }

            if (response.status.isSuccess()) {
                Result.success(Unit)
            } else {
                val errorBody = response.bodyAsText()
                val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                Result.failure(Exception(errorResponse.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTareas(): Result<String> {
        return try {
            val response = client.get("$BASE_URL/tareas")

            if (response.status.isSuccess()) {
                Result.success(response.bodyAsText())
            } else {
                val errorBody = response.bodyAsText()
                val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                Result.failure(Exception(errorResponse.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createTarea(tarea: Tarea): Result<Unit> {
        return try {
            val response = client.post("$BASE_URL/tareas") {
                contentType(ContentType.Application.Json)
                setBody(tarea)
            }

            if (response.status.isSuccess()) {
                Result.success(Unit)
            } else {
                val errorBody = response.bodyAsText()
                val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                Result.failure(Exception(errorResponse.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun completarTarea(idTarea: String): Result<Unit> {
        return try {
            val response = client.put("$BASE_URL/tareas/$idTarea/completar")

            if (response.status.isSuccess()) {
                Result.success(Unit)
            } else {
                val errorBody = response.bodyAsText()
                val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                Result.failure(Exception(errorResponse.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun eliminarTarea(idTarea: String): Result<Unit> {
        return try {
            val response = client.delete("$BASE_URL/tareas/$idTarea")

            if (response.status.isSuccess()) {
                Result.success(Unit)
            } else {
                val errorBody = response.bodyAsText()
                val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                Result.failure(Exception(errorResponse.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}