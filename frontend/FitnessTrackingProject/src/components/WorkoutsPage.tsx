import React, {useCallback, useEffect, useState} from "react";
import {PersonalizedExerciseDto, WorkoutDto} from "./Types.tsx";
import {
    addWorkout,
    addWorkoutToDay,
    editWorkout,
    getAllWorkoutsBySearch,
    removeWorkout
} from "../service/WorkoutsService.tsx";
import {
    debounce,
    IconButton,
    InputAdornment,
    Paper,
    Stack,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TextField
} from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import AddWorkoutDialog from "./AddWorkoutDialog.tsx";
import DeleteIcon from "@mui/icons-material/Delete";
import {
    getAllPersonalizedExerciseForWorkout,
    getAllPersonalizedExercises,
    removePersonalizedExercise
} from "../service/PersonalizedExercisesSrvice.tsx";
import {useNavigate} from "react-router-dom";
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';


const InitialWorkout: WorkoutDto = {
    wid: 0,
    name: "",
    duration: 0,
    exercises: []
}
const WorkoutsPage = (): React.ReactNode => {
    const [workouts, setWorkouts] = useState<Array<WorkoutDto>>([]);
    const [exercises, setExercises] =
        useState<Array<PersonalizedExerciseDto>>([]);
    const[personalizedExercises, setPersonalizedExercises] =
        useState<Array<PersonalizedExerciseDto>>([]);
    const [workout,setWorkout] = useState<WorkoutDto>(InitialWorkout);
    const [open, setOpen] = React.useState(false);
    const [searchCriteria, setSearchCriteria] = useState<string>("")
    const loadExercises = useCallback((workoutId : number) => {
        getAllPersonalizedExerciseForWorkout(workoutId).then(exe => setExercises(exe))
    }, [setExercises])
    const navigate = useNavigate();
    const loadWorkouts = useCallback( (searchCrit: string) => {
        getAllWorkoutsBySearch(searchCrit).then(wrk => {
            setWorkouts(wrk)
            wrk.length && loadExercises(wrk[0].wid)
        })
    }, [setWorkouts])


    const handleClose = () => {
        setOpen(false);
        setWorkout(InitialWorkout)
    };
    const handleClickOpen = async (workoutDto: WorkoutDto) => {
        setWorkout(workoutDto)
        setOpen(true);
        getAllPersonalizedExercises()
            .then(personalizedExercises => {
                if (workoutDto.wid !== 0) {
                    const personalizedExerciseDtos = Array.from(personalizedExercises);
                    workoutDto.exercises.map(exercise => {
                        personalizedExerciseDtos.map((personalizedExercise) => {
                            if (personalizedExercise.exercise.eid == exercise.exercise.eid) {
                                personalizedExercise.sets = exercise.sets;
                                personalizedExercise.reps = exercise.reps;
                                personalizedExercise.weight = exercise.weight;
                                personalizedExercise.time = exercise.time;
                                personalizedExercise.selected = true;
                            }
                        })
                    })
                    setPersonalizedExercises(personalizedExerciseDtos)
                } else {
                    setPersonalizedExercises(personalizedExercises.map(exercise =>
                        ({...exercise, selected: false})))
                }
            })

    };
    const handleRemove = (id: number) => {
        removeWorkout(id).then(() => loadWorkouts(searchCriteria));
    }
    const handleSubmit = (workoutDto: WorkoutDto, personalizedExercises: Array<PersonalizedExerciseDto>) => {
        workoutDto.exercises = personalizedExercises.filter((exercise) => exercise.selected);
        if (workoutDto.wid !== 0) {
            editWorkout(workoutDto).then(() => loadWorkouts(searchCriteria));
        } else {
            addWorkout(workoutDto).then(() => {
                loadWorkouts(searchCriteria)
                setExercises([]);
            })
        }
        handleClose()
    }
    const handleRemoveExercise = (id: number) => {
        removePersonalizedExercise(id).then(() => loadWorkouts(searchCriteria));
    }

    const handleAddWorkoutToDay = (workoutDto: WorkoutDto) => {
            addWorkoutToDay(workoutDto)
                .then(() => navigate('/home'))
    }

    const handleChange = ({target}: any) => {
        setWorkout({
            ...workout,
            [target.name]: target.value
        })
    }

    const handleSearch = debounce((event: any) => {
        loadWorkouts(event.target.value)
    }, 500);

    useEffect(() => {
        loadWorkouts(searchCriteria);
    }, []);

    const renderWorkoutsTable = () => {
        return <Paper sx={{width: '100%'}}>
            <TableContainer sx={{maxHeight: 440}} component={Paper}>
                <Table aria-label="sticky table">
                    <TableHead
                        style={{
                            backgroundColor:"#849489"
                        }}
                    >
                        <TableRow>
                            <TableCell>
                                Name
                            </TableCell>
                            <TableCell>
                                Duration
                            </TableCell>
                            <TableCell>
                                Actions
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody
                        style={{
                            backgroundColor:"#A7BEAE"
                        }}>
                        {workouts
                            .map((workout) => {
                                return (
                                    <TableRow hover role="checkbox" tabIndex={-1} key={workout.wid}
                                              onClick={() => loadExercises(workout.wid)}>
                                        <TableCell>
                                            {workout.name}
                                        </TableCell>
                                        <TableCell>
                                            {workout.duration}&nbsp;min
                                        </TableCell>
                                        <TableCell
                                            style={{width:"120px"}}>
                                            <IconButton aria-label="delete"
                                                        onClick={() => handleRemove(workout.wid)}>
                                                <DeleteIcon/>
                                            </IconButton>
                                            <IconButton onClick={() => {
                                                handleClickOpen(workout)
                                            }} >
                                                <EditIcon/>
                                            </IconButton>
                                            <IconButton onClick={() => handleAddWorkoutToDay(workout)}>
                                                <AddIcon/>
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
    const renderExercisesTable = () => {
        return <Paper sx={{width: '100%'}}>
            <TableContainer sx={{maxHeight: 440}} component={Paper}>
                <Table aria-label="sticky table">
                    <TableHead
                        style={{
                        backgroundColor:"#849489"
                    }}>
                        <TableRow>
                            <TableCell>
                                Name
                            </TableCell>
                            <TableCell>
                            Type
                            </TableCell>
                            <TableCell>
                                Sets
                            </TableCell>
                            <TableCell>
                                Reps
                            </TableCell>
                            <TableCell>
                                Weight
                            </TableCell>
                            <TableCell>
                                Time
                            </TableCell>
                            <TableCell>
                                Actions
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody
                        style={{
                            backgroundColor:"#A7BEAE"
                        }}>
                        {exercises
                            .map((personalzedExercise) => {
                                return (
                                    <TableRow hover role="checkbox" tabIndex={-1} key={personalzedExercise.peid}>
                                        <TableCell>
                                            {personalzedExercise.exercise.name}
                                        </TableCell>
                                        <TableCell>
                                            {personalzedExercise.exercise.type}
                                        </TableCell>
                                        <TableCell>
                                            {personalzedExercise.sets}
                                        </TableCell>
                                        <TableCell>
                                            {personalzedExercise.reps}
                                        </TableCell>
                                        <TableCell>
                                            {personalzedExercise.weight}
                                        </TableCell>
                                        <TableCell>
                                            {personalzedExercise.time}
                                        </TableCell>
                                        <TableCell
                                        style={{width:"60px"}}>
                                            <IconButton aria-label="delete"
                                                        onClick={() =>
                                                            handleRemoveExercise(personalzedExercise.peid)}>
                                                <DeleteIcon/>
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
        <div style={{display: 'flex', margin: '10px', width:"1800px", margin:"auto"}}>
            <div style={{flex: '1', marginRight: '10px'}}>
                <div style={{
                    display: 'flex',
                    marginTop: '10px',
                    marginBottom: '10px',
                    justifyContent: 'space-between',
                    alignItems: "flex-end"
                }}>
                    <h1
                        style={{
                            marginBottom: "0px",
                            marginTop: "0px",
                            color: "#606b65"
                        }}
                    >WORKOUTS</h1>
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
                        <button onClick={() => handleClickOpen(workout)}
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
                {renderWorkoutsTable()}
            </div>
            <div style={{flex: '1', marginTop: "77px"}}>
                {renderExercisesTable()}
            </div>
            <AddWorkoutDialog
                open={open}
                handleClose={handleClose}
                handleSubmit={handleSubmit}
                workout={workout}
                personalizedExercises={personalizedExercises}
                handleChange={handleChange}
            />
        </div>
    );
}
export default WorkoutsPage