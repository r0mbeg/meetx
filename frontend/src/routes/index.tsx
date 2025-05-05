import { createFileRoute, Link } from '@tanstack/react-router'

export const Route = createFileRoute('/')({
    component: Index,
})

function Index() {
    return (
        <>
            <div className="h-full w-full flex flex-col justify-center items-center space-x-2">
                <h1 className="mb-4 text-8xl">meetX</h1>
                <p className="max-w-md text-center mb-4">
                    Удобное приложение для организации командных мероприятий
                    и распределения расходов
                </p>
                <Link to="/sign-in">
                    <button className="bg-white rounded-lg p-3 border-2 border-transparent text-black hover:bg-stone-600 hover:text-white transition duration-200">
                        Начать организацию
                    </button>
                </Link>
            </div>
        </>
    )
}
