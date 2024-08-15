package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Workout.service;

import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedExercise.dto.PersonalizedExerciseDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Workout.Workout;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Workout.dto.WorkoutDto;

import java.util.List;

public interface WorkoutService {
    List<Workout> getAll();
    void addWorkout(String name);

    void addExerciseToWorkout(Long workoutID, Long exerciseID, Integer sets, Integer reps, Integer weight, Integer time);
    Workout getById(Long workoutID);
    void removeExercise(Long workoutID,Long exerciseID);


    List<PersonalizedExerciseDto> getPersonalizedExercisesByWorkoutId(Long workoutId);


    List<PersonalizedExerciseDto> getAllPersonalizedExercises();

    Long removeWorkout(Long id);

    WorkoutDto edit(WorkoutDto workoutDto);

    WorkoutDto add(WorkoutDto workoutDto);

    void addWorkoutToDay(WorkoutDto workoutDto);

    List<Workout> getAllByUser();

    List<Workout> getAllByUserAndSearch(String searchCriteria);
}
