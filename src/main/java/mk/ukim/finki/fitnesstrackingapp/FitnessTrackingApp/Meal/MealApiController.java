package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal;

import lombok.AllArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.dto.MealDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.service.MealService;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meals")
@AllArgsConstructor
public class MealApiController {
    public final MealService mealService;
    private final UserService userService;

    @GetMapping("/listAll")
    public List<MealDto> getMeals(){
        return mealService.getAllByUser();
    }

    @GetMapping("/search")
    public List<MealDto> getMeals(@RequestParam(required = false) String searchCriteria){
        if(searchCriteria == null){
            searchCriteria = "";
        }
        return mealService.getAllByUserIdAndCriteria(searchCriteria);
    }

    @PostMapping("/add")
    public void addMeal(@RequestBody MealDto meal){
        mealService.addNewMeal(meal);
    }

    @PostMapping("/addMealToDay")
    public MealDto addMealToDay(@RequestBody MealDto meal){
        return mealService.addMealToDay(meal);
    }

    @PutMapping("/edit")
    public MealDto editMeal(@RequestBody MealDto meal){
        MealDto mealDto = mealService.editMeal(meal);
        return mealDto;
    }
    @GetMapping("remove/{id}")
    public Long removeMeal(@PathVariable Long id){
        return mealService.removeMeal(id);
    }
}
