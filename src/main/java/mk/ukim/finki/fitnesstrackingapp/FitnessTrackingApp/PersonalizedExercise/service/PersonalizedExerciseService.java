package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedExercise.service;

import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedExercise.PersonalizedExercise;

public interface PersonalizedExerciseService {
    void addPersonalizedExercise(PersonalizedExercise personalizedExercise);

    PersonalizedExercise getById(Long id);

    Long delete(Long id);
}
