package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class IngredientDto {
    private Long iid;
    private String name;
    private int calories;
    private int protein;
    private int fats;
    private int carbs;
}
