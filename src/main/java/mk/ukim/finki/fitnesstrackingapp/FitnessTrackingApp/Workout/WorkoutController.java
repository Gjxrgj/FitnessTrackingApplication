package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Workout;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Workout.service.WorkoutService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/workouts")
@RequiredArgsConstructor
public class   WorkoutController {
    private final WorkoutService workoutService;
    @GetMapping
    public String getWorkoutsPage(Model model){
        model.addAttribute("workoutList", workoutService.getAllByUser());
        return "workouts";
    }
    @GetMapping("/{workoutID}")
    public String getWorkoutsPage(@PathVariable Long workoutID, Model model){
        model.addAttribute("workout", workoutService.getById(workoutID));
        return "workoutInfo";
    }
    @GetMapping("/addWorkout")
    public String getAddWorkoutPage(){
        return "addWorkout";
    }
    @PostMapping("/addWorkout")
    public String addWorkout(@RequestParam String name){
        workoutService.addWorkout(name);
        return "redirect:/workouts";
    }
    @PostMapping("/removeExercise/{workoutID}")
    public String removeExercise(@PathVariable Long workoutID,
                                 @RequestParam Long exerciseID){
        workoutService.removeExercise(workoutID, exerciseID);
        return "redirect:/workouts/" + workoutID;
    }

}
