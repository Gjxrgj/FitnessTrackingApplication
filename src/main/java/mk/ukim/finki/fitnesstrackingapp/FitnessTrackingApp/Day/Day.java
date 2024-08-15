package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day;

import jakarta.persistence.*;
import lombok.*;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.Meal;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Workout.Workout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_day")
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "day_seq")
    @SequenceGenerator(name = "day_seq", sequenceName = "_day_seq", allocationSize = 1)
    @Column(name = "did")
    private Long dID;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "meals")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "dayhasmeal",
            joinColumns = @JoinColumn(name = "did"),
            inverseJoinColumns = @JoinColumn(name = "mid")
    )
    private List<Meal> meals = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "dayhasworkout",
            joinColumns = @JoinColumn(name = "did"),
            inverseJoinColumns = @JoinColumn(name = "wid")
    )
    private List<Workout> workouts = new ArrayList<>();

    public Day(LocalDate date) {
        this.date = date;
    }

    public void addWorkoutToDay(Workout workout){
        workouts.add(workout);
    }
    public void addMealToDay(Meal meal){
        meals.add(meal);
    }
}
