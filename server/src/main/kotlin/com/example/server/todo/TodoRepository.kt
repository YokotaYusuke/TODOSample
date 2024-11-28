package com.example.server.todo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TodoRepository: JpaRepository<TodoRecord, UUID> {
}