package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day.service.DayService;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.service.MealService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/day")
@RequiredArgsConstructor
public class DayController {
    private final MealService mealService;
    private final DayService dayService;
    @GetMapping("/workout/{workoutID}")
    public String addWorkoutToDay(@PathVariable Long workoutID){
        dayService.addWorkoutToDay(workoutID);
        return "redirect:/home";
    }
    @GetMapping("/meal/{mealID}")
    public String addMealToDay(@PathVariable Long mealID){
        dayService.addMealToDay(mealService.getById(mealID));
        return "redirect:/home";
    }
    @PostMapping("/removeWorkout/{workoutID}")
    public String removeWorkoutFromDay(@PathVariable Long workoutID, @RequestParam Long dayID){
        dayService.removeWorkoutFromDay(dayID, workoutID);
        return "redirect:/home";
    }
    @PostMapping("/removeMeal/{mealID}")
    public String removeMealFromDay(@PathVariable Long mealID, @RequestParam Long dayID){
        dayService.removeMealFromDay(dayID, mealID);
        return "redirect:/home";
    }
}
