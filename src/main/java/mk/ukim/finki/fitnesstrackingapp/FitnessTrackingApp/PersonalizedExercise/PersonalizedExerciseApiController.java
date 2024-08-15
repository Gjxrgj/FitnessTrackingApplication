package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedExercise;

import lombok.AllArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedExercise.dto.PersonalizedExerciseDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedExercise.service.PersonalizedExerciseService;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Workout.service.WorkoutService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personalizedexercises")
@AllArgsConstructor
public class PersonalizedExerciseApiController {
    private final WorkoutService workoutService;
    private final PersonalizedExerciseService personalizedExerciseService;
    @GetMapping("/{workoutId}")
    public List<PersonalizedExerciseDto> getPersonalizedExercisesByWorkout(@PathVariable Long workoutId){
        return workoutService.getPersonalizedExercisesByWorkoutId(workoutId);
    }
    @GetMapping("/listAll")
    public List<PersonalizedExerciseDto> getAllPersonalizedExercises(){
        return workoutService.getAllPersonalizedExercises();
    }
    @GetMapping("/remove/{id}")
    public Long removePersonalizedExercise(@PathVariable Long id){
        return personalizedExerciseService.delete(id);
    }
}
