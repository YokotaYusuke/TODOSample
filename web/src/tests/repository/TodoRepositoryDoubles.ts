import TodoRepository from "../../repository/TodoRepository.ts";

export class DummyTodoRepository implements TodoRepository {
    getTodos(): Promise<string[]> {
        return Promise.resolve([]);
    }

    saveTodo(todo: string): void {
    }

}

export class SpyTodoRepository implements TodoRepository {
    getTodos_wasCalled: boolean = false
    getTodos(): Promise<string[]> {
        this.getTodos_wasCalled = true
        return Promise.resolve([]);
    }

    saveTodo_argument_todo?: string = undefined
    saveTodo(todo: string): void {
        this.saveTodo_argument_todo = todo
    }

}

export class StubTodoRepository implements TodoRepository {
    getTodos_returnValue: Promise<string[]> = Promise.resolve([])
    getTodos(): Promise<string[]> {
        return this.getTodos_returnValue
    }

    saveTodo(todo: string): void {
    }

}