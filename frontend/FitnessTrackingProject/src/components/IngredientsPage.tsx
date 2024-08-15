import React, {ChangeEvent, useEffect, useState} from "react";
import {IngredientDto} from "./Types.tsx";
import {
    addIngredient,
    editIngredient,
    getAllIngredientsBySearch,
    removeIngredient
} from "../service/IngredientService.tsx";
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
import AddIngredientDialog from "./AddIngredientDialog.tsx";
import SearchIcon from "@mui/icons-material/Search";
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from "@mui/icons-material/Edit";


let InitialIngredient : IngredientDto = {
    iid: 0,
    name: "",
    calories: 0,
    protein: 0,
    fats: 0,
    carbs: 0,
};
const IngredientsPage: React.FC = () => {
    const [ingredients, setIngredients] = useState<IngredientDto[]>([]);
    const [searchCriteria, setSearchCriteria] = useState<string>("")
    const [ingredient, setIngredient] = useState<IngredientDto>(InitialIngredient)
    const [open, setOpen] = React.useState(false);

    const handleClickOpen = async (ingr: IngredientDto) => {
        setIngredient(ingr);
        setOpen(true);
    };
    const handleRemove = (id : number) =>{
        removeIngredient(id).then( () => loadIngredients(searchCriteria))
    }
    const handleChange = ({target}: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setIngredient({
            ...ingredient,
            [target.name] : target.value
        })
    }

    const handleClose = () => {
        setOpen(false);
    };
    const handleSubmit = (ingr : IngredientDto) => {
        if(ingr.iid!==0){
            editIngredient(ingredient).then(() => {
                loadIngredients(searchCriteria)
                handleClose()
            })
        }
        else{addIngredient(ingr).then(() => {
            loadIngredients(searchCriteria).then(() => handleClose())
        })
        }
    }
    const loadIngredients = async (searchCriteria: string) => {
        getAllIngredientsBySearch(searchCriteria).then(ingredients => setIngredients(ingredients))
    };

    const handleSearch = debounce((event: any) => {
        loadIngredients(event.target.value)
    }, 500);

    useEffect(() => {
        loadIngredients(searchCriteria);
    }, []);

    const renderIngredientTable = () => {
        return <Paper sx={{width: '100%'}}>
            <TableContainer sx={{maxHeight: 980}} component={Paper}>
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
                                Calories per 100g
                            </TableCell>
                            <TableCell>
                                Proteins per 100g
                            </TableCell>
                            <TableCell>
                                Fats per 100g
                            </TableCell>
                            <TableCell>
                                Carbs per 100g
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
                        {ingredients
                            .map((ingredient) => {
                                return (
                                    <TableRow hover role="checkbox" tabIndex={-1} key={ingredient.iid}>
                                        <TableCell>
                                            {ingredient.name}
                                        </TableCell>
                                        <TableCell>
                                            {ingredient.calories}kcal
                                        </TableCell>
                                        <TableCell>
                                            {ingredient.protein}g
                                        </TableCell>
                                        <TableCell>
                                            {ingredient.fats}g
                                        </TableCell>
                                        <TableCell>
                                            {ingredient.carbs}g
                                        </TableCell>
                                        <TableCell>
                                           {/* <IconButton aria-label="delete" onClick={() => handleRemove(ingredient.iid)}>
                                                <DeleteIcon />
                                            </IconButton>*/}
                                            <IconButton onClick={() => {
                                                handleClickOpen(ingredient)}}>
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
                    style={{
                        marginBottom: "0px",
                        marginTop: "0px",
                        color: "#606b65"
                    }}
                >INGREDIENTS</h1>
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
                    <button onClick={() => handleClickOpen(InitialIngredient)} style={{
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
            {renderIngredientTable()}
            <AddIngredientDialog
                open={open}
                handleClose={handleClose}
                handleSubmit={handleSubmit}
                ingredient={ingredient}
                handleChange={handleChange}
            />
        </div>
    );
};

export default IngredientsPage;