package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day.Day;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.User;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.WorkoutProgram.WorkoutProgram;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long uID;

    private String username;

    private String password;

    private int age;

    private int height;

    private int weight;

    private List<Day> days;

    private List<WorkoutProgram> workoutPrograms;

    private Role role;

    public static UserDto of(User user) {
        UserDto details = new UserDto();
        details.username = user.getUsername();
        details.role = user.getRole();

        return details;
    }

}
