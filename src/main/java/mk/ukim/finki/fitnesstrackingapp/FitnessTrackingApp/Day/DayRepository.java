package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day;

import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {

}
