package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient;

import lombok.AllArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.dto.IngredientDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.service.IngredientService;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.service.MealService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@AllArgsConstructor
public class IngredientApiController {
    private final IngredientService ingredientService;
    private final MealService mealService;

    @GetMapping("/listAll")
    public List<Ingredient> getAllIngredients() {

        return ingredientService.getAll();
    }

    @GetMapping("/search")
    public List<Ingredient> getAllIngredientsBySearch(@RequestParam(required = false) String searchCriteria) {
        if (searchCriteria == null) {
            searchCriteria = "";
        }
        return ingredientService.getAllByCriteria(searchCriteria);
    }

    @GetMapping("/remove/{id}")
    public Long removeIngredient(@PathVariable Long id) {
        return ingredientService.removeById(id);
    }

    @PostMapping("/add")
    public IngredientDto addIngredient(@RequestBody IngredientDto ingredient) {
        return ingredientService.addIngredientFromDto(ingredient);
    }
    @PutMapping("/edit")
    public IngredientDto editIngredient(@RequestBody IngredientDto ingredient) {
        return ingredientService.editIngredient(ingredient);
    }
}