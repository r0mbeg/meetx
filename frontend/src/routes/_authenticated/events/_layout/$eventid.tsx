import { createFileRoute } from '@tanstack/react-router'

export const Route = createFileRoute('/_authenticated/events/_layout/$eventid')({
    component: RouteComponent,
})

function RouteComponent() {
    const { eventid } = Route.useParams()

    return <p>Пост {eventid}</p>
}
