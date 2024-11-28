package com.example.server

import com.example.server.todo.TodoController
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
class TodoControllerTest {
    lateinit var todoController: TodoController

    @Test
    fun TodoServiceのgetTodosを正しく呼ぶ() {
        val spyTodoService = SpyTodoService()
        todoController = TodoController(spyTodoService)
        val result = MockMvcBuilders
            .standaloneSetup(todoController)
            .build()
            .perform(get("/api/todos"))


        result.andExpect(status().isOk)
        assertTrue(spyTodoService.getTodos_wasCalled)
    }

    @Test
    fun TodoServiceのgetTodosの戻り値を返す() {
        val stubTodoService = StubTodoService()
        stubTodoService.getTodos_returnValue = listOf(
            "筋トレ",
            "ランニング",
        )

        todoController = TodoController(stubTodoService)
        val result = MockMvcBuilders
            .standaloneSetup(todoController)
            .build()
            .perform(get("/api/todos"))

        result.andExpect(status().isOk)
            .andExpect(jsonPath("$[0]", equalTo("筋トレ")))
            .andExpect(jsonPath("$[1]", equalTo("ランニング")))
    }

    @Test
    fun ToDoServiceのsaveTodoを正しく呼ぶ(){
        val spyTodoService = SpyTodoService()
        val todoController = TodoController(spyTodoService)
        val result = MockMvcBuilders
            .standaloneSetup(todoController)
            .build()
            .perform(post("/api/todos")
                .content("Flutter study")
                .contentType(MediaType.TEXT_PLAIN))

        result.andExpect(status().isCreated)
        assertEquals("Flutter study", spyTodoService.saveTodo_argument_todo)
    }
}