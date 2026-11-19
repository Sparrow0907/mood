package com.motionmood.content;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface HabitRepository extends JpaRepository<Habit, UUID> {
}