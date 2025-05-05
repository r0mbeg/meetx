import { createFileRoute, Outlet } from '@tanstack/react-router'
import Sidebar from '../../../components/sidebar.tsx'

export const Route = createFileRoute('/_authenticated/events/_layout')({
    component: RouteComponent,
})

function RouteComponent() {
    return (
        <div className="flex h-full">
            <Sidebar />
            <Outlet />
        </div>
    )
}
