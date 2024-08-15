package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.User;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.UserRepository;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.dto.UserDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;


@Slf4j
@AllArgsConstructor
public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {


    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        User creds = null;
        try {
            creds = new ObjectMapper().readValue(request.getInputStream(), User.class);
    } catch (IOException e) {
            log.error(e.getMessage());
        }

        if (creds == null) {
            throw new UsernameNotFoundException("Invalid creds");
        }
        User user = userService.findByName(creds.getUsername());

        if (!passwordEncoder.matches(creds.getPassword(), user.getPassword())) {
            throw new RuntimeException();
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                creds.getPassword(),
                user.getAuthorities()
        );

        return authenticationManager.authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        User userDetails = (User) authResult.getPrincipal();

        String token = JWT.create()
                .withSubject(new ObjectMapper().writeValueAsString(UserDto.of(userDetails)))
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtAuthConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(JwtAuthConstants.SECRET));

        response.addHeader(JwtAuthConstants.HEADER_STRING, JwtAuthConstants.TOKEN_PREFIX + token);
    }

}
