import React, {useEffect, useState} from "react";
import {ExerciseDto} from "./Types.tsx";
import {addExercise, editExericse, getAllExercisesBySearch, removeExercise} from "../service/ExercisesService.tsx";
import {
    debounce,
    IconButton,
    InputAdornment,
    Paper, Stack,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TextField
} from "@mui/material";
import AddExerciseDialog from "./AddExerciseDialog.tsx";
import SearchIcon from "@mui/icons-material/Search";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from '@mui/icons-material/Edit';

const InitialExercise: ExerciseDto = {
    eid: 0,
    name: "",
    type:"",
}
const ExercisesPage: React.FC = () => {

    const [exercises, setExercises] = useState<ExerciseDto[]>([]);
    const [open, setOpen] = useState(false);
    const [searchCriteria, setSearchCriteria] = useState<string>("")
    const [exercise, setExercise] = useState<ExerciseDto>(InitialExercise)
    const handleClickOpen = (exercise: ExerciseDto) => {
        setExercise(exercise)
        setOpen(true)
    }

    const handleClose = () => {
        setOpen(false)
    }

    const handleChange = ({target} : any) => {
        setExercise({
            ...exercise,
            [target.name] : target.value
        })
    }

    const handleSubmit = (exercise : ExerciseDto) => {
        if(exercise.eid !== 0) {
            editExericse(exercise).then(() =>
                getExercises(searchCriteria).then(() => handleClose()))
        } else {
            addExercise(exercise).then(() => {
                getExercises(searchCriteria).then(() => handleClose())
            })
        }

    }
    const handleRemove = (id : number) =>{
        removeExercise(id).then( () => getExercises(searchCriteria))
    }

    const handleSearch = debounce((event: any) => {
        getExercises(event.target.value)
    }, 500);

    const getExercises = async (searchCriteria : string) => {
        getAllExercisesBySearch(searchCriteria).then(exercises => setExercises(exercises))
    };
    useEffect(() => {
        getExercises(searchCriteria);
    }, [searchCriteria]);

    const renderExercises = () => {
        return <Paper sx={{width: '100%'}}>
            <TableContainer sx={{maxHeight: 9080}} component={Paper}>
                <Table aria-label="sticky table">
                    <TableHead
                    style={{
                        backgroundColor:'#849489',
                    }}
                    >
                        <TableRow>
                            <TableCell
                            style={{width:"400px"}}>
                                Name
                            </TableCell>
                            <TableCell>
                                Type
                            </TableCell>
                            <TableCell>
                                Actions
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody
                        style={{
                            backgroundColor:'#A7BEAE'
                        }}
                    >
                        {exercises
                            .map((exercise) => {
                                return (
                                    <TableRow hover role="checkbox" tabIndex={-1} key={exercise.eid}>
                                        <TableCell>
                                            {exercise.name}
                                        </TableCell>
                                        <TableCell>
                                            {exercise.type}
                                        </TableCell>
                                        <TableCell style={{
                                            width: "100px",
                                        }}>
                                            {/*<IconButton aria-label="delete" onClick={() => handleRemove(exercise.eid)}>
                                                <DeleteIcon />
                                            </IconButton>*/}
                                            <IconButton onClick={() => {
                                                handleClickOpen(exercise)
                                            }}>
                                                <EditIcon/>
                                            </IconButton>
                                        </TableCell>
                                    </TableRow>
                                );
                            })}
                    </TableBody>
                </Table>
            </TableContainer>
        </Paper>
    }


    return (
        <div style={{width:"1000px", margin:"auto"}}>
            <div style={{
                display: 'flex',
                marginTop: '10px',
                marginBottom: '10px',
                justifyContent: 'space-between',
                alignItems: "flex-end"
            }}>
                <h1
                style={{marginBottom:"0px",
                    marginTop:"0px",
                    color:"#606b65"
                }}
                >EXERCISES</h1>
                <Stack direction={"row"}>
                    <TextField
                        onChange={(e) => {
                            setSearchCriteria(e.target.value);
                            handleSearch(e);
                        }}
                        value={searchCriteria}
                        placeholder="Search..."
                        InputProps={{
                            startAdornment: (
                                <InputAdornment position="start">
                                    <SearchIcon color="action"/>
                                </InputAdornment>
                            ),
                            style: {
                                width: "300px",
                                borderRadius: 5,
                                backgroundColor: "#f2f2f2",
                            },
                        }}
                        fullWidth
                    />
                    <button onClick={() => handleClickOpen(InitialExercise)}
                            style={{
                                height: "56px",
                                width: "100px",
                                borderRadius: "4px",
                                backgroundColor: '#7695A8',
                                color: "white"
                            }}>
                        CREATE
                    </button>
                </Stack>
            </div>
            {renderExercises()}
            <AddExerciseDialog
                open={open}
                handleClose={handleClose}
                handleSubmit={handleSubmit}
                exercise={exercise}
                handleChange={handleChange}
            />
        </div>
    );
}
    export default ExercisesPage