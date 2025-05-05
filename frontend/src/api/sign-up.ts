import { useMutation } from '@tanstack/react-query'
import axios from 'axios'
import { z } from 'zod'
import { signInSchema } from '../routes/sign-up.tsx'

export const useSignUpMutation = () => {
    return useMutation({
        mutationFn: async (data: z.infer<typeof signInSchema>) => {
            try {
                const {resData} = await axios.post('http://localhost:8080/api/auth/register', data);
                console.log(resData)
                return resData
            } catch (error) {
                console.log(error)
            }
        }
    })
}