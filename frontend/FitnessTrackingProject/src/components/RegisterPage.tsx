import React, {useState} from "react";
import {RegisterDto} from "./Types.tsx";
import {Box, TextField} from "@mui/material";
import logo from "../images/logo.png";
import {register} from "../service/UserService.tsx";
import {useNavigate} from "react-router-dom";

const RegisterPage: React.FC = () => {
    const [formData, setFormData] = useState<RegisterDto>({
        username: '',
        password: '',
        age: 0,
        weight: 0,
        height: 0
    });
    const navigate = useNavigate();

    const handleFormSubmit = (e: any) => {
        e.preventDefault();
        register(formData).then(() => navigate("/login"));
    };
    const handleInputChange = (e: any) => {
        const {name, value} = e.target;
        setFormData({...formData, [name]: value})
    }
    return (
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
                    backgroundColor: '#A7BEAE',
                    paddingBottom:"60px"
                }}
                sx={{
                    width: 400,
                    height: 600,
                    borderRadius: 4,
                }}
            >
                <img src={logo} alt=""
                     style={{
                         height: "140px",
                         width: "400px",
                         marginBottom: "70px",
                         marginTop: "70px"
                     }}/>

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

                    <TextField
                        required
                        id="filled-required"
                        label="Age"
                        defaultValue="Age"
                        name="age"
                        value={formData.age}
                        onChange={handleInputChange}
                        variant="filled"
                    />
                    <TextField
                        required
                        id="filled-required"
                        label="Height"
                        defaultValue="Height"
                        name="height"
                        value={formData.height}
                        onChange={handleInputChange}
                        variant="filled"
                    />
                    <TextField
                        required
                        id="filled-required"
                        label="Weight"
                        defaultValue="Weight"
                        name="weight"
                        value={formData.weight}
                        onChange={handleInputChange}
                        variant="filled"
                    />
                    <br/>
                    <button style={{
                        display: "block",
                        margin: "auto",
                        width: "200px",
                        marginTop: "40px",
                        backgroundColor: '#7695A8',
                        color: "white"
                    }} type={"submit"}>Register
                    </button>
                </form>
                <div
                    style={{marginTop: "10px"}}>
                    <i>Already a member? <a onClick={() => navigate('/login')}>Log in now.</a></i>
                </div>
            </Box>
        </div>
    );
}
export default RegisterPage