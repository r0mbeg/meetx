import { Link } from '@tanstack/react-router'
import { Calendar, CirclePlus } from 'lucide-react'

const navigationItem = [
    {
        label: 'Мероприятия',
        to: '/events',
        icon: Calendar,
    },
    {
        label: 'Создать мероприятие',
        to: '/events/create',
        icon: CirclePlus,
    },
]

const Navigation = () => {
    return (
        <nav className='space-y-6'>
            {navigationItem.map((item) => (
                <Link to={item.to} key={item.label} className='flex items-center space-x-2 py-2 px-3 bg-stone-600 rounded-md'>
                    <span>{item.label}</span>
                    {item.icon && <item.icon size={20} />}
                </Link>
            ))}
        </nav>
    )
}

export default Navigation
