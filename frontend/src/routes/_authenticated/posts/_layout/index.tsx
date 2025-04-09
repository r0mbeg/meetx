import { createFileRoute, Link } from '@tanstack/react-router'
import { useQuery } from '@tanstack/react-query'

export const Route = createFileRoute('/_authenticated/posts/_layout/')({
    component: RouteComponent,
})

const getPosts = async () => {
    const data = await fetch('https://jsonplaceholder.typicode.com/posts')
    return data.json()
}

function RouteComponent() {
    const { data, error, status } = useQuery({
        queryKey: ['posts'],
        queryFn: getPosts,
    })

    return (
        <div className="p-3 w-full bg-grey-600">
            <div>Список постов!</div>
            {status === 'pending' ? (
                'Загрузка...'
            ) : status === 'error' ? (
                <span>Ошибка: {error.message}</span>
            ) : data.length > 0 ? (
                data.slice(0, 10).map((post: { id: string }) => (
                    <Link
                        key={post.id}
                        to="/posts/$postid"
                        params={{ postid: post.id }}
                    >
                        Пост {post.id}
                    </Link>
                ))
            ) : (
                <span>Список пуст</span>
            )}
        </div>
    )
}
