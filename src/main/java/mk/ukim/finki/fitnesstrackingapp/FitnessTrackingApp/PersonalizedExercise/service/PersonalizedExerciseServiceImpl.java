package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedExercise.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedExercise.PersonalizedExercise;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedExercise.PersonalizedExerciseRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonalizedExerciseServiceImpl implements PersonalizedExerciseService {
    private final PersonalizedExerciseRepository personalizedExerciseRepository;


    @Override
    @Transactional
    public void addPersonalizedExercise(PersonalizedExercise personalizedExercise) {
        personalizedExerciseRepository.save(personalizedExercise);
    }

    @Override
    public PersonalizedExercise getById(Long id) {
        return personalizedExerciseRepository.findById(id).orElseThrow();
    }

    @Override
    public Long delete(Long id) {
        personalizedExerciseRepository.deleteById(id);
        return id;
    }
}
