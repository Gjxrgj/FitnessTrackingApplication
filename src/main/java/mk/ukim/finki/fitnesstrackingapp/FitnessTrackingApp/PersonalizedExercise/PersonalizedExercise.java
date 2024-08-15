package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedExercise;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Exercise.Exercise;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "personalizedexercise")
public class PersonalizedExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "_personalized_exercise_seq")
    @SequenceGenerator(name = "_personalized_exercise_seq", sequenceName = "_personalized_exercise_seq", allocationSize = 1)
    @Column(name = "peID")
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "eID", nullable = false)
    private Exercise exercise;

    @Column(name = "reps")
    private int reps;

    @Column(name = "sets")
    private int sets;

    @Column(name = "weight")
    private int weight;

    @Column(name = "time")
    private int time;

    public PersonalizedExercise(Exercise exercise, int reps, int sets, int weight, int time) {
        this.exercise = exercise;
        this.reps = reps;
        this.sets = sets;
        this.weight = weight;
        this.time = time;
    }
}
