import React, {useState} from "react";
import {useAuthContext} from "./AuthContext.tsx";
import {LoginDto} from "./Types.tsx";
import {Box, TextField} from "@mui/material";
import logo from "../images/logo.png";
import {useNavigate} from "react-router-dom";

const Login = () => {
    const { login } = useAuthContext();
    const [formData, setFormData] = useState<LoginDto>({
        username: '',
        password: '',
    });
    const navigate = useNavigate()
    const handleFormSubmit = (e : any) => {
        e.preventDefault();
        login(formData);
    };
    const handleInputChange = (e : any) =>{
        const {name, value} = e.target;
        setFormData({...formData, [name]: value})
    }
    return(
        <div
            style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '100vh', // Set the height to 100% of the viewport height
            }}
        >
            <Box
                style={{
                    display: 'flex',
                    flexDirection: 'column',
                    justifyContent: 'center',
                    alignItems: 'center',
                    margin: 'auto',
                    backgroundColor: '#A7BEAE'
                }}
                sx={{
                    width: 400,
                    height: 500,
                    borderRadius: 4,
                }}
            >
                <img src={logo} alt="" style={{height: "140px", width: "400px", marginBottom:"70px"}}/>

                <form onSubmit={handleFormSubmit} style={{width: '50%'}}>

                    <TextField
                        required
                        id="filled-required"
                        label="Username"
                        defaultValue="Username"
                        name="username"
                        value={formData.username}
                        onChange={handleInputChange}
                        variant="filled"
                    />
                    <TextField
                        required
                        id="filled-password-input"
                        label="Password"
                        type="password"
                        name="password"
                        value={formData.password}
                        onChange={handleInputChange}
                        autoComplete="current-password"
                        variant="filled"
                    />
                    <br/>
                    <button style={{display: "block",
                        margin: "auto",
                        width: "200px",
                        marginTop: "40px",
                        backgroundColor: '#7695A8',
                        borderRadius:"5px",
                        color:"white"}} type="submit">Login</button>
                </form>
                <div
                style={{marginTop:"10px"}}>
                    <i>Not a member yet? <a  onClick={() => navigate('/register')}>Register now.</a></i>
                </div>
            </Box>
        </div>
    );
}
export default Login