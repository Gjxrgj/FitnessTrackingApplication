package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Exercise.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExerciseDto {
    private Long eID;
    private String name;
    private String type;
}
