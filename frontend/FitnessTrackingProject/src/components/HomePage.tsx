import React, {useCallback, useEffect, useState} from "react";
import {DayDto, MealDto, WorkoutDto} from "./Types.tsx";
import {getDay, removeMealFromDay, removeWorkoutFromDay} from "../service/DaysService.tsx";
import moment from "moment/moment";
import {IconButton, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";

const HomePage = () => {
    const [day, setDay] = useState<DayDto>()
    const day1 = moment(day?.date).clone()
    const loadDay = useCallback(async () => {
        getDay().then(day => setDay(day))
    }, [setDay])
    useEffect(() => {
        loadDay()
    }, []);

    const onRemoveWorkout = (did: number, wid: number) => {
        removeWorkoutFromDay(did, wid).then(() => loadDay())
    }
    const onRemoveMeal = (did: number, mid: number) => {
        removeMealFromDay(did, mid).then(() => loadDay())
    }
    const renderMealsTable = (meals: Array<MealDto> | undefined) => {
        return <Paper sx={{width: '100%'}}>
            <TableContainer component={Paper}>
                <Table
                    sx={{minWidth: 650}}
                    aria-label="simple table"
                >
                    <TableHead
                        style={{
                            backgroundColor: "#ccccb9"
                        }}>
                        <TableRow>
                            <TableCell>Name</TableCell>
                            <TableCell align="right">Calories&nbsp;(kCal)</TableCell>
                            <TableCell align="right">Fat&nbsp;(g)</TableCell>
                            <TableCell align="right">Carbs&nbsp;(g)</TableCell>
                            <TableCell align="right">Protein&nbsp;(g)</TableCell>
                            <TableCell align="right">Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody
                        style={{
                            backgroundColor: "#E7E8D1"
                        }}>
                        {meals?.map((meal) => (
                            <TableRow
                                key={meal.mid}
                                sx={{'&:last-child td, &:last-child th': {border: 0}}}
                            >
                                <TableCell component="th" scope="row">
                                    {meal.name}
                                </TableCell>
                                <TableCell align="right">{meal.calories}</TableCell>
                                <TableCell align="right">{meal.fats}</TableCell>
                                <TableCell align="right">{meal.carbs}</TableCell>
                                <TableCell align="right">{meal.proteins}</TableCell>
                                <TableCell align="right">
                                    {
                                        day?.did &&
                                        <IconButton aria-label="delete" onClick={() => onRemoveMeal(day.did, meal.mid)}>
                                            <DeleteIcon/>
                                        </IconButton>
                                    }
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </Paper>
    }

    const renderWorkoutsTable = (workouts: Array<WorkoutDto> | undefined) => {
        return <Paper sx={{width: '100%'}}>
            <TableContainer component={Paper}>
                <Table sx={{minWidth: 650}} aria-label="simple table">
                    <TableHead style={{backgroundColor: "#ccccb9"}}>
                        <TableRow>
                            <TableCell>Name</TableCell>
                            <TableCell align="right">Duration</TableCell>
                            <TableCell align="right">#Exercises</TableCell>
                            <TableCell align="right">#Sets</TableCell>
                            <TableCell align="right">#Reps</TableCell>
                            <TableCell align="right">Actions</TableCell>
                        </TableRow>
                    </TableHead>

                    <TableBody
                        style={{
                            backgroundColor: "#E7E8D1"
                        }}>
                        {workouts?.map((workout) => (
                            <TableRow
                                key={workout.wid}
                                sx={{'&:last-child td, &:last-child th': {border: 0}}}
                            >
                                <TableCell component="th" scope="row">
                                    {workout.name}
                                </TableCell>
                                <TableCell align="right">{workout.duration}&nbsp;min</TableCell>
                                <TableCell align="right">{workout.exercises.length}</TableCell>
                                <TableCell align="right">{workout.exercises.map((exercise) =>
                                    exercise.sets).reduce((acc, sets) => acc + sets, 0)}</TableCell>
                                <TableCell align="right">{workout.exercises.map((exercise) =>
                                    exercise.reps).reduce((acc, reps) => acc + reps, 0)}</TableCell>
                                <TableCell align="right">
                                    {
                                        day?.did &&
                                        <IconButton aria-label="delete"
                                                    onClick={() => onRemoveWorkout(day.did, workout.wid)}>
                                            <DeleteIcon/>
                                        </IconButton>
                                    }
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </Paper>
    }
    return (
        <div
            style={{
                backgroundColor: "#A7BEAE",
                borderRadius: "10px",
                padding: "30px",
                margin: "auto",
                marginTop: "100px",
                width: "1000px"
            }}>
            <h1 style={{
                color: "white",
                textAlign: "center",
            }}>{day1?.format("ddd DD MMMM YYYY")}</h1>
            <hr/>

            <h2 style={{
                color: "white",
                marginLeft: "15px"
            }}>Meals</h2>
            {renderMealsTable(day?.meals)}

            <h2 style={{
                color: "white",
                marginLeft: "15px"
            }}>Workouts</h2>
            {renderWorkoutsTable(day?.workouts)}
        </div>

    );
}
export default HomePage