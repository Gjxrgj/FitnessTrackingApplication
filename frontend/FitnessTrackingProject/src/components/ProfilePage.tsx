import React, {useEffect, useState} from "react";
import {LoginUserDto} from "./Types.tsx";
import {getLoggedUser} from "../service/UserService.tsx";
import {CardContent, TextField} from "@mui/material";

const ProfilePage: React.FC = () => {

    const [user, setUser] = useState<LoginUserDto>(null);

    const getUser = async () => {
       getLoggedUser().then(usr => setUser(usr))
    };


    useEffect(() => {
        getUser()
    }, []);

    if (user === null) {
        return <div></div>;
    }

    const card = (
        <React.Fragment>
            <CardContent
                style={{
                    backgroundColor: "#A7BEAE",
                    borderRadius: "5px"
                }}>
                <h2
                    style={{
                        marginBottom: "40px",
                        marginTop: "0px",
                        color: "white"
                    }}
                >PROFILE</h2>
                <TextField
                    id="outlined-read-only-input"
                    disabled={true}
                    label="USERNAME"
                    defaultValue={user?.username}
                    InputProps={{
                        readOnly: true,
                    }}
                    style={{
                        marginBottom: "10px"
                    }}
                />
                <TextField
                    id="outlined-read-only-input"
                    disabled={true}
                    label="AGE"
                    defaultValue={user?.age}
                    InputProps={{
                        readOnly: true,
                    }}
                    style={{
                        marginBottom: "10px"
                    }}
                />
                <TextField
                    id="outlined-read-only-input"
                    disabled={true}
                    label="HEIGHT"
                    defaultValue={user?.height}
                    InputProps={{
                        readOnly: true,
                    }}
                    style={{
                        marginBottom: "10px"
                    }}
                />
                <TextField
                    id="outlined-read-only-input"
                    disabled={true}
                    label="WEIGHT"
                    defaultValue={user?.weight}
                    InputProps={{
                        readOnly: true,
                    }}
                    style={{
                        marginBottom: "10px"
                    }}
                />
            </CardContent>
        </React.Fragment>
    );

    return (
        <div className="row">
            <div
                className="col"
                style={{
                    width: "300px",
                    paddingTop: "50px",
                    margin: "0 auto",
                     textAlign: "center",
                 }}
             >
                 {card}
             </div>
         </div>

     );
}
export default ProfilePage