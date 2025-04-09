import { createFileRoute, Link } from '@tanstack/react-router'

export const Route = createFileRoute('/')({
    component: Index,
})

function Index() {
    return (
        <>
            <div className="h-full w-full flex justify-center items-center space-x-2">
                <Link to="/sign-in">
                    <button className="bg-blue-500 rounded-lg p-3 border-2 border-transparent text-black hover:opacity-85 transition duration-200">
                        Войти
                    </button>
                </Link>
                <Link to="/sign-up">
                    <button className="bg-white rounded-lg p-3 border-2 border-blue-500 text-black hover:bg-blue-500 transition duration-200">
                        Зарегистрироваться
                    </button>
                </Link>
            </div>
        </>
    )
}
