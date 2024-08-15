import client from "../axios/axios.ts";
import {WorkoutDto} from "../components/Types.tsx";

export const getAllWorkouts = async ():  Promise<Array<WorkoutDto>> => {
    return client.get("/fitness-tracking-app/api/workouts/listAll")
        .then(res => res.data)
}
export const getAllWorkoutsBySearch = async (searchCriteria: string):  Promise<Array<WorkoutDto>> => {
    return client.get("/fitness-tracking-app/api/workouts/search", {params: {searchCriteria}})
        .then(res => res.data)
}
export const removeWorkout = async (id : number ):Promise<number> => {
    return client.delete(`/fitness-tracking-app/api/workouts/remove/${id}`, )
        .then(res => res.data)
}

export const editWorkout = async (workoutDto : WorkoutDto): Promise<WorkoutDto> => {
    return client.put("/fitness-tracking-app/api/workouts/edit", workoutDto)
        .then(res => res.data)
}
export const addWorkout = async (workout : WorkoutDto): Promise<WorkoutDto> => {
    return client.post("/fitness-tracking-app/api/workouts/add", workout)
        .then(res => res.data)
}

export const addWorkoutToDay = async (workoutDto : WorkoutDto): Promise<WorkoutDto> => {
    return client.post("/fitness-tracking-app/api/workouts/addWorkoutToDay", workoutDto)
        .then(res => res.data)
}


