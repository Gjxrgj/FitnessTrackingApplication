package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Exercise.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Exercise.Exercise;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Exercise.ExerciseRepository;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Exercise.dto.ExerciseDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.Ingredient;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;


    @Override
    public List<Exercise> getAll() {
        return exerciseRepository.findAll();
    }

    @Override
    @Transactional
    public void addExercise(Exercise exercise) {
        exerciseRepository.save(exercise);
    }

    @Override
    public ExerciseDto addExerciseFromDto(ExerciseDto exercise) {
        Exercise exercise1 = new Exercise(exercise.getName(), exercise.getType());
        exerciseRepository.save(exercise1);
        return exercise;
    }

    @Override
    public Optional<Exercise> findById(Long id) {
        return exerciseRepository.findById(id);
    }

    @Override
    public List<Exercise> getAllBySearchCriteria(String searchCriteria) {
        return exerciseRepository.findAllByNameLike(searchCriteria)
                .stream()
                .sorted(Comparator.comparing(Exercise::getName))
                .toList();
    }

    @Override
    public Long remove(Long id) {
        exerciseRepository.deleteById(id);
        return id;
    }

    @Override
    public ExerciseDto editExerciseFromDto(ExerciseDto exerciseDto) {
        Exercise exercise = exerciseRepository.findById(exerciseDto.getEID()).orElseThrow();
        exercise.setName(exerciseDto.getName());
        exercise.setType(exerciseDto.getType());
        exerciseRepository.save(exercise);
        return exercise.toDto();
    }

    @Override
    public Exercise getById(Long eid) {
        return exerciseRepository.findById(eid).orElseThrow();
    }
}
