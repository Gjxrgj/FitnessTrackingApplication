package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.service;

import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.Ingredient;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.dto.IngredientDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.dto.PersonalizedIngredientDto;

import java.util.List;

public interface IngredientService {
    List<Ingredient> getAll();
    void addIngredient(Ingredient ingredient);

    Ingredient getById(Long id);

    IngredientDto addIngredientFromDto(IngredientDto ingredient);

    List<Ingredient> getAllByCriteria(String searchCriteria);

    Long removeById(Long id);

    IngredientDto editIngredient(IngredientDto ingredient);
}
