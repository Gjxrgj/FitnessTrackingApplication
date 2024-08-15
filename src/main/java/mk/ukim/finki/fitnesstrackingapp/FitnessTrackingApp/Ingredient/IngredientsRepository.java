package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient;

import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Ingredient.Ingredient;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredient, Long> {
    @Override
    List<Ingredient> findAll();

    @Query("SELECT i FROM Ingredient i WHERE LOWER(i.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Ingredient> findAllByNameLike(@Param("search") String search);
}
