package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Exercise;

import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Override
    List<Exercise> findAll();

    @Query("SELECT e FROM Exercise e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Exercise> findAllByNameLike(@Param("search") String search);
}
