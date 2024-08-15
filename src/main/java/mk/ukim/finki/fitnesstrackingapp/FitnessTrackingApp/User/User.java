package mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.Day.Day;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.dto.UserDto;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.WorkoutProgram.WorkoutProgram;
import mk.ukim.finki.fitnesstrackingapp.FitnessTrackingApp.User.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "_user_seq")
    @SequenceGenerator(name = "_user_seq", sequenceName = "_user_seq", allocationSize = 1)
    @Column(name = "uid")
    private Long uID;

    @Column(name = "name", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "height")
    private int height;

    @Column(name = "weight")
    private int weight;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "userhasdays",
            joinColumns = @JoinColumn(name = "uID"),
            inverseJoinColumns = @JoinColumn(name = "dID")
    )
    private List<Day> days = new ArrayList<>();

    public User(String username, String password, int age, int height, int weight) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.role = Role.USER;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "userhasprogram",
            joinColumns = @JoinColumn(name = "uID"),
            inverseJoinColumns = @JoinColumn(name = "wpID")
    )
    private List<WorkoutProgram> workoutPrograms= new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addDay(Day day) {
        days.add(day);
    }

    public Day getToday() {
        return days.get(days.size() - 1);
    }

    public UserDto toDto() {
        return new UserDto(
                this.uID,
                this.getUsername(),
                this.getPassword(),
                this.getAge(),
                this.getHeight(),
                this.getWeight(),
                this.getDays(),
                this.getWorkoutPrograms(),
                this.getRole()
        );
    }
}

