package com.es.aplicacion.model

import org.bson.codecs.pojo.annotations.BsonId
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("Tareas")
data class Tarea (
    @BsonId
    val _id : String?,
    val autor : String?,
    val objetivo : String?,
    val fecha : Date?,
    )