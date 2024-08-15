import React from "react";
import {Navigate, Route, Routes} from "react-router-dom";
import HomePage from "./HomePage.tsx";
import ExercisesPage from "./ExercisesPage.tsx";
import IngredientsPage from "./IngredientsPage.tsx";
import WorkoutsPage from "./WorkoutsPage.tsx";
import MealsPage from "./MealsPage.tsx";
import ProfilePage from "./ProfilePage.tsx";

const Home = () => {
    return (
        <main style={{ height: "100vh" }}>
                    <Routes>
                            <Route
                                path="/home"
                                element={
                                    <HomePage />
                                }
                            />
                            <Route
                                path="/exercises"
                                element={
                                    <ExercisesPage />
                                }
                            />
                            <Route
                                path="/ingredients"
                                element={
                                    <IngredientsPage />
                                }
                            />
                            <Route
                                path="/workouts"
                                element={<WorkoutsPage/>}
                            />
                            <Route
                                path="/meals"
                                element={<MealsPage/>}
                            />
                            <Route
                                path="/profile"
                                element={<ProfilePage/>}
                            />
                        <Route
                            path="/addIngredient"
                            element={<ProfilePage/>}
                        />
                        <Route
                            path="*"
                            element={<Navigate to="/login" />}
                        />
                    </Routes>
        </main>
    );
};
export default Home;
