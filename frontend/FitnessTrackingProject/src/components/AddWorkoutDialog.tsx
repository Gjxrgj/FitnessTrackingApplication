import {AddWorkoutDialogProps, PersonalizedExerciseDto} from "./Types.tsx";
import * as React from "react";
import {useCallback, useEffect, useState} from "react";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import {Checkbox, FormControl, Input, InputLabel, Table, TableCell, TableRow, TextField} from "@mui/material";
import DialogActions from "@mui/material/DialogActions";
import Button from "@mui/material/Button";

const AddWorkoutDialog = (props: AddWorkoutDialogProps) => {
    const [personalizedExercises, setPersonalizedExercises]
        = useState<Array<PersonalizedExerciseDto>>(props.personalizedExercises)

    useEffect(() => {
        setPersonalizedExercises(props.personalizedExercises);
    }, [props.personalizedExercises]);


    const handleExerciseSelection = useCallback((exercise: PersonalizedExerciseDto, index: number) => {
        setPersonalizedExercises(prevExercises => {
            const newList = [...prevExercises];
            newList[index] = {...exercise, selected: !exercise.selected};
            return newList;
        });
    }, [setPersonalizedExercises]);

    const handleChange = useCallback((index: number, value: string, name: string) => {
        const val: number = Number(value);
        setPersonalizedExercises(prevExercise => {
            const newList = [...prevExercise];
            newList[index] = {...newList[index], [name]: val};
            return newList;
        });
    }, [personalizedExercises, setPersonalizedExercises]);

    const renderTable = useCallback(() => {
        return <Table>
            {personalizedExercises.map((personalizedExercise, index) => {
                return (
                    <TableRow key={personalizedExercise.peid}>
                        <TableCell>
                            <Checkbox
                                checked={personalizedExercise.selected}
                                value={personalizedExercise.selected}
                                onClick={() => handleExerciseSelection(personalizedExercise, index)}
                            />
                        </TableCell>
                        <TableCell>
                            {personalizedExercise.exercise.name}
                        </TableCell>
                        <TableCell>
                            {personalizedExercise.selected && (
                                <>
                                    {
                                        personalizedExercise.sets !== 0 ?
                                            <TextField
                                                label="Sets"
                                                type={"number"}
                                                name={"sets"}
                                                value={personalizedExercise.sets}
                                                onChange={(event) => handleChange(index, event.target.value, event.target.name)}
                                                style={{
                                                    marginBottom: "10px"
                                                }}
                                            />
                                            :
                                            <TextField
                                                label="Sets"
                                                type={"number"}
                                                name={"sets"}
                                                onChange={(event) => handleChange(index, event.target.value, event.target.name)}
                                                style={{
                                                    marginBottom: "10px"
                                                }}
                                            />
                                    }
                                    {
                                        personalizedExercise.reps !== 0 ?
                                            <TextField
                                                label="Reps"
                                                type={"number"}
                                                name={"reps"}
                                                value={personalizedExercise.reps}

                                                onChange={(event) => handleChange(index, event.target.value, event.target.name)}
                                                style={{
                                                    marginBottom: "10px"
                                                }}
                                            />
                                            :
                                            <TextField
                                                label="Reps"
                                                type={"number"}
                                                name={"reps"}
                                                onChange={(event) => handleChange(index, event.target.value, event.target.name)}
                                                style={{
                                                    marginBottom: "10px"
                                                }}
                                            />
                                    }
                                    {
                                        personalizedExercise.weight !== 0 ?
                                            <TextField
                                                label="Weight"
                                                type={"number"}
                                                name={"weight"}
                                                value={personalizedExercise.weight}
                                                onChange={(event) => handleChange(index, event.target.value, event.target.name)}
                                                style={{
                                                    marginBottom: "10px"
                                                }}
                                            />
                                            :
                                            <TextField
                                                label="Weight"
                                                type={"number"}
                                                name={"weight"}
                                                onChange={(event) => handleChange(index, event.target.value, event.target.name)}
                                                style={{
                                                    marginBottom: "10px"
                                                }}
                                            />
                                    }
                                    {
                                        personalizedExercise.time !== 0 ?
                                            <TextField
                                                label="Time"
                                                type={"number"}
                                                name={"time"}
                                                value={personalizedExercise.time}
                                                onChange={(event) => handleChange(index, event.target.value, event.target.name)}
                                                style={{
                                                    marginBottom: "10px",
                                                }}
                                            />
                                            :
                                            <TextField
                                                label="Time"
                                                type={"number"}
                                                name={"time"}
                                                onChange={(event) => handleChange(index, event.target.value, event.target.name)}
                                                style={{
                                                    marginBottom: "10px",
                                                }}
                                            />
                                    }
                                </>
                            )}
                        </TableCell>
                    </TableRow>
                )
            })}
        </Table>
    }, [personalizedExercises])


    return (
        <div>
            <React.Fragment>
                <Dialog
                    open={props.open}
                    onClose={props.handleClose}
                    PaperProps={{
                        component: 'form',
                    }}
                >
                    {
                        props.workout.wid === 0 ?
                            <DialogTitle style={{margin: "auto"}}>ADD WORKOUT</DialogTitle> :
                            <DialogTitle style={{margin: "auto"}}>EDIT WORKOUT</DialogTitle>
                    }
                    <DialogContent style={{paddingLeft: "10%"}}>
                        <FormControl variant="standard" fullWidth>
                            <InputLabel id="demo-simple-select-label">Name</InputLabel>
                            <Input
                                value={props.workout.name}
                                required
                                id="standard-adornment-weight"
                                name="name"
                                aria-describedby="standard-weight-helper-text"
                                onChange={(e) => props.handleChange(e)}
                                inputProps={{
                                    'aria-label': 'weight',
                                    required: true,
                                }}
                                style={{
                                    marginBottom: "10px"
                                }}
                            />
                        </FormControl>
                        {
                            props.workout.duration !== 0 ?
                                <FormControl variant="standard" fullWidth>
                                    <InputLabel id="demo-simple-select-label">Duration</InputLabel>
                                    <Input
                                        value={props.workout.duration}
                                        required
                                        id="standard-adornment-weight"
                                        name="duration"
                                        aria-describedby="standard-weight-helper-text"
                                        onChange={(e) => props.handleChange(e)}
                                        inputProps={{
                                            'aria-label': 'weight',
                                            required: true,
                                        }}
                                    />
                                </FormControl>
                                :
                                <FormControl variant="standard" fullWidth>
                                    <InputLabel id="demo-simple-select-label">Duration</InputLabel>
                                    <Input
                                        required
                                        id="standard-adornment-weight"
                                        name="duration"
                                        aria-describedby="standard-weight-helper-text"
                                        onChange={(e) => props.handleChange(e)}
                                        inputProps={{
                                            'aria-label': 'weight',
                                            required: true,
                                        }}
                                    />
                                </FormControl>
                        }
                        {(personalizedExercises.length !== 0) && renderTable()}
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={props.handleClose}>CANCEL</Button>
                        {
                            props.workout.wid === 0 ?
                                <Button onClick={() => {
                                    props.handleSubmit(props.workout, personalizedExercises)
                                }}>ADD</Button> :
                                <Button onClick={() => {
                                    props.handleSubmit(props.workout, personalizedExercises)
                                }}>EDIT</Button>
                        }
                    </DialogActions>
                </Dialog>
            </React.Fragment>
        </div>
    );
}
export default AddWorkoutDialog