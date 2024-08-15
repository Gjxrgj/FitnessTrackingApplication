package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day.Day;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day.DayRepository;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day.service.DayService;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.User;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.UserRepository;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.dto.RegisterDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DayRepository dayRepository;


    @Override
    public User findByName(String name) {
        return userRepository.findByUsername(name)
                .orElseThrow();
    }

    @Override
    public List<User> listALl() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public UserDto getUserData() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        final Authentication authentication = securityContext.getAuthentication();
        final Object authDetails = authentication.getDetails();
        Map<String, Object> details = (Map<String, Object>) authDetails;
        String username = (String) details.get("username");
        UserDto userDto = toDto(userRepository.findByUsername(username).orElseThrow());
        return userDto;
    }

    @Override
    public User getLoggedUser() {
        return userRepository.getReferenceById(getUserData().getUID());
    }

    @Override
    public void register(RegisterDto registerDto) {

        Day day = new Day(LocalDate.now());
        dayRepository.save(day);
        User user = new User(
                registerDto.getUsername(),
                passwordEncoder.encode(registerDto.getPassword()),
                registerDto.getAge(),
                registerDto.getHeight(),
                registerDto.getWeight()
        );
        user.addDay(day);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    private UserDto toDto(User user) {
        return new UserDto(
                user.getUID(),
                user.getUsername(),
                user.getPassword(),
                user.getAge(),
                user.getHeight(),
                user.getWeight(),
                user.getDays(),
                user.getWorkoutPrograms(),
                user.getRole()
        );
    }
}
