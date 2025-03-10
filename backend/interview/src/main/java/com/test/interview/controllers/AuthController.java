package com.test.interview.controllers;


import com.test.interview.data.UserResponse;
import com.test.interview.models.AppUser;
import com.test.interview.repositories.UserRepository;
import com.test.interview.security.JwtUtil;
import com.test.interview.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<AppUser> registerUser(@RequestBody AppUser user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        if (userService.authenticateUser(email, password)) {
            String token = jwtUtil.generateToken(email);
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).body("Credenciales inválidas");
    }
    @GetMapping("/me")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractEmail(token.replace("Bearer ", ""));
        Optional<AppUser> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return ResponseEntity.ok(new UserResponse(user.get()));
        }
        return ResponseEntity.status(404).body("Usuario no encontrado");
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody AppUser updatedUser) {

        String email = jwtUtil.extractEmail(token.replace("Bearer ", ""));
        Optional<AppUser> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent() && existingUser.get().getId().equals(id)) {
            AppUser user = userService.updateUser(id, updatedUser);
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.status(403).body("No tienes permiso para actualizar este usuario.");
    }
}