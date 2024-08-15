package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient;

import lombok.AllArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.service.MealService;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.dto.PersonalizedIngredientDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.service.PersonalizedIngredientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/personalizedIngredients")
@AllArgsConstructor
public class PersonalizedIngredientApiController {
    private final MealService mealService;
    private final PersonalizedIngredientService personalizedIngredientService;
    @GetMapping("/{mealId}")
    public List<PersonalizedIngredientDto> getAllIngredientsByMealId(@PathVariable Long mealId) {
        return mealService.getPersonalizedIngredientsFromMeal(mealId);
    }
    @GetMapping("/all")
    public List<PersonalizedIngredientDto> getAll(){
        return personalizedIngredientService.getAll();
    }

    @DeleteMapping("/remove/{id}")
    public Long removePersonalizedIngredient(@PathVariable Long id){
        return personalizedIngredientService.removeById(id);
    }
}
