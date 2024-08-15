package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day.service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day.Day;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.Meal;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Workout.Workout;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.User;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day.DayRepository;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.service.MealService;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.service.UserService;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Workout.service.WorkoutService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class DayServiceImpl implements DayService {
    private final DayRepository dayRepository;

    private final MealService mealService;
    private final UserService userService;
    private final HttpSession session;
    private final WorkoutService workoutService;
    @Override
    public Day getDayByDateAndUserID() {
        return null;
    }

    @Override
    @Scheduled(cron="0 0 * * *")
    @Transactional
    public void createDay() {
        for(User user : userService.listALl()){
            Day day = Day.builder()
                    .date(LocalDate.now())
                    .build();
            dayRepository.save(day);
            user.addDay(day);
            userService.save(user);
        }
    }

    @Override
    @Transactional
    public void addWorkoutToDay(Long workoutID) {
        User user = userService.findByName(((User) session.getAttribute("user")).getUsername());
        Day day = user.getToday();
        day.addWorkoutToDay(workoutService.getById(workoutID));
        dayRepository.save(day);
    }

    @Override
    @Transactional
    public void addMealToDay(Meal meal){
        User user = userService.findByName(((User) session.getAttribute("user")).getUsername());
        Day day = user.getToday();
        day.addMealToDay(meal);
        dayRepository.save(day);
    }

    @Override
    @Transactional
    public Day removeWorkoutFromDay(Long dayID, Long workoutId) {
        Workout workout = workoutService.getById(workoutId);
        Day day = dayRepository.findById(dayID).orElseThrow();
        day.getWorkouts().remove(workout);
        return dayRepository.save(day);
    }

    @Override
    @Transactional
    public Day removeMealFromDay(Long dayID, Long mealID) {
        Meal meal = mealService.getById(mealID);
        Day day = dayRepository.findById(dayID).orElseThrow();
        day.getMeals().remove(meal);
        return dayRepository.save(day);
    }
}
