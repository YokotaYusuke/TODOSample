package com.example.server

import com.example.server.todo.TodoService

class SpyTodoService: TodoService {
    var getTodos_wasCalled: Boolean = false
    override fun getTodos(): List<String> {
        getTodos_wasCalled = true
        return emptyList()
    }

    var saveTodo_argument_todo: String? = null
    override fun saveTodo(todo: String) {
        saveTodo_argument_todo = todo
    }
}

class StubTodoService: TodoService {
    var getTodos_returnValue:List<String> = emptyList()
    override fun getTodos(): List<String> {
        return getTodos_returnValue
    }

    override fun saveTodo(todo: String) {
    }
}