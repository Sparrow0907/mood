package com.motionmood.content;

import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/reflections")
public class ReflectionController {

    private final ReflectionRepository reflectionRepository;
    private final MoodHistoryRepository moodHistoryRepository;
    private final MissionSessionRepository missionSessionRepository;
    private final MissionRepository missionRepository;

    public ReflectionController(ReflectionRepository reflectionRepository,
                                MoodHistoryRepository moodHistoryRepository,
                                MissionSessionRepository missionSessionRepository,
                                MissionRepository missionRepository) {
        this.reflectionRepository = reflectionRepository;
        this.moodHistoryRepository = moodHistoryRepository;
        this.missionSessionRepository = missionSessionRepository;
        this.missionRepository = missionRepository;
    }

    @PostMapping
    public String saveReflection(@RequestBody ReflectionRequest request) {
        UUID sessionId = UUID.fromString(request.sessionId());
        UUID userId = UUID.fromString(request.userId());

        // 1. Создаем сессию, если её нет
        if (!missionSessionRepository.existsById(sessionId)) {
            MissionSession session = new MissionSession();
            session.setId(sessionId);
            session.setUserId(userId);
            session.setStartedAt(LocalDateTime.now());
            session.setStatus("completed");
            session.setCompletedAt(LocalDateTime.now());

            // Берем ID любой существующей миссии, чтобы не было ошибки внешнего ключа
            UUID anyMissionId = missionRepository.findAll().stream()
                    .findFirst()
                    .map(Mission::getId)
                    .orElse(UUID.randomUUID());
            session.setMissionId(anyMissionId);

            missionSessionRepository.save(session);
        }

        // 2. Сохраняем рефлексию
        Optional<Reflection> existing = reflectionRepository.findBySessionId(sessionId);
        Reflection reflection = existing.orElseGet(Reflection::new);

        if (reflection.getId() == null) {
            reflection.setId(UUID.randomUUID());
            reflection.setSessionId(sessionId);
        }

        reflection.setUserId(userId);
        reflection.setMoodAfter(request.moodAfter());
        reflection.setEnergyAfter(request.energyAfter());
        reflection.setNote(request.note() != null ? request.note() : "");
        reflection.setCreatedAt(LocalDateTime.now());

        reflectionRepository.save(reflection);

        // 3. Добавляем в историю (для календаря)
        MoodHistory history = new MoodHistory();
        history.setId(UUID.randomUUID());
        history.setUserId(userId);
        history.setMood(request.moodAfter());
        history.setEnergyLevel(request.energyAfter() != null ? request.energyAfter() : "medium");
        history.setRecordedAt(LocalDateTime.now());

        moodHistoryRepository.save(history);

        return "Рефлексия сохранена!";
    }
}

record ReflectionRequest(
        String sessionId,
        String userId,
        String moodAfter,
        String energyAfter,
        String note
) {}