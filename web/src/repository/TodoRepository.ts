import Http, {NetWorkHttp} from "../http/NetWorkHttp.ts";

export default interface TodoRepository {
    getTodos(): Promise<string[]>
    saveTodo(todo: string): void
}

export class DefaultTodoRepository implements TodoRepository {
    http: Http

    constructor(http: Http = new NetWorkHttp()) {
        this.http = http
    }

    getTodos(): Promise<string[]> {
        return this.http.get('/api/todos')
    }

    saveTodo(todo: string): void {
        this.http.post('/api/todos', todo)
    }

}