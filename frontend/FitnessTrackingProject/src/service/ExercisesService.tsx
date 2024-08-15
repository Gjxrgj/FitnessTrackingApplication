import {ExerciseDto} from "../components/Types.tsx";
import client from "../axios/axios.ts";

export const getAllExercises = async (): Promise<ExerciseDto[]>  => {
    return client.get('/fitness-tracking-app/api/exercises/listAll')
        .then(res => res.data)
}
export const getAllExercisesBySearch = async (searchCriteria: string): Promise<Array<ExerciseDto>>  => {
    return client.get('/fitness-tracking-app/api/exercises/search', {params: {searchCriteria}})
        .then(res => res.data)
}
export const addExercise = async (exercise : ExerciseDto) : Promise<ExerciseDto> => {
    return client.post('/fitness-tracking-app/api/exercises/add', exercise)
        .then(res => res.data)
}
export const editExericse = async (exercise : ExerciseDto) : Promise<ExerciseDto> => {
    return client.post('/fitness-tracking-app/api/exercises/edit', exercise)
        .then(res => res.data)
}
export const removeExercise = async (id : number) : Promise<number> => {
    return client.post(`/fitness-tracking-app/api/exercises/remove/${id}`)
        .then(res => res.data)
}



