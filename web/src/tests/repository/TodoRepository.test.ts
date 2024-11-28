import {describe, expect} from "vitest";
import {DefaultTodoRepository} from "../../repository/TodoRepository.ts";
import {SpyHttp, StubHttp} from "../http/HttpDobules.ts";

describe('TodoRepository', () => {
    describe('getTodos', () => {
        test('httpのgetに正しい引数を渡している', () => {
            const spyHttp = new SpyHttp()
            const todoRepository = new DefaultTodoRepository(spyHttp)


            todoRepository.getTodos()


            expect(spyHttp.get_argument_url).toEqual('/api/todos')
        })

        test('httpのgetの戻り値をキャストして返す', async () => {
            const stubHttp = new StubHttp()
            stubHttp.get_returnValue = Promise.resolve(['todo1', 'todo2'])
            const todoRepository = new DefaultTodoRepository(stubHttp)


            const todos = await todoRepository.getTodos()


            expect(todos).toEqual(['todo1', 'todo2'])
        })
    })

    describe('saveTodo', () => {
        test('httpのpostに正しい引数を渡す', () => {
            const spyHttp = new SpyHttp()
            const todoRepository = new DefaultTodoRepository(spyHttp)


            todoRepository.saveTodo('Motta!')


            expect(spyHttp.post_argument_url).toEqual('/api/todos')
            expect(spyHttp.post_argument_body).toEqual('Motta!')
        })
    })

})