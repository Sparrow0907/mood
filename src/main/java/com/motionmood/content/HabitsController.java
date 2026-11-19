package com.motionmood.content;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/habits")
public class HabitsController {

    private final HabitRepository habitRepository;
    private final UserHabitRepository userHabitRepository;
    private final HabitProgressRepository progressRepository;

    public HabitsController(HabitRepository habitRepository,
                            UserHabitRepository userHabitRepository,
                            HabitProgressRepository progressRepository) {
        this.habitRepository = habitRepository;
        this.userHabitRepository = userHabitRepository;
        this.progressRepository = progressRepository;
    }

    @GetMapping
    public List<HabitDto> getUserHabits(@RequestParam String userId) {
        UUID userUUID = UUID.fromString(userId);
        List<Habit> allHabits = habitRepository.findAll();

        return allHabits.stream().map(habit -> {
            // Привязываем привычку к юзеру, если еще не привязана
            UserHabit userHabit = userHabitRepository.findByUserIdAndHabitId(userUUID, habit.getId())
                    .orElseGet(() -> {
                        UserHabit newUH = new UserHabit();
                        newUH.setId(UUID.randomUUID());
                        newUH.setUserId(userUUID);
                        newUH.setHabitId(habit.getId());
                        newUH.setStatus("active");
                        newUH.setStartedAt(LocalDateTime.now());
                        return userHabitRepository.save(newUH);
                    });

            // Возвращаем ID из user_habits (он нужен для сохранения прогресса)
            return new HabitDto(userHabit.getId().toString(), habit.getTitle());
        }).collect(Collectors.toList());
    }

    @PostMapping("/progress")
    public String saveProgress(@RequestBody ProgressRequest request) {
        UUID userHabitId = UUID.fromString(request.habitId());
        LocalDate today = LocalDate.now();

        Optional<HabitProgress> existing = progressRepository.findByUserHabitIdAndCheckDate(userHabitId, today);

        HabitProgress progress = existing.orElseGet(HabitProgress::new);
        if (progress.getId() == null) {
            progress.setId(UUID.randomUUID());
            progress.setUserHabitId(userHabitId);
            progress.setCheckDate(today);
        }

        progress.setIsCompleted(request.isCompleted());
        progressRepository.save(progress);

        return "Прогресс сохранен!";
    }
}

record HabitDto(String id, String title) {}
record ProgressRequest(String habitId, Boolean isCompleted) {}