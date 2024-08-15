package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Workout.service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day.Day;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day.DayRepository;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day.service.DayService;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Exercise.Exercise;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Exercise.dto.ExerciseDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.Ingredient;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedExercise.PersonalizedExercise;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedExercise.PersonalizedExerciseRepository;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedExercise.dto.PersonalizedExerciseDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.PersonalizedIngredient;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.dto.PersonalizedIngredientDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Workout.Workout;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.User;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Workout.WorkoutRepository;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Exercise.service.ExerciseService;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedExercise.service.PersonalizedExerciseService;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.service.UserService;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Workout.dto.WorkoutDto;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;

import java.nio.charset.CoderMalfunctionError;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {
    private final WorkoutRepository workoutRepository;

    private final ExerciseService exerciseService;
    private final HttpSession session;
    private final PersonalizedExerciseService personalizedExerciseService;
    private final UserService userService;
    private final PersonalizedExerciseRepository personalizedExerciseRepository;
    private final DayRepository dayRepository;

    @Override
    public List<Workout> getAll() {
        return workoutRepository.findAll();
    }

    @Override
    @Transactional
    public void addWorkout(String name) {
        workoutRepository.save(
                Workout.builder()
                        .name(name)
                        .build());
    }

    @Override
    @Transactional
    public void addExerciseToWorkout(Long workoutId, Long exerciseID, Integer sets, Integer reps, Integer weight, Integer time) {
        if (time == null) {
            time = 0;
        } else {
            sets = 0;
            reps = 0;
            weight = 0;
        }
        Exercise exercise = exerciseService.findById(exerciseID).orElseThrow();
        PersonalizedExercise personalizedExercise = new PersonalizedExercise(exercise, sets, reps, weight, time);

        personalizedExerciseService.addPersonalizedExercise(personalizedExercise);
        Workout workout = workoutRepository.findById(workoutId).orElseThrow();
        workout.addExercise(personalizedExercise);
        workoutRepository.save(workout);
    }

    @Override
    public Workout getById(Long workoutID) {
        return workoutRepository.findById(workoutID).orElseThrow();
    }

    @Override
    @Transactional
    public void removeExercise(Long workoutID, Long exerciseID) {
        Workout workout = workoutRepository.findById(workoutID).orElseThrow();
        PersonalizedExercise exercise = personalizedExerciseService.getById(exerciseID);
        workout.getExercises().remove(exercise);
        workoutRepository.save(workout);
    }

    @Override
    public List<PersonalizedExerciseDto> getPersonalizedExercisesByWorkoutId(Long workoutId) {
        Workout workout = workoutRepository.findById(workoutId).orElseThrow();
        List<PersonalizedExerciseDto> exercisesTableDto = new ArrayList<>();
        for (PersonalizedExercise exercise : workout.getExercises()) {
            exercisesTableDto.add(new PersonalizedExerciseDto(
                    exercise.getId(),
                    new ExerciseDto(
                            exercise.getExercise().getEID(),
                            exercise.getExercise().getName(),
                            exercise.getExercise().getType()
                    ),
                    exercise.getSets(),
                    exercise.getReps(),
                    exercise.getWeight(),
                    exercise.getTime()
            ));
        }
        return exercisesTableDto;
    }


    @Override
    public List<PersonalizedExerciseDto> getAllPersonalizedExercises() {
        List<PersonalizedExerciseDto> personalizedExercise = new ArrayList<>();
        for (Exercise exercise : exerciseService.getAll()) {
            personalizedExercise.add(new PersonalizedExerciseDto(
                    0L,
                    exercise.toDto(),
                    0,
                    0,
                    0,
                    0
            ));
        }
        return personalizedExercise;
    }

    @Override
    public Long removeWorkout(Long id) {
        Workout workout = workoutRepository.findById(id).orElseThrow();
        workout.setExercises(new ArrayList<>());
        for (PersonalizedExercise personalizedExercise : workout.getExercises()) {
            personalizedExerciseService.delete(personalizedExercise.getId());
        }
        workoutRepository.delete(workout);
        return id;
    }

    @Override
    public WorkoutDto edit(WorkoutDto workoutDto) {
        Workout workout = workoutRepository.findById(workoutDto.getWid()).orElseThrow();
        workout.setName(workoutDto.getName());
        workout.setDuration(workoutDto.getDuration());
        workout.setExercises(workoutDto.getExercises());
        return workoutRepository.save(workout).toDto();
    }

    @Override
    public WorkoutDto add(WorkoutDto workoutDto) {
        Workout workout = new Workout(
                userService.getLoggedUser().getUID(),
                workoutDto.getName(),
                workoutDto.getDuration(),
                workoutDto.getExercises()
        );

        List<PersonalizedExercise> personalizedExercises = new ArrayList<>();

        for (PersonalizedExerciseDto exerciseDto : workoutDto.getExercisesDto()) {
            Exercise exercise = exerciseService.getById(exerciseDto.getExercise().getEID());

            PersonalizedExercise personalizedExercise = new PersonalizedExercise(
                    exercise,
                    exerciseDto.getReps(),
                    exerciseDto.getSets(),
                    exerciseDto.getWeight(),
                    exerciseDto.getTime()
            );
            personalizedExerciseService.addPersonalizedExercise(personalizedExercise);
            personalizedExercises.add(personalizedExercise);
        }

        workout.setExercises(personalizedExercises);
        return workoutRepository.save(workout).toDto();
    }

    @Override
    @Transactional
    public void addWorkoutToDay(WorkoutDto workoutDto) {
        User user = userService.getLoggedUser();
        Day day = user.getToday();
        day.addWorkoutToDay(workoutRepository.findById(workoutDto.getWid()).orElseThrow());

        dayRepository.save(day);
        userService.save(user);


    }

    @Override
    public List<Workout> getAllByUser() {
        return workoutRepository.findAllByUid(
                userService
                        .getLoggedUser()
                        .getUID()
        );
    }

    @Override
    public List<Workout> getAllByUserAndSearch(String searchCriteria) {
        List<Workout> workouts = getAllByUser();
        return workouts.stream()
                .filter(workout -> workout.getName().toLowerCase().contains(searchCriteria.toLowerCase()))
                .sorted(Comparator.comparing(Workout::getName))
                .collect(Collectors.toList());
    }

}
