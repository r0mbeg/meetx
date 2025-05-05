import Navigation from './navigation.tsx'
import { Link } from '@tanstack/react-router'
import { LogOut } from 'lucide-react'

const Sidebar = () => {
    return (
        <div className="min-w-[262px] h-full flex flex-col justify-center items-center p-3">
            <Navigation />
            <Link to='/sign-in' className='flex items-center space-x-2 py-2 px-3 bg-stone-600 rounded-md mt-20'>
                <span>Выйти</span> <LogOut size={20}/>
            </Link>
        </div>
    )
}

export default Sidebar
