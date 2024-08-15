import client from "../axios/axios.ts";

export const getDay = async () => {

    return client.get("/fitness-tracking-app/api/days/getDay")
        .then(res => res.data)

}

export const removeWorkoutFromDay = async (did: number, wid: number) => {
    return client.delete("/fitness-tracking-app/api/days/removeWorkout", {params: {did, wid}})
    .then(res => res.data)
}
export const removeMealFromDay = async (did: number, mid: number) => {
    return client.delete("/fitness-tracking-app/api/days/removeMeal", {params: {did, mid}})
    .then(res => res.data)
}