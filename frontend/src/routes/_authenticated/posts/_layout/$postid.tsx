import { createFileRoute } from '@tanstack/react-router'

export const Route = createFileRoute('/_authenticated/posts/_layout/$postid')({
    component: RouteComponent,
})

function RouteComponent() {
    const { postid } = Route.useParams()

    return <p>Пост {postid}</p>
}
