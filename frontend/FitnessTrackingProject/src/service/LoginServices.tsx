const TOKEN_KEY = "token";
export const AUTHORIZATION_KEY = "authorization";

export const setAuthToken = (token: string) => {
    localStorage.setItem(TOKEN_KEY, token);
};

export const getAuthToken = () => {
    return localStorage.getItem(TOKEN_KEY);
};

export const removeAuthToken = () => {
    return localStorage.removeItem(TOKEN_KEY);
};