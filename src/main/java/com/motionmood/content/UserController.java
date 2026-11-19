package com.motionmood.content;

import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final MissionSessionRepository sessionRepository;
    private final HabitProgressRepository habitProgressRepository;
    private final MoodHistoryRepository moodHistoryRepository;
    private final UserRepository userRepository;

    public UserController(MissionSessionRepository sessionRepository,
                          HabitProgressRepository habitProgressRepository,
                          MoodHistoryRepository moodHistoryRepository,
                          UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.habitProgressRepository = habitProgressRepository;
        this.moodHistoryRepository = moodHistoryRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/stats")
    public UserStatsDto getUserStats(@RequestParam String userId) {
        UUID userUUID = UUID.fromString(userId);

        // 1. Получаем данные пользователя (почту и имя)
        var user = userRepository.findById(userUUID).orElse(null);
        String email = user != null ? user.getEmail() : "unknown@mail.com";
        String name = email.split("@")[0]; // Имя = всё, что до @

        // 2. Считаем реальную статистику
        long missions = sessionRepository.countByUserId(userUUID);
        long habits = habitProgressRepository.countCompletedHabitsByUserId(userUUID);

        List<MoodHistory> history = moodHistoryRepository.findByUserIdOrderByRecordedAtDesc(userUUID);
        long streak = history.stream()
                .map(h -> h.getRecordedAt().toLocalDate())
                .distinct()
                .count();

        // 3. Возвращаем результат
        return new UserStatsDto(name, email, missions, habits, streak);
    }
}

// DTO для передачи данных профиля на фронтенд
record UserStatsDto(String name, String email, long missions, long habits, long streak) {}