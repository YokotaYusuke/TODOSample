package com.example.server.todo

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "noside")
data class TodoRecord(
    @Id
    val id: UUID = UUID.randomUUID(),
    val todo: String = ""
)
