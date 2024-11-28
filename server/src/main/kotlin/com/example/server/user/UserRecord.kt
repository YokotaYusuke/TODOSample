package com.example.server.user

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Entity(name = "users")
data class UserRecord(
    @Id
    val id: UUID = UUID.randomUUID(),
    val username: String,
    val subject: String
)
