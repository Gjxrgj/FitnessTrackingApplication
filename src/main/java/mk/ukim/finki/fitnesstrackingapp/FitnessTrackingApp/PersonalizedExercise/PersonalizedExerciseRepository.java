package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedExercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalizedExerciseRepository extends JpaRepository<PersonalizedExercise, Long> {
}
