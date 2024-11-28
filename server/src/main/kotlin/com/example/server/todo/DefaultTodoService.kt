package com.example.server.todo

import org.springframework.stereotype.Service

interface TodoService {
    fun getTodos(): List<String>
    fun saveTodo(todo: String)
}

@Service
class DefaultTodoService(
    private val todoRepository: TodoRepository,
): TodoService {
    override fun getTodos(): List<String> {
        val todos = todoRepository.findAll()
        return todos.map { it.todo }
    }

    override fun saveTodo(todo: String) {
        todoRepository.save(TodoRecord(todo = todo))
    }
}