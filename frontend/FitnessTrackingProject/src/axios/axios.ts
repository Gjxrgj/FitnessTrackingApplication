import axios, {InternalAxiosRequestConfig} from "frontend/FitnessTrackingProject/src/axios/axios";
import {getAuthToken} from "../service/LoginServices.tsx";

const client = axios.create({
    headers:{
        'Access-Control-Allow-Origin': '*'
    }
});

client.interceptors.request.use((request: InternalAxiosRequestConfig) => {
    request.headers.set("Authorization", getAuthToken());

    return request;
});

export default client;
