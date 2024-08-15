package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient;

import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.PersonalizedIngredient.PersonalizedIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalizedIngredientRepository extends JpaRepository<PersonalizedIngredient, Long> {
}
