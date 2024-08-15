import client from "../axios/axios";
import {LoginDto, LoginUserDto, RegisterDto} from "../components/Types.tsx";
import {CancelTokenSource} from "axios";

const baseUrl = "/fitness-tracking-app";

export const getUserData = (source: CancelTokenSource): Promise<LoginUserDto> => {
    return client
        .get(`${baseUrl}/get-logged-user`, {
            cancelToken: source.token
        })
        .then(response => response.data);
};
export const getLoggedUser = async (): Promise<LoginUserDto> => {
    return client
        .get(`${baseUrl}/get-logged-user`)
        .then(res => res.data);
};
export const loginUser = (login: LoginDto, source: CancelTokenSource) => {
    return client.post(`${baseUrl}/login`, login, {
        cancelToken: source.token,
    });
};

export const logOutUser = (source: CancelTokenSource) => {
    return client.post(`${baseUrl}/logout`, null, { cancelToken: source.token });
};

export const register = async (registerDto: RegisterDto): Promise<LoginUserDto> => {
    return client.post(`${baseUrl}/register`, registerDto)
        .then(res => res.data)

}