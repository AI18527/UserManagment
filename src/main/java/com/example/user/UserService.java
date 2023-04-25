package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(UserRequest userRequest) {
        String password = passwordEncoder.encode(userRequest.getPassword());
        User user = new User(userRequest.getUsername(), userRequest.getEmail(), password);
        userRepository.findByEmail(userRequest.getEmail()).ifPresent(u -> {
            throw new IllegalStateException("Email already registered.");
        });
        userRepository.save(user);
        return user;
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public String Login(UserRequest userRequest) {
        Optional<User> user = userRepository.findByEmail(userRequest.getEmail());
        if (user.isPresent()) {
            if (passwordEncoder.matches(userRequest.getPassword(), user.get().getPassword())) {
                return user.get().getId();
            } else {
            throw new IllegalArgumentException("Wrong password.");
            }
        } else {
         throw new IllegalArgumentException("Wrong email");
        }
    }
}
