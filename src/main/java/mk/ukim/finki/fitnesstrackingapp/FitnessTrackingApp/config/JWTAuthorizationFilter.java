package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.dto.UserDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.auth0.jwt.algorithms.Algorithm.HMAC256;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(JwtAuthConstants.HEADER_STRING);

        if (header == null || !header.startsWith(JwtAuthConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String user = JWT.require(HMAC256(JwtAuthConstants.SECRET.getBytes()))
                    .build()
                    .verify(header.replace(JwtAuthConstants.TOKEN_PREFIX, ""))
                    .getSubject();

            if (user == null) {
                return;
            }

            UserDto userDetailsDto = new ObjectMapper().readValue(user, UserDto.class);

            List<SimpleGrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority(userDetailsDto.getRole().toString()));

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetailsDto.getUsername(), null, roles);
            Map<String, Object> details = new HashMap<>();
            details.put("username", userDetailsDto.getUsername());
            token.setDetails(details);
            SecurityContextHolder.getContext().setAuthentication(token);


        } catch (TokenExpiredException e) {
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }
}
