package com.test.interview.controllers;

import com.test.interview.models.AppUser;
import com.test.interview.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 🔹 Register a new user (POST)
    @PostMapping("/register")
    public ResponseEntity<AppUser> registerUser(@RequestBody AppUser user) {
        AppUser newUser = userService.registerUser(user);
        return ResponseEntity.ok(newUser);
    }

    // 🔹 Get all users (GET)
    @GetMapping
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // 🔹 Get user by ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Optional<AppUser>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // 🔹 Update user by ID (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<AppUser> updateUser(@PathVariable Long id, @RequestBody AppUser updatedUser) {
        AppUser user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }

    // 🔹 Delete user by ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }
}