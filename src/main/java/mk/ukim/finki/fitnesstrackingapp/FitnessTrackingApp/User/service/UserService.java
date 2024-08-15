package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.service;

import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.User;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.dto.RegisterDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.dto.UserDto;

import java.util.List;

public interface UserService {
    User findByName(String name);
    List<User> listALl();
    void save(User user);


    User findById(Long id);

    UserDto getUserData();
    User getLoggedUser();

    void register(RegisterDto registerDto);
}
