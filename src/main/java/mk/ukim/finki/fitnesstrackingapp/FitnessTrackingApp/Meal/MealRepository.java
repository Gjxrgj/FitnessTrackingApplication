package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal;

import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Meal.Meal;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    @Override
    List<Meal> findAll();


    List<Meal> findAllByUid(Long uid);

    List<Meal> findAllByUidOrderByNameAsc(Long uid);
}
