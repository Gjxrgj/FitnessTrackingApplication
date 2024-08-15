package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.service.MealService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/meals")
@RequiredArgsConstructor
public class MealController {
    private final MealService mealService;

    @GetMapping
    public String getMealPage(Model model){
        model.addAttribute("mealsList", mealService.getAllByUserInSession());
        return "meals";
    }
    @GetMapping("/info/{mealID}")
    public String getMeal(Model model, @PathVariable Long mealID){
        model.addAttribute("meal", mealService.getById(mealID));
        return "mealInfo";
    }
    @GetMapping("/addMeal")
    public String getAddMealPage(){
        return "addMeal";
    }
    @PostMapping("/addMeal")
    public String addMeal(@RequestParam String name,
                          @RequestParam String type){
        mealService.addMeal(name ,type);
        return "redirect:/meals";
    }

    @PostMapping("/removeIngredient/{mealID}")
    public String removeMealFromDay(@PathVariable Long mealID, @RequestParam Long ingredientID){
        mealService.removeMealFromDay(mealID, ingredientID);
        return "redirect:/meals/info/" + mealID;
    }
}
