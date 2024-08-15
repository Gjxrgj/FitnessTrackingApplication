package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterDto {
    private String username;
    private String password;
    private int age;
    private int height;
    private int weight;

}
