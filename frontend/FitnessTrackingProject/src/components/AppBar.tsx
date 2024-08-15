import React from 'react';
import {useNavigate} from "react-router-dom";
import {useAuthContext} from "./AuthContext.tsx";
import styled from "styled-components";
import Home from "./Home.tsx";
import logo from "../images/logo2.png";

const StyledButton = styled.button`
    margin-inline: 20px;
    background: #A7BEAE;
    color: #ffffff;
    font-size: 18px;
    border: none; /* Remove the default button border */
    cursor: pointer;

    &:focus {
        outline: none; /* Remove the focus outline */
    }

    &:hover {
        /* Add hover styles if needed */
    }
`;
const AppBar = () => {
    const navigate = useNavigate();
    const {logout} = useAuthContext()

    const handleNavigation = (path:any) => {
        navigate(path);
    };

    return (
        <div className="custom-font">
        <div className="app-bar" style={{justifyContent: "space-between", display:"flex",  background: '#A7BEAE', color: '#A7BEAE', padding: '8px 16px', border: 'none', cursor: 'pointer' }}>
            <div className="left-buttons" style={{ background: '#A7BEAE', color: '#fff', padding: '8px 16px', border: 'none', cursor: 'pointer' }}>
                <StyledButton style={{padding:"0px"}} onClick={() => handleNavigation('/home')}><img src={logo} alt="" style={{
                    height: "40px",
                    width: "200px",
                    padding:"0px",
                    margin:"0px"
                }}/></StyledButton>
            </div>
            <div className="right-buttons"
                 style={{background: '#A7BEAE', color: '#fff', padding: '8px 16px', border: 'none', cursor: 'pointer' }}>
                <StyledButton onClick={() => handleNavigation('/ingredients')} >INGREDIENTS</StyledButton>
                <StyledButton onClick={() => handleNavigation('/meals')}>MEALS</StyledButton>
                <StyledButton onClick={() => handleNavigation('/exercises')}>EXERCISES</StyledButton>
                <StyledButton onClick={() => handleNavigation('/workouts')}>WORKOUTS</StyledButton>
                <StyledButton onClick={() => handleNavigation('/profile')}>PROFILE</StyledButton>
                <StyledButton onClick={ logout }>LOGOUT</StyledButton>
            </div>
        </div>
           <div style={{marginInline: "50px"}}>
               <Home/>
           </div>
        </div>
    );
};

export default AppBar;
