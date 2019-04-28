package cl.usach.alaya.service;

import cl.usach.alaya.model.User;
import cl.usach.alaya.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    //Registra un usuario
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegisteredDate(new Date());
        user.setStatus("new user");
        user.setUserType("normal");
        return userRepository.save(user);
    }

    //Verifica si el usuario existe
    public User verifyUser(String email, String pass) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+$");

        // Se valida el email
        Matcher match = pattern.matcher(email);
        if (match.find()) {
            if (userRepository.existsByMail(email)) {
                User user = userRepository.findByMail(email);
                if (passwordEncoder.matches(pass, user.getPassword())) return user;
                return null;
            }
            return null;
        }
        return null;
    }

}
