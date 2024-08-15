package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Exercise.service;

import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Exercise.Exercise;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Exercise.dto.ExerciseDto;

import java.util.List;
import java.util.Optional;

public interface ExerciseService {
    List<Exercise> getAll();
    void addExercise(Exercise exercise);
    ExerciseDto addExerciseFromDto(ExerciseDto exercise);

    Optional<Exercise> findById(Long id);

    List<Exercise> getAllBySearchCriteria(String searchCriteria);

    Long remove(Long id);

    ExerciseDto editExerciseFromDto(ExerciseDto exercise);

    Exercise getById(Long eid);
}
