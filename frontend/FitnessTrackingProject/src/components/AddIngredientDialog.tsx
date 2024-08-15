import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import {AddIngredientDialogProps} from "./Types.tsx";
import {FormControl, FormHelperText, Input, InputAdornment} from "@mui/material";

const AddIngredientDialog = (props: AddIngredientDialogProps) => {
    return (
        <React.Fragment>
            <Dialog
                open={props.open}
                onClose={props.handleClose}
            >
                {props.ingredient.iid === 0 ?
                    <DialogTitle style={{margin: "auto"}}>ADD INGREDIENT</DialogTitle> :
                    <DialogTitle style={{margin: "auto"}}>EDIT INGREDIENT</DialogTitle>}

                <form onSubmit={(event) => {
                    event.preventDefault()
                    props.handleSubmit(props.ingredient);
                }}>
                    <DialogContent style={{paddingLeft: "10%"}}>
                        <FormControl variant="standard" sx={{m: 1, mt: 3, width: '52ch'}}>
                            <Input
                                required
                                id="standard-adornment-weight"
                                name="name"
                                aria-describedby="standard-weight-helper-text"
                                onChange={(e) => props.handleChange(e)}
                                value={props.ingredient.name}
                                inputProps={{
                                    'aria-label': 'weight',
                                }}
                            />
                            <FormHelperText id="standard-weight-helper-text">Name</FormHelperText>
                        </FormControl>
                        {
                            props.ingredient.calories !== 0 ?
                                <FormControl variant="standard" sx={{m: 1, mt: 3, width: '25ch'}}>
                                    <Input
                                        required
                                        id="standard-adornment-weight"
                                        name="calories"
                                        value={props.ingredient.calories}
                                        endAdornment={<InputAdornment position="end">kcal</InputAdornment>}
                                        aria-describedby="standard-weight-helper-text"
                                        onChange={(e) => props.handleChange(e)}
                                        inputProps={{
                                            'aria-label': 'weight',
                                        }}
                                    />
                                    <FormHelperText id="standard-weight-helper-text">Calories</FormHelperText>
                                </FormControl>
                                :
                                <FormControl variant="standard" sx={{m: 1, mt: 3, width: '25ch'}}>
                                    <Input
                                        required
                                        id="standard-adornment-weight"
                                        name="calories"
                                        endAdornment={<InputAdornment position="end">kcal</InputAdornment>}
                                        aria-describedby="standard-weight-helper-text"
                                        onChange={(e) => props.handleChange(e)}
                                        inputProps={{
                                            'aria-label': 'weight',
                                        }}
                                    />
                                    <FormHelperText id="standard-weight-helper-text">Calories</FormHelperText>
                                </FormControl>
                        }

                        {
                            props.ingredient.protein !== 0 ?
                                < FormControl variant="standard" sx={{m: 1, mt: 3, width: '25ch'}}>
                                    <Input
                                        required
                                        id="standard-adornment-weight"
                                        name="protein"
                                        value={props.ingredient.protein}
                                        endAdornment={<InputAdornment position="end">g</InputAdornment>}
                                        aria-describedby="standard-weight-helper-text"
                                        onChange={(e) => props.handleChange(e)}
                                        inputProps={{
                                            'aria-label': 'weight',
                                        }}
                                    />
                                    <FormHelperText id="standard-weight-helper-text">Proteins</FormHelperText>
                                </FormControl>
                                :
                                <FormControl variant="standard" sx={{m: 1, mt: 3, width: '25ch'}}>
                                    <Input
                                        required
                                        id="standard-adornment-weight"
                                        name="protein"
                                        endAdornment={<InputAdornment position="end">g</InputAdornment>}
                                        aria-describedby="standard-weight-helper-text"
                                        onChange={(e) => props.handleChange(e)}
                                        inputProps={{
                                            'aria-label': 'weight',
                                        }}
                                    />
                                    <FormHelperText id="standard-weight-helper-text">Proteins</FormHelperText>
                                </FormControl>
                        }
                        {
                            props.ingredient.fats !== 0 ?
                                <FormControl variant="standard" sx={{m: 1, mt: 3, width: '25ch'}}>
                                    <Input
                                        required
                                        id="standard-adornment-weight"
                                        name="fats"
                                        value={props.ingredient.fats}
                                        endAdornment={<InputAdornment position="end">g</InputAdornment>}
                                        aria-describedby="standard-weight-helper-text"
                                        onChange={(e) => props.handleChange(e)}
                                        inputProps={{
                                            'aria-label': 'weight',
                                        }}
                                    />
                                    <FormHelperText id="standard-weight-helper-text">Fats</FormHelperText>
                                </FormControl>
                                :
                                <FormControl variant="standard" sx={{m: 1, mt: 3, width: '25ch'}}>
                                    <Input
                                        required
                                        id="standard-adornment-weight"
                                        name="fats"
                                        endAdornment={<InputAdornment position="end">g</InputAdornment>}
                                        aria-describedby="standard-weight-helper-text"
                                        onChange={(e) => props.handleChange(e)}
                                        inputProps={{
                                            'aria-label': 'weight',
                                        }}
                                    />
                                    <FormHelperText id="standard-weight-helper-text">Fats</FormHelperText>
                                </FormControl>
                        }
                        {
                            props.ingredient.carbs !== 0 ?
                                <FormControl variant="standard" sx={{m: 1, mt: 3, width: '25ch'}}>
                                    <Input
                                        required
                                        id="standard-adornment-weight"
                                        name="carbs"
                                        value={props.ingredient.carbs}
                                        endAdornment={<InputAdornment position="end">g</InputAdornment>}
                                        aria-describedby="standard-weight-helper-text"
                                        onChange={(e) => props.handleChange(e)}
                                        inputProps={{
                                            'aria-label': 'weight',
                                        }}
                                    />
                                    <FormHelperText id="standard-weight-helper-text">Carbs</FormHelperText>
                                </FormControl>
                                :
                                <FormControl variant="standard" sx={{m: 1, mt: 3, width: '25ch'}}>
                                    <Input
                                        required
                                        id="standard-adornment-weight"
                                        name="carbs"
                                        endAdornment={<InputAdornment position="end">g</InputAdornment>}
                                        aria-describedby="standard-weight-helper-text"
                                        onChange={(e) => props.handleChange(e)}
                                        inputProps={{
                                            'aria-label': 'weight',
                                        }}
                                    />
                                    <FormHelperText id="standard-weight-helper-text">Carbs</FormHelperText>
                                </FormControl>
                        }
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={props.handleClose}>CANCEL</Button>
                        {
                            props.ingredient.iid === 0 ?
                                <Button type={"submit"}>ADD</Button> :
                                <Button type={"submit"}>Edit</Button>
                        }
                    </DialogActions>
                </form>
            </Dialog>
        </React.Fragment>
    )
        ;
}
export default AddIngredientDialog