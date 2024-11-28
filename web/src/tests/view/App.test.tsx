import {describe, expect, test} from "vitest";
import {render, screen, within} from "@testing-library/react";
import App from "../../view/App.tsx";
import userEvent from "@testing-library/user-event";
import {DummyTodoRepository, SpyTodoRepository, StubTodoRepository} from "../repository/TodoRepositoryDoubles.ts";
import TodoRepository from "../../repository/TodoRepository.ts";
import {act} from "react";

describe('App', () => {
    test('TODOというタイトルがある', async () => {
        await renderApp()


        expect(screen.getByText('TODO')).toBeInTheDocument()
    })

    test('テキストを入力できるインプットがある', async () => {
        await renderApp()


        const textInput = screen.getByRole('text_input') as HTMLElement
        expect(textInput).toBeInTheDocument()
    })

    test('テキストを保存できるボタンがある', async () => {
        await renderApp()


        const saveButton = screen.getByRole('save_button') as HTMLElement
        expect(saveButton).toBeInTheDocument()
    })

    test('保存ボタンを押した時にインプットに入力したテキストが表示される', async () => {
        await renderApp()


        await inputTodoAndSaveTodo('hello, world!')


        const todoArea = screen.getByRole('todoArea')
        expect(within(todoArea).getByText('hello, world!')).toBeInTheDocument()
    })

    test('初期化時は、todoAreaの中に何も表示されない', async () => {
        await renderApp()


        expect(screen.getByRole('todoArea').textContent).toBe('')
        expect(screen.getByRole('todoArea').children.length).toBe(0)
    })

    test('複数のTODOを保存できる', async () => {
        await renderApp()


        await inputTodoAndSaveTodo('hello, world!')
        await inputTodoAndSaveTodo('todotodotodo')


        expect(within(screen.getByRole('todoArea')).getByText('hello, world!')).toBeInTheDocument()
        expect(within(screen.getByRole('todoArea')).getByText('todotodotodo')).toBeInTheDocument()
    })

    test('初期化時に、TodoRepositoryのgetTodosを呼ぶ', () => {
        const spyTodoRepository = new SpyTodoRepository()
        render(<App repository={spyTodoRepository}/>)


        expect(spyTodoRepository.getTodos_wasCalled).toBeTruthy()
    })

    test('初期化時に、getTodosの返り値のTODOを表示する', async () => {
        const stubTodoRepository = new StubTodoRepository()
        stubTodoRepository.getTodos_returnValue = Promise.resolve([
            '歯を磨く',
            '薬を飲む'
        ])


        await renderApp(stubTodoRepository)


        const todoArea = screen.getByRole('todoArea')
        // screen.debug()
        expect(todoArea.children.length).toEqual(2)
        expect(within(todoArea).getByText('歯を磨く')).toBeInTheDocument()
        expect(within(todoArea).getByText('薬を飲む')).toBeInTheDocument()
    })

    test('saveボタンを押した時、TodoRepositoryのsaveTodoに正しい引数を渡す', async () => {
        const spyTodoRepository = new SpyTodoRepository()
        await renderApp(spyTodoRepository)


        await inputTodoAndSaveTodo('hello, world!')


        expect(spyTodoRepository.saveTodo_argument_todo).toEqual('hello, world!')
    })

    async function renderApp(repository: TodoRepository = new DummyTodoRepository()) {
        await act(async () => {
            render(<App repository={repository}/>)
        })
    }

    async function inputTodoAndSaveTodo(todo: string) {
        const textInput = screen.getByRole('text_input') as HTMLElement
        await userEvent.type(textInput, todo)
        const saveButton = screen.getByRole('save_button') as HTMLElement
        await userEvent.click(saveButton)
    }
})