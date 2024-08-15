package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day.service;

import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day.Day;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.Meal;

public interface DayService {
    Day getDayByDateAndUserID();
    void createDay();
    void addWorkoutToDay(Long workoutID);
    void addMealToDay(Meal meal);

    Day removeWorkoutFromDay(Long dayID, Long workoutId);
    Day removeMealFromDay(Long dayID, Long mealID);
}

