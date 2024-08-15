import {AddMealDialogProps, PersonalizedIngredientDto} from "./Types.tsx";
import * as React from "react";
import {useCallback, useEffect, useState} from "react";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import {
    Checkbox,
    FormControl,
    Input,
    InputLabel,
    MenuItem,
    Select,
    Table,
    TableCell,
    TableRow,
    TextField
} from "@mui/material";
import DialogActions from "@mui/material/DialogActions";
import Button from "@mui/material/Button";

const AddMealDialog = (props: AddMealDialogProps) => {

    const [personalizedIngredients, setPersonalizedIngredients]
        = useState<Array<PersonalizedIngredientDto>>([])

    useEffect(() => {
        setPersonalizedIngredients(props.personalizedIngredients);
    }, [props.personalizedIngredients]);


    const handleIngredientSelection = useCallback((ingredient: PersonalizedIngredientDto, index: number) => {
        setPersonalizedIngredients(prevIngredients => {
            const newList = [...prevIngredients];
            newList[index] = {...ingredient, selected: !ingredient.selected};
            return newList;
        });
    }, [setPersonalizedIngredients]);

    const handleQuantityChange = useCallback((index: number, quantity: string) => {
            const quan: number = Number(quantity);
            setPersonalizedIngredients(prevIngredients => {
                const newList = [...prevIngredients];
                newList[index] = {...newList[index], quantity: quan};
                return newList;
            });
    }, [personalizedIngredients, setPersonalizedIngredients]);

    const renderTable = useCallback(() => {
        return <Table>
            {personalizedIngredients.map((ingredient, index) => {
                return (
                    <TableRow key={ingredient.piid}>
                        <TableCell>
                            <Checkbox
                                checked={ingredient.selected}
                                value={ingredient.selected}
                                onClick={() => handleIngredientSelection(ingredient, index)}
                            />
                        </TableCell>
                        <TableCell>
                            {ingredient.ingredient.name}
                        </TableCell>
                        <TableCell>
                            {(ingredient.quantity !== 0 && ingredient.selected)
                                && <TextField
                                    type={"number"}
                                    value={ingredient.quantity}
                                    label="Quantity"
                                    onChange={(event) => handleQuantityChange(index, event.target.value)}/>
                            }
                            {(ingredient.quantity === 0 && ingredient.selected)  &&
                                <TextField
                                    type={"number"}
                                    label="Quantity"
                                    onChange={(event) => handleQuantityChange(index, event.target.value)}/>
                            }
                        </TableCell>
                    </TableRow>
                )
            })}
        </Table>
    }, [personalizedIngredients])


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
                        props.meal.mid === 0 ?
                            <DialogTitle style={{margin: "auto"}}>ADD MEAL</DialogTitle> :
                            <DialogTitle style={{margin: "auto"}}>EDIT MEAL</DialogTitle>
                    }
                    <DialogContent style={{paddingLeft: "10%"}}>
                        <FormControl variant="standard" fullWidth>
                            <InputLabel id="demo-simple-select-label">Name</InputLabel>
                            <Input
                                value={props.meal.name}
                                required
                                id="standard-adornment-weight"
                                name="name"
                                aria-describedby="standard-weight-helper-text"
                                onChange={(e) => props.handleChange(e)}
                                inputProps={{
                                    'aria-label': 'weight',
                                    required: true,
                                }}
                            />
                        </FormControl>
                        <FormControl sx={{m: 1, mt: 3, width: '55ch'}}>
                            <InputLabel id="demo-simple-select-label">Type</InputLabel>
                            <Select
                                required
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                name="type"
                                label="Type"
                                value={props.meal.type}
                                onChange={(e) => props.handleChange(e)}
                            >
                                <MenuItem value={"Breakfast"}>Breakfast</MenuItem>
                                <MenuItem value={"Lunch"}>Lunch</MenuItem>
                                <MenuItem value={"Dinner"}>Diner</MenuItem>
                                <MenuItem value={"Snack"}>Snack</MenuItem>
                            </Select>
                        </FormControl>
                        {renderTable()}
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={props.handleClose}>CANCEL</Button>
                        {
                            props.meal.mid === 0 ?
                                <Button onClick={() => {
                                    props.handleSubmit(props.meal, personalizedIngredients)
                                }}>ADD</Button> :
                                <Button onClick={() => {
                                    props.handleSubmit(props.meal, personalizedIngredients)
                                }}>EDIT</Button>
                        }
                    </DialogActions>
                </Dialog>
            </React.Fragment>
        </div>
    );
}
export default AddMealDialog