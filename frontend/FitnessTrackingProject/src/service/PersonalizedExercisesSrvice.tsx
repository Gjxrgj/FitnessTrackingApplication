import {PersonalizedExerciseDto} from "../components/Types.tsx";
import client from "../axios/axios.ts";

export const getAllPersonalizedExercises = async (): Promise<Array<PersonalizedExerciseDto>>  => {
    return client.get('/fitness-tracking-app/api/personalizedexercises/listAll')
        .then(res => res.data)
}
export const getAllPersonalizedExerciseForWorkout = async (workoutId : number):Promise<Array<PersonalizedExerciseDto>> => {
    return client.get(`/fitness-tracking-app/api/personalizedexercises/${workoutId}`)
        .then(res => res.data)
}
export const removePersonalizedExercise = async (id : number):Promise<number> => {
    return client.get(`/fitness-tracking-app/api/personalizedexercises/remove/${id}`)
        .then(res => res.data)
}