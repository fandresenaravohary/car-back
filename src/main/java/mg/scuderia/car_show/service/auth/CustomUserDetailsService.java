package mg.scuderia.car_show.service.auth;

import lombok.AllArgsConstructor;
import mg.scuderia.car_show.model.User;
import mg.scuderia.car_show.model.auth.Role;
import mg.scuderia.car_show.model.enums.Permission;
import mg.scuderia.car_show.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public void createUser(User user) {

        if (!user.getEmail().contains("@") || !user.getEmail().contains(".")) {
            throw new RuntimeException("E-mail as not valid.");
        }

        if (user.getPassword().length() < 8) {
            throw new RuntimeException("Password must contain 8 characters minimum.");
        }

        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new RuntimeException("Your e-mail is already register.");
        }

        // Encode user password and make this as the new password that saving in database
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Role userRole = new Role();
        userRole.setRoleName(Permission.CLIENT);
        user.setRole(userRole);

        user = this.userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user match."));
    }

}