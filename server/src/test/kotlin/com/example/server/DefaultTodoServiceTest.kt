package com.example.server

import com.example.server.todo.DefaultTodoService
import com.example.server.todo.TodoRecord
import com.example.server.todo.TodoRepository
import com.example.server.todo.TodoService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.Test

@DataJpaTest
class DefaultTodoServiceTest {
    @Autowired
    lateinit var todoRepository: TodoRepository

    lateinit var todoService: TodoService

    @BeforeEach
    fun setup() {
        todoService = DefaultTodoService(todoRepository)
    }

    @Test
    fun TodoServiceのgetTodosを呼んだ時にTodoの配列を返す() {
        todoRepository.saveAll(
            listOf(
                TodoRecord(todo = "ffff"),
                TodoRecord(todo = "遅くまで起きる"),
            )
        )


        val todos = todoService.getTodos()


        assertEquals(2, todos.size)
        assertEquals("ffff", todos.first())
        assertEquals("遅くまで起きる", todos.last())
    }

    @Test
    fun TodoServiceのsaveTodoを呼んだ時にTodoを保存する() {
        todoRepository.deleteAll()


        todoService.saveTodo("筋トレ")


        val todos = todoRepository.findAll()
        assertEquals(1, todos.size)
        assertEquals("筋トレ", todos.first().todo)
    }
}