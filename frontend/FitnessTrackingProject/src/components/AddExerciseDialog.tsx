import * as React from "react";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import {FormControl, FormHelperText, Input, InputLabel, MenuItem, Select} from "@mui/material";
import DialogActions from "@mui/material/DialogActions";
import Button from "@mui/material/Button";
import {AddExerciseDialogProps} from "./Types.tsx";

const AddExerciseDialog = (props: AddExerciseDialogProps) => {
    return (
        <React.Fragment>
            <Dialog
                open={props.open}
                onClose={props.handleClose}
            >
                {props.exercise.eid !== 0 ?
                    <Button type={"submit"}>
                        <DialogTitle style={{margin: "auto"}}>EDIT EXERCISE</DialogTitle>

                    </Button>
                    :
                    <Button type={"submit"}>
                        <DialogTitle style={{margin: "auto"}}>ADD EXERCISE</DialogTitle>

                    </Button>
                }
                <form onSubmit={(event) => {
                    event.preventDefault()
                    props.handleSubmit(props.exercise);
                }}>
                    <DialogContent style={{paddingLeft: "10%"}}>
                        <FormControl variant="standard" sx={{m: 1, mt: 3, width: '40ch'}}>
                            <Input
                                required
                                id="standard-adornment-weight"
                                name="name"
                                aria-describedby="standard-weight-helper-text"
                                onChange={(e) => props.handleChange(e)}
                                value={props.exercise.name}
                                inputProps={{
                                    'aria-label': 'weight',
                                    required: true,
                                }}
                            />
                            <FormHelperText id="standard-weight-helper-text">Name</FormHelperText>
                        </FormControl>

                        <FormControl fullWidth>
                            <InputLabel id="demo-simple-select-label">Type</InputLabel>
                            <Select
                                value={props.exercise.type}
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                name="type"
                                label="Type"
                                onChange={(e) => props.handleChange(e)}
                                required
                            >
                                <MenuItem value={"Bodybuilding"}>Bodybuilding</MenuItem>
                                <MenuItem value={"Cardio"}>Cardio</MenuItem>
                            </Select>
                        </FormControl>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={props.handleClose}>CANCEL</Button>
                        {props.exercise.eid !== 0 ?
                            <Button type={"submit"}>
                                EDIT
                            </Button>
                            :
                            <Button type={"submit"}>
                                ADD
                            </Button>
                        }
                    </DialogActions>
                </form>
            </Dialog>
        </React.Fragment>
    );
}
export default AddExerciseDialog