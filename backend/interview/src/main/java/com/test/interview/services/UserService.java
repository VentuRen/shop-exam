package com.test.interview.services;

import com.test.interview.models.AppUser;
import com.test.interview.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 🔹 Register a new user
    public AppUser registerUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        return userRepository.save(user);
    }

    // 🔹 Get all users
    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    // 🔹 Get user by ID
    public Optional<AppUser> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // 🔹 Update user
    public AppUser updateUser(Long id, AppUser updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(updatedUser.getFirstName());
                    user.setLastName(updatedUser.getLastName());
                    user.setEmail(updatedUser.getEmail());
                    user.setAddress(updatedUser.getAddress());
                    return userRepository.save(user);
                }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 🔹 Delete user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean authenticateUser(String email, String password) {
        Optional<AppUser> user = userRepository.findByEmail(email);

        // Si el usuario existe, comparar la contraseña ingresada con la almacenada (bcrypt)
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }
}