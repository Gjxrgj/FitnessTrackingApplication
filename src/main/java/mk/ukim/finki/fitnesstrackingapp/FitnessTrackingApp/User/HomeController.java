package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.User;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;
    @GetMapping
    public String getHomePage(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        User foundUser = userRepository.findById(user.getUID()).orElseThrow();
        model.addAttribute("day", foundUser.getToday());
        return "home";
    }
}
