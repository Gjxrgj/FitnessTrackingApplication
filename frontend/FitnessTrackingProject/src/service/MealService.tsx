import client from "../axios/axios.ts";
import {MealDto} from "../components/Types.tsx";

export const getMeals = async (): Promise<Array<MealDto>> => {
    return client.get("/fitness-tracking-app/api/meals/listAll")
        .then(res => res.data)
}
export const addMeal = async(meal: MealDto)=>{
    return client.post('/fitness-tracking-app/api/meals/add', meal)
        .then(res => res.data)
}
export const getMealsByCriteria = async (searchCriteria: string): Promise<Array<MealDto>> => {
    return client.get(`/fitness-tracking-app/api/meals/search`, {params: {searchCriteria}})
        .then(res => res.data)
}

export const editMeal = async (meal: MealDto) => {
    return client.put('/fitness-tracking-app/api/meals/edit', meal)
        .then(res => res.data)
}

export const removeMeal = async(id: number): Promise<number> => {
    return client.get(`/fitness-tracking-app/api/meals/remove/${id}`)
        .then(res => res.data.mid)
}

export const addMealToDay = async (mealDto :MealDto): Promise<MealDto> => {
    return client.post('/fitness-tracking-app/api/meals/addMealToDay', mealDto)
        .then(res => res.data)
}