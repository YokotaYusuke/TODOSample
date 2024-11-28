package com.example.server.todo

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val todoService: TodoService
) {
    @GetMapping
    fun getTodos(): List<String> {
        println("+++++++++++++++++++++++++++++++++++")
        return todoService.getTodos()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveTodo(@RequestBody todo: String) {
        todoService.saveTodo(todo)
    }
}

