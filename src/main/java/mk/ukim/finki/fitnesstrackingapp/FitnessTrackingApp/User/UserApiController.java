package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User;

import lombok.AllArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.dto.RegisterDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.dto.UserDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
@AllArgsConstructor
public class UserApiController {
    private final UserService userService;
    @GetMapping(path = "/get-logged-user")
    public UserDto getUserData() throws Exception {
        return userService.getUserData();
    }
    @PostMapping("/register")
    public UserDto registerUser(@RequestBody RegisterDto registerDto) {
        userService.register(registerDto);
        return userService.findByName(registerDto.getUsername()).toDto();
    }
}
