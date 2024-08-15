package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Workout;

import jakarta.persistence.*;
import lombok.*;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedExercise.PersonalizedExercise;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedExercise.dto.PersonalizedExerciseDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.User;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Workout.dto.WorkoutDto;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "workout")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "_workout_seq")
    @SequenceGenerator(name = "_workout_seq", sequenceName = "_workout_seq", allocationSize = 1)
    @Column(name = "wID")
    private Long wID;

    @Column(name="uID")
    private Long uid;

    @Column(name="name")
    private String name;

    @Column(name = "duration")
    private int duration;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "workouthasexercise",
            joinColumns = @JoinColumn(name = "wID"),
            inverseJoinColumns = @JoinColumn(name = "peID")
    )
    private List<PersonalizedExercise> exercises;

    public Workout() {
        this.exercises = new ArrayList<>();
    }

    public Workout(Long uid, String name, int duration, List<PersonalizedExercise> exercises) {
        this.uid = uid;
        this.name = name;
        this.duration = duration;
        this.exercises = exercises;
    }

    public Workout(String name) {
        this.name = name;
        duration = 0;
        exercises = new ArrayList<>();
    }
    public void addExercise(PersonalizedExercise exercise){
        this.exercises.add(exercise);
    }

    public WorkoutDto toDto(){
        return new WorkoutDto(
                getWID(),
                getUid(),
                getName(),
                getDuration(),
                getExercisesDto()
        );
    }

    private List<PersonalizedExerciseDto> getExercisesDto() {
        List<PersonalizedExerciseDto> temp = new ArrayList<>();
        for(PersonalizedExercise personalizedExercise : getExercises()){
            temp.add(new PersonalizedExerciseDto(
                    personalizedExercise.getId(),
                    personalizedExercise.getExercise().toDto(),
                    personalizedExercise.getSets(),
                    personalizedExercise.getReps(),
                    personalizedExercise.getWeight(),
                    personalizedExercise.getTime()
            ));
        }
        return temp;
    }


}
