import { createFileRoute, useNavigate } from '@tanstack/react-router'

export const Route = createFileRoute('/sign-in')({
    component: RouteComponent,
})

function RouteComponent() {
    const navigate = useNavigate()

    const signIn = () => {
        navigate({ to: '/posts' })
    }

    return (
        <div className="flex flex-col justify-center items-center h-full">
            <h1 className="mb-4 text-8xl">meetX</h1>
            <form className="flex flex-col space-y-3" action={signIn}>
                <input
                    type="text"
                    className="bg-white rounded-lg p-2 border-2 border-blue-500 text-black"
                    placeholder="Эл. почта"
                />
                <input
                    type="text"
                    className="bg-white rounded-lg p-2 border-2 border-blue-500 text-black"
                    placeholder="Пароль"
                />
                <button className="bg-blue-500 rounded-lg p-3 border-2 border-transparent text-black hover:opacity-85 transition duration-200">
                    Войти
                </button>
            </form>
        </div>
    )
}
