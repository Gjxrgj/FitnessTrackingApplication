package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Exercise;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Exercise.service.ExerciseService;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Workout.service.WorkoutService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/exercises")
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService exerciseService;
    private final WorkoutService workoutService;
    @GetMapping
    public String getExercisePage(Model model){
        model.addAttribute("exerciseList", exerciseService.getAll());
        return "exercise";
    }

    @GetMapping("/{workoutID}")
    public String getExercises(@PathVariable Long workoutID,
                               Model model){
        model.addAttribute("exerciseList", exerciseService.getAll());
        model.addAttribute("exerciseID", workoutID);
        return "exercise";
    }
    @GetMapping("/addExercise")
    public String getAddExercisePage(){
        return "addExercise";
    }
    @PostMapping("/addExercise")
    public String addExercise(@RequestParam String name,
                              @RequestParam String type
                              ){
        exerciseService.addExercise(new Exercise(name, type));
        return "redirect:/exercises";
    }
    @PostMapping("/addExerciseToWorkout")
    public String addExerciseToWorkout(
            @RequestParam(required = false) Integer sets,
            @RequestParam(required = false) Integer reps,
            @RequestParam(required = false) Integer weight,
            @RequestParam(required = false) Integer time,
            @RequestParam Long exerciseID,
            @RequestParam Long workoutID

    ){
        workoutService.addExerciseToWorkout(workoutID,exerciseID, sets, reps, weight, time);
        return "redirect:/workouts";
    }

}
