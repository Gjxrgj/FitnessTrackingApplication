package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.PersonalizedIngredient;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.dto.PersonalizedIngredientDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.User;

import java.util.List;

@Data
@AllArgsConstructor
public class MealDto {
    private Long mID;
    private Long uid;
    private String name;
    private String type;
    private List<PersonalizedIngredientDto> ingredients;

    private int calories;
    private int proteins;
    private int fats;
    private int carbs;

}
