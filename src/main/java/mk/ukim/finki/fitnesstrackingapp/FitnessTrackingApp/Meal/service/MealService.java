package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.service;

import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.Meal;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.dto.MealDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.dto.PersonalizedIngredientDto;

import java.util.List;

public interface MealService {
    List<MealDto> getAll();
    void addMeal(String name, String type);
    void AddIngredientToMeal(Long ingredientId, Long mealID, int quantity);
    Meal getById(Long mealID);
    List<PersonalizedIngredientDto> getPersonalizedIngredientsFromMeal(Long mealId);
    void removeMealFromDay(Long mealID, Long ingredientID);

    List<Meal> getAllByUserInSession();

    List<MealDto> getAllByUser();

    void addNewMeal(MealDto meal);

    List<MealDto> getAllByUserIdAndCriteria(String searchCriteria);

    Long removeMeal(Long id);

    MealDto editMeal(MealDto meal);

    MealDto addMealToDay(MealDto meal);
}
