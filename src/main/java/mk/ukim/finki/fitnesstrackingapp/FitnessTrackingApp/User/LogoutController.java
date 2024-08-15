package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logoutt")
@RequiredArgsConstructor

public class LogoutController {
    @GetMapping
    public String logout(HttpServletResponse response, HttpSession session){
        Cookie cookie = new Cookie("Authorization", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        session.invalidate();
        return "redirect:/login";
    }

}
