import { Link } from '@tanstack/react-router'

const navigationItem = [
    {
        label: 'Посты',
        to: '/posts',
    },
    {
        label: 'Создать Пост',
        to: '/posts/create',
    },
]

const Navigation = () => {
    return (
        <nav>
            <ul>
                {navigationItem.map((item) => (
                    <Link to={item.to} key={item.label}>
                        <li>{item.label}</li>
                    </Link>
                ))}
            </ul>
        </nav>
    )
}

export default Navigation
