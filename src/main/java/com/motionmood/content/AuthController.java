package com.motionmood.content;

import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            return new AuthResponse(null, "Пользователь с таким email уже существует");
        }

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail(request.email());
        user.setPasswordHash(request.password());
        user.setDisplayName(request.name() != null ? request.name() : "Пользователь");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        // Возвращаем ID нового пользователя
        return new AuthResponse(user.getId().toString(), "Успешная регистрация");
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.email());

        if (userOpt.isPresent() && userOpt.get().getPasswordHash().equals(request.password())) {
            // Возвращаем ID пользователя
            return new AuthResponse(userOpt.get().getId().toString(), "Успешный вход");
        }

        return new AuthResponse(null, "Неверный email или пароль");
    }
}

record AuthRequest(String email, String password, String name) {}
record AuthResponse(String userId, String message) {}