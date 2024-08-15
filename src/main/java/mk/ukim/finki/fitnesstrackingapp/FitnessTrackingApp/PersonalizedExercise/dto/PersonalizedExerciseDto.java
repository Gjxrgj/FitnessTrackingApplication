package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedExercise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Exercise.Exercise;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Exercise.dto.ExerciseDto;

@Data
@AllArgsConstructor
public class PersonalizedExerciseDto {
    private Long peid;
    private ExerciseDto exerciseDto;
    private int sets;
    private int reps;
    private int weight;
    private int time;

    public Exercise getExercise() {
        return new Exercise(
                exerciseDto.getEID(),
                exerciseDto.getName(),
                exerciseDto.getType()
        );
    }
}
