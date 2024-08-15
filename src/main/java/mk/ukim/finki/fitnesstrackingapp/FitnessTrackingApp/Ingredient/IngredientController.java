package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient;

import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.service.IngredientService;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.service.MealService;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;
    private final MealService mealService;

    @GetMapping()
    public String getIngredients(Model model){
        model.addAttribute("ingredientList", ingredientService.getAll());

        return "ingredients";
    }
    @GetMapping("/{mealID}")
    public String getIngredientsPage(@PathVariable(required = false) Long mealID, Model model){
        model.addAttribute("ingredientList", ingredientService.getAll());
        model.addAttribute("mealID", mealID);

        return "ingredients";
    }

    @GetMapping("/addIngredient")
    public String getAddIngredientPage(){

        return "addIngredient";
    }
    @PostMapping("/addIngredient")
    public String addIngredient(@RequestParam String name,
                                @RequestParam int calories,
                                @RequestParam int protein,
                                @RequestParam int carbs,
                                @RequestParam int fats

                                ){
        ingredientService.addIngredient(new Ingredient(name, calories, protein, carbs, fats));
        return "redirect:/ingredients";
    }

    @PostMapping("/addIngredientToMeal")
    public String addIngredientToMeal(@RequestParam Long mealID,
                                      @RequestParam Long ingredientID,
                                      @RequestParam int quantity){
        mealService.AddIngredientToMeal(ingredientID, mealID, quantity);

        return "redirect:/meals";
    }

}
