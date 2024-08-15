package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.Ingredient;

@Data
@AllArgsConstructor
public class PersonalizedIngredientDto {
    private Long piid;
    private Ingredient ingredient;
    private int quantity;

    private int calories;
    private int proteins;
    private int fats;
    private int carbs;
}

