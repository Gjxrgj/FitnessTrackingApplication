import React, {useCallback, useEffect, useState} from "react";
import {MealDto, PersonalizedIngredientDto} from "./Types.tsx";
import {
    Box,
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
import {addMeal, addMealToDay, editMeal, getMealsByCriteria, removeMeal} from "../service/MealService.tsx";
import {
    getAllPersonalizedIngredients,
    getIngredientsByMealId,
    removePersonalizedIngredient
} from "../service/IngredientService.tsx";
import AddMealDialog from "./AddMealDialog.tsx";
import {useAuthContext} from "./AuthContext.tsx";
import SearchIcon from '@mui/icons-material/Search';
import DeleteIcon from "@mui/icons-material/Delete";
import {useNavigate} from "react-router-dom";
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';


const MealsPage = () => {
    const [meals, setMeals] = useState<Array<MealDto>>([])
    const [ingredients, setIngredients]
        = useState<Array<PersonalizedIngredientDto>>([])
    const [searchCriteria, setSearchCriteria] = useState<string>("")

    const [personalizedIngredients, setPersonalizedIngredients]
        = useState<Array<PersonalizedIngredientDto>>([])
    const [open, setOpen] = React.useState(false);
    const {user} = useAuthContext()
    const navigate = useNavigate();


    const InitialMeal: MealDto = {
        mid: 0,
        user: user,
        name: '',
        type: '',
        ingredients: [],
        calories: 0,
        proteins: 0,
        fats: 0,
        carbs: 0
    }

    const [meal, setMeal] = useState<MealDto>(InitialMeal)


    const handleSearch = debounce((event: any) => {
        loadMeals(event.target.value)
    }, 500);

    const getPersonalizedIngredients = () => {
        getAllPersonalizedIngredients()
            .then(personalizedIngredients =>
                setPersonalizedIngredients(personalizedIngredients.map(ingredient =>
                    ({...ingredient, selected: false}))))
    }

    const loadIngredients = useCallback((mealId: number) => {
        getIngredientsByMealId(mealId).then(ingredients => setIngredients(ingredients))
    }, [])
    const handleClickOpen = async (mealDto: MealDto) => {
        setMeal(mealDto)
        setOpen(true);
        getAllPersonalizedIngredients()
            .then(personalizedIngredients => {
                if (mealDto.mid !== 0) {
                    const personalizedIngredientDtos = Array.from(personalizedIngredients);
                    mealDto.ingredients.map(ingredient => {
                        personalizedIngredientDtos.map((personalizedIngredient) => {
                            if (personalizedIngredient.ingredient.iid == ingredient.ingredient.iid) {
                                personalizedIngredient.quantity = ingredient.quantity;
                                personalizedIngredient.selected = true;
                            }
                        })
                    })
                    setPersonalizedIngredients(personalizedIngredientDtos)
                } else {
                    setPersonalizedIngredients(personalizedIngredients.map(ingredient =>
                        ({...ingredient, selected: false})))
                }
            })

    };

    const handleClose = () => {
        setOpen(false);
        setMeal(InitialMeal)
    };

    const handleRemove = (id: number) => {
        removeMeal(id).then(() => loadMeals(searchCriteria));
    }

    const handleRemoveIngredient = (id: number) => {
        removePersonalizedIngredient(id).then(() => loadMeals(searchCriteria));
    }

    const handleAddMealToDay = (mealDto: MealDto) => {
        addMealToDay(mealDto)
            .then(() => navigate('/home'))
    }

    const handleSubmit = (mealDto: MealDto, personalizedIngredients: Array<PersonalizedIngredientDto>) => {
        mealDto.ingredients = personalizedIngredients.filter((ingredient) => ingredient.selected);
        if (mealDto.mid !== 0) {
            editMeal({...mealDto, user: undefined}).then(() => loadMeals(searchCriteria));
        } else {
            addMeal(mealDto).then(() => {
                loadMeals(searchCriteria)
                setIngredients([]);
            })
        }
        handleClose()
    }

    const handleChange = ({target}: any) => {
        setMeal({
            ...meal,
            [target.name]: target.value
        })
    }

    const loadMeals = useCallback((search: string) => {
        getMealsByCriteria(search).then((meals) => {
            setMeals(meals);
            meals.length != 0 && loadIngredients(meals[0].mid);
            getPersonalizedIngredients();
        });
    }, [setMeals])

    useEffect(() => {
        loadMeals(searchCriteria);
    }, [setMeals]);

    const renderMealTable = () => {
        return <Paper sx={{width: '100%'}}>
            <TableContainer sx={{maxHeight: 1080}} component={Paper}>
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
                                Calories
                            </TableCell>
                            <TableCell>
                                Proteins
                            </TableCell>
                            <TableCell>
                                Fats
                            </TableCell>
                            <TableCell>
                                Carbs
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
                        {meals
                            .map((meal) => {
                                return (
                                    <TableRow hover role="checkbox" tabIndex={-1} key={meal.mid}
                                              onClick={() => loadIngredients(meal.mid)}>
                                        <TableCell>
                                            {meal.name}
                                        </TableCell>
                                        <TableCell>
                                            {meal.type}
                                        </TableCell>
                                        <TableCell>
                                            {meal.calories}
                                        </TableCell>
                                        <TableCell>
                                            {meal.proteins}
                                        </TableCell>
                                        <TableCell>
                                            {meal.fats}
                                        </TableCell>
                                        <TableCell>
                                            {meal.carbs}
                                        </TableCell>
                                        <TableCell>
                                            <IconButton aria-label="delete"
                                                        onClick={() => handleRemove(meal.mid)}>
                                                <DeleteIcon/>
                                            </IconButton>
                                            <IconButton onClick={() => {
                                                handleClickOpen(meal)
                                            }}>
                                                <EditIcon/>
                                            </IconButton>
                                            <IconButton onClick={() => handleAddMealToDay(meal)}>
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
    const renderIngredientTable = () => {
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
                                Calories
                            </TableCell>
                            <TableCell>
                                Proteins
                            </TableCell>
                            <TableCell>
                                Fats
                            </TableCell>
                            <TableCell>
                                Carbs
                            </TableCell>
                            <TableCell>
                                Quantity
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
                            .map((personalizedIngredient) => {
                                return (
                                    <TableRow hover role="checkbox" tabIndex={-1} key={personalizedIngredient.piid}>
                                        <TableCell>
                                            {personalizedIngredient.ingredient.name}
                                        </TableCell>
                                        <TableCell>
                                            {personalizedIngredient.calories}
                                        </TableCell>
                                        <TableCell>
                                            {personalizedIngredient.proteins}
                                        </TableCell>
                                        <TableCell>
                                            {personalizedIngredient.fats}
                                        </TableCell>
                                        <TableCell>
                                            {personalizedIngredient.carbs}
                                        </TableCell>
                                        <TableCell>
                                            {personalizedIngredient.quantity}
                                        </TableCell>
                                        <TableCell>
                                            <IconButton aria-label="delete"
                                                        onClick={() => handleRemoveIngredient(personalizedIngredient.piid)}>
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
        <Box style={{display: 'block', width:"1800px", margin: 'auto'}}>
            <div style={{display: 'flex', margin: '10px'}}>
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
                        >MEALS</h1>
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
                            <button onClick={() => handleClickOpen(meal)}
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
                    {renderMealTable()}
                </div>
                <div style={{flex: '1', marginTop: "76px"}}>
                    {renderIngredientTable()}
                </div>
            </div>
            <AddMealDialog
                open={open}
                handleClose={handleClose}
                handleSubmit={handleSubmit}
                meal={meal}
                personalizedIngredients={personalizedIngredients}
                handleChange={handleChange}
            />
        </Box>
    );
}
export default MealsPage