package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Workout.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedExercise.PersonalizedExercise;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedExercise.dto.PersonalizedExerciseDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.User;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class WorkoutDto {
    private Long wid;
    private Long uid;
    private String name;
     private int duration;
     private List<PersonalizedExerciseDto> exercises;

    public List<PersonalizedExercise> getExercises() {
        List<PersonalizedExercise> temp = new ArrayList<>();
        for(PersonalizedExerciseDto exerciseDto: this.exercises){
            temp.add(new PersonalizedExercise(
                    exerciseDto.getExercise(),
                    exerciseDto.getReps(),
                    exerciseDto.getSets(),
                    exerciseDto.getWeight(),
                    exerciseDto.getTime()
            ));
        }
        return temp;
    }
    public List<PersonalizedExerciseDto> getExercisesDto() {
        return exercises;
    }
}
