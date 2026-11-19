package com.motionmood.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface HabitProgressRepository extends JpaRepository<HabitProgress, UUID> {
    Optional<HabitProgress> findByUserHabitIdAndCheckDate(UUID userHabitId, LocalDate checkDate);

    // Считаем ТОЛЬКО выполненные привычки (is_completed = true) для текущего пользователя
    @Query("SELECT COUNT(hp) FROM HabitProgress hp JOIN UserHabit uh ON hp.userHabitId = uh.id WHERE uh.userId = :userId AND hp.isCompleted = true")
    long countCompletedHabitsByUserId(@Param("userId") UUID userId);
}