import { createFileRoute } from '@tanstack/react-router'

export const Route = createFileRoute('/_authenticated/posts/_layout/create')({
    component: RouteComponent,
})

function RouteComponent() {
    return <div>Hello "/posts/create"!</div>
}
