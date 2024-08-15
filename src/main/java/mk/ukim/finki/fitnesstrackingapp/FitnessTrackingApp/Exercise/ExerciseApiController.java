package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Exercise;

import lombok.AllArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Exercise.dto.ExerciseDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Exercise.service.ExerciseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@AllArgsConstructor
public class ExerciseApiController {
    private final ExerciseService exerciseService;
    @GetMapping("/listAll")
    public List<Exercise> getAllExercises(){
        return exerciseService.getAll();
    }
    @GetMapping("/search")
    public List<Exercise> getAllExercisesBySearch(@RequestParam String searchCriteria){
        return exerciseService.getAllBySearchCriteria(searchCriteria);
    }
    @PostMapping("/add")
    public ExerciseDto addExercise(@RequestBody ExerciseDto exercise){
        return exerciseService.addExerciseFromDto(exercise);
    }
    @PostMapping("/edit")
    public ExerciseDto editExercise(@RequestBody ExerciseDto exercise){
        return exerciseService.editExerciseFromDto(exercise);
    }
    @PostMapping("/remove/{id}")
    public Long removeExercise(@PathVariable Long id){
        return exerciseService.remove(id);
    }
}
