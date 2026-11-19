package com.motionmood.content;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserHabitRepository extends JpaRepository<UserHabit, UUID> {
    Optional<UserHabit> findByUserIdAndHabitId(UUID userId, UUID habitId);
}