package com.motionmood.content;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "mood_history", schema = "motionmood")
public class MoodHistory {
    @Id
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    private String mood;

    @Column(name = "energy_level")
    private String energyLevel;

    @Column(name = "recorded_at")
    private LocalDateTime recordedAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getMood() { return mood; }
    public void setMood(String mood) { this.mood = mood; }
    public String getEnergyLevel() { return energyLevel; }
    public void setEnergyLevel(String energyLevel) { this.energyLevel = energyLevel; }
    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }

    // Переводы для календаря
    public String getMoodRu() {
        return switch (mood) {
            case "tired" -> "Уставший";
            case "anxious" -> "Тревожный";
            case "bored" -> "Скучающий";
            case "energetic" -> "Энергичный";
            case "sad" -> "Грустный";
            case "neutral" -> "Нейтральный";
            case "happy" -> "Радостный";
            case "very_happy" -> "Очень радостный";
            case "angry" -> "Злой";
            case "calm" -> "Спокойный";
            default -> mood;
        };
    }

    public String getEnergyRu() {
        return switch (energyLevel) {
            case "low" -> "Низкая";
            case "medium" -> "Средняя";
            case "high" -> "Высокая";
            default -> energyLevel;
        };
    }
}