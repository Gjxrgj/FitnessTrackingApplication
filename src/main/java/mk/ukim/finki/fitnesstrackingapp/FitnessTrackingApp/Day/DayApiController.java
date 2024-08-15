package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day;

import lombok.AllArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day.service.DayService;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/days")
@AllArgsConstructor
public class DayApiController {
    private final UserService userService;
    private final DayService dayService;
    @GetMapping("getDay")
    public Day getDay(){

        return userService.findById(userService.getUserData().getUID()).getToday();
    }
    @DeleteMapping("/removeWorkout")
    public Day removeWorkoutFromDay(@RequestParam Long did, @RequestParam Long wid) {
        return dayService.removeWorkoutFromDay(did, wid);
    }
    @DeleteMapping("/removeMeal")
    public Day removeMealFromDay(@RequestParam Long did, @RequestParam Long mid) {
        return dayService.removeMealFromDay(did, mid);
    }

}
