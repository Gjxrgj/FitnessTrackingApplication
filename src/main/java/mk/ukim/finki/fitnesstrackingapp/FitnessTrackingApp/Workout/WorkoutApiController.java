package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Workout;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.User;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.service.UserService;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Workout.dto.WorkoutDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Workout.service.WorkoutService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/workouts")
@RestController
@AllArgsConstructor
public class WorkoutApiController {
    private final WorkoutService workoutService;
    private final UserService userService;

    @GetMapping("/listAll")
    public List<Workout> getAllWorkouts(){
        return workoutService.getAllByUser();
    }
    @GetMapping("/search")
    public List<Workout> getAllWorkoutsBySearch(@RequestParam String searchCriteria){
        return workoutService.getAllByUserAndSearch(searchCriteria);
    }
    @DeleteMapping("/remove/{id}")
    public Long removeWorkout(@PathVariable Long id){
        return workoutService.removeWorkout(id);
    }
    @PutMapping("/edit")
    public WorkoutDto editWorkout(@RequestBody WorkoutDto workoutDto){
        return workoutService.edit(workoutDto);
    }
    @PostMapping("/add")
    public WorkoutDto addWorkout(@RequestBody WorkoutDto workoutDto){
        return workoutService.add(workoutDto);
    }

    @PostMapping("/addWorkoutToDay")
    public void addWorkoutToDay(@RequestBody WorkoutDto workoutDto){
        workoutService.addWorkoutToDay(workoutDto);
    }
}
