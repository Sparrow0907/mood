package com.motionmood.content;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "habit_progress", schema = "motionmood")
public class HabitProgress {
    @Id
    private UUID id;

    @Column(name = "user_habit_id")
    private UUID userHabitId;

    @Column(name = "check_date")
    private LocalDate checkDate;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    // Геттеры и сеттеры
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserHabitId() { return userHabitId; }
    public void setUserHabitId(UUID userHabitId) { this.userHabitId = userHabitId; }
    public LocalDate getCheckDate() { return checkDate; }
    public void setCheckDate(LocalDate checkDate) { this.checkDate = checkDate; }
    public Boolean getIsCompleted() { return isCompleted; }
    public void setIsCompleted(Boolean isCompleted) { this.isCompleted = isCompleted; }
}