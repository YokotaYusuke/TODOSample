import {useEffect, useState} from 'react'
import TodoRepository, {DefaultTodoRepository} from '../repository/TodoRepository.ts'

type Props = {
    repository?: TodoRepository
}

export default function App({repository = new DefaultTodoRepository()}: Props) {
    const [draftText, setDraftText] = useState<string>('')
    const [todoList, setTodoList] = useState<string[]>([])
    const [user, setUser] = useState<string | undefined>(undefined)

    useEffect(() => {
        if (user === undefined) {
            fetch('/auth/user')
                .then(res => {
                    if (!res.ok && user !== null) return
                    return res.json()
                })
                .then(user => setUser(user.username))
        }

        repository.getTodos().then(todos => {
                if (!Array.isArray(todos)) return
                setTodoList(todos)
            }
        ).catch(error => console.log('error:::', error))
    }, [])

    function socialSignIn(registration: string) {
        window.location.href = `http://localhost:8080/oauth2/authorization/${registration}`
    }

    return (
        <>
            <div>TODO</div>
            <div>ログイン中のユーザー: {user}</div>
            <button onClick={() => socialSignIn('google')}>Googleでサインイン</button>
            <button onClick={() => socialSignIn('line')}>LINEでサインイン</button>
            <input type="text" role="text_input"
                   value={draftText}
                   onChange={(event) => (setDraftText(event.target.value))}
            />
            <button role="save_button" onClick={() => {
                repository?.saveTodo(draftText)
                setTodoList([...todoList, draftText])
                setDraftText('')
            }}>
                save
            </button>
            <div role="todoArea">
                {todoList.map(todo =>
                    (<div key={window.crypto.randomUUID()}>
                        {todo}
                    </div>)
                )}
            </div>

            <form action="http://localhost:8080/logout" method="post">
                <button type="submit">ログアウト</button>
            </form>
        </>
    )
}