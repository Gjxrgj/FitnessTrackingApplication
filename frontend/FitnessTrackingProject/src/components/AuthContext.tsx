import React from "react";
import axios, {AxiosError} from "axios";
import {useLocation, useNavigate} from "react-router-dom";
import {LoginDto, LoginUserDto} from "./Types.tsx";
import {AUTHORIZATION_KEY, getAuthToken, removeAuthToken, setAuthToken} from "../service/LoginServices.tsx";
import client from "../axios/axios.ts";
import {getUserData, loginUser, logOutUser} from "../service/UserService.tsx";

type LoginFunction = (login: LoginDto) => void;

interface State {
    user?: Partial<LoginUserDto>,
    login: LoginFunction,
    logout: () => void,
    isAuthenticated: boolean,
}

/*type Props = IntrinsicAttributes & ProviderProps<State>*/


const AuthContext = React.createContext<State>({
    login: () => undefined,
    logout: () => undefined,
    isAuthenticated: false
});

export const AuthProvider = ({children} : any) => {
    const authToken = React.useMemo(() => getAuthToken(), []);
    const navigate = useNavigate();
    const location = useLocation();
    const cancelTokenSource = React.useMemo(axios.CancelToken.source, []);
    const publicRoutes = ["/login", "/register"];

    const [user, setUser] = React.useState<Partial<LoginUserDto> | undefined>();
    const [isAuthenticated, setIsAuthenticated] = React.useState<boolean>(!!authToken);

    React.useEffect(() => {
        const pathname = location.pathname;
        if (authToken === null || authToken === undefined) {
            if (pathname !== "" && !publicRoutes.includes(location.pathname)) {
                navigate("/login");
            }
            return;
        } else if (pathname === "/") {
            navigate("/home")
        }
        getUserData(cancelTokenSource)
            .then((data: Partial<LoginUserDto>) => {
                setUser(data);
            })
            .catch(() => {
                removeAuthToken();
                const pathname = location.pathname;
                if (pathname !== "" && pathname !== "/login") {
                    navigate("/login");
                }
            });
    }, []);

    const login: LoginFunction = React.useCallback((login: LoginDto) => {
        loginUser(login, cancelTokenSource)
            .then((response: any) => {
                const token = response.headers[AUTHORIZATION_KEY];
                setAuthToken(token);

                getUserData(cancelTokenSource).then((data : LoginUserDto) => {
                    setUser(data);
                        setIsAuthenticated(true);
                        navigate("/home")
                });
            });
    }, [cancelTokenSource, navigate]);

    const sessionExpiredHandler = (error: AxiosError) => {
        const { response } = error;

        if (response && response.status === 401) {
            removeAuthToken();
            navigate("/login");
        }

        return Promise.reject(error);
    };

    client.interceptors.response.use(response => response, sessionExpiredHandler);

    const logout = React.useCallback(() => {
        logOutUser(cancelTokenSource)
            .then(() => {
                removeAuthToken();
                setIsAuthenticated(false);
                navigate("/login");
            });
    }, [cancelTokenSource, setIsAuthenticated]);

    React.useEffect(() => {
        if (!isAuthenticated && !publicRoutes.includes(location.pathname)) {
            navigate("/login");
        }
    }, [isAuthenticated, navigate]);

    const state = React.useMemo(() => {
        return {
            user,
            setUser,
            logout,
            login,
            isAuthenticated,
            setIsAuthenticated
        };
    }, [
        user, setUser,
        logout, login,
        isAuthenticated, setIsAuthenticated
    ]);

    return (
        <AuthContext.Provider value={state}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuthContext = () => React.useContext(AuthContext);