import { createFileRoute, Link } from '@tanstack/react-router'
import { useForm } from 'react-hook-form'
import { z } from 'zod'
import { useSignUpMutation } from '../api/sign-up.ts'

export const Route = createFileRoute('/sign-up')({
    component: RouteComponent,
})

export const signInSchema = z.object({
    name: z.string()
        .min(1, { message: "Поле не должно быть пустым" }) // Проверка на пустую строку
        .refine((value) => {
            const parts = value.trim().split(' ');
            return parts.length >= 2; // Проверка на наличие хотя бы двух частей
        }, { message: "Введите фамилию и имя" })
        .refine((value) => {
            const parts = value.trim().split(' ');
            return parts.every(part => /^[A-Za-zА-Яа-яЁё]+$/.test(part)); // Проверка на буквы
        }, { message: "Фамилия и имя должны содержать только буквы" }),
    username: z.string().min(4).max(34),
    email: z.string()
        .email({ message: "Введите корректный адрес электронной почты" }),
    password: z.string().min(4),
});



function RouteComponent() {
    const { mutate, isPending } = useSignUpMutation();
    const { register, handleSubmit } = useForm({
        defaultValues: {
            name: '',
            username: '',
            email: '',
            password: '',
        }
    })

    const signUp = async (data: z.infer<typeof signInSchema>) => {
        console.log(data)
        mutate(data, {
            onSuccess: () => {
                console.log()
            }
        })
    }

    return (
        <div className="flex flex-col justify-center items-center h-full">
            <h1 className="mb-4 text-8xl select-none">meetX</h1>
            <form className="flex flex-col space-y-3" onSubmit={handleSubmit(signUp)}>
                <input
                    type="text"
                    className="bg-white rounded-lg p-2 border-2 border-blue-500 text-black"
                    placeholder="Фамилия и Имя"
                    {...register('name')}
                />
                <input
                    type="text"
                    className="bg-white rounded-lg p-2 border-2 border-blue-500 text-black"
                    placeholder="Имя пользователя"
                    {...register('username')}
                />
                <input
                    type="text"
                    className="bg-white rounded-lg p-2 border-2 border-blue-500 text-black"
                    placeholder="Эл. почта"
                    {...register('email')}
                />
                <input
                    type="text"
                    className="bg-white rounded-lg p-2 border-2 border-blue-500 text-black"
                    placeholder="Пароль"
                    {...register('password')}
                />
                <button
                    className="bg-blue-500 rounded-lg p-3 border-2 border-transparent hover:opacity-85 transition duration-200"
                    disabled={isPending}
                >
                    Зарегистрироваться
                </button>
                <Link to="/sign-in" className="text-center hover:opacity-85 transition duration-200">
                    Уже есть аккаунт?
                </Link>
            </form>
        </div>
    )
}
