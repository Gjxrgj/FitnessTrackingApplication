import {IngredientDto, PersonalizedIngredientDto} from "../components/Types.tsx";
import client from "../axios/axios.ts";

export const getAllIngredients = async (): Promise<Array<IngredientDto>> => {
    return client.get("/fitness-tracking-app/api/ingredients/listAll")
        .then(res => res.data)
}
export const getAllIngredientsBySearch = async (searchCriteria : string): Promise<Array<IngredientDto>> => {
    return client.get("/fitness-tracking-app/api/ingredients/search", {params: {searchCriteria}})
        .then(res => res.data)
}
export const removeIngredient = async (id: number): Promise<number> => {
    return client.get(`/fitness-tracking-app/api/ingredients/remove/${id}`)
        .then(res => res.data.iid)
}
export const editIngredient = async (ingredient: IngredientDto): Promise<IngredientDto> => {
    return client.put(`/fitness-tracking-app/api/ingredients/edit`, ingredient)
        .then(res => res.data.iid)
}


export const getIngredientsByMealId = async (mealId : number): Promise<Array<PersonalizedIngredientDto>> => {
    return client.get(`/fitness-tracking-app/api/personalizedIngredients/${mealId}`)
        .then(res => res.data)
}
export const getAllPersonalizedIngredients = async (): Promise<Array<PersonalizedIngredientDto>> => {
    return client.get(`/fitness-tracking-app/api/personalizedIngredients/all`)
        .then(res => res.data)
}
export const addIngredient = async (ingredient : IngredientDto): Promise<PersonalizedIngredientDto> => {
    return client.post(`/fitness-tracking-app/api/ingredients/add`, ingredient)
        .then(res => res.data)
}

export const removePersonalizedIngredient = async (id: number): Promise<number> => {
    return client.delete(`/fitness-tracking-app/api/personalizedIngredients/remove/${id}`)
        .then(res => res.data)
}