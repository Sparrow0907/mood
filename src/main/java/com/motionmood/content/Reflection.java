package com.motionmood.content;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reflections", schema = "motionmood")
public class Reflection {
    @Id
    private UUID id;

    @Column(name = "session_id")
    private UUID sessionId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "mood_after")
    private String moodAfter;

    @Column(name = "energy_after")
    private String energyAfter;

    private String note;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Геттеры и сеттеры
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getSessionId() { return sessionId; }
    public void setSessionId(UUID sessionId) { this.sessionId = sessionId; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getMoodAfter() { return moodAfter; }
    public void setMoodAfter(String moodAfter) { this.moodAfter = moodAfter; }
    public String getEnergyAfter() { return energyAfter; }
    public void setEnergyAfter(String energyAfter) { this.energyAfter = energyAfter; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    // ВОТ ЭТОТ СЕТТЕР БЫЛ ПРОПУЩЕН:
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
}