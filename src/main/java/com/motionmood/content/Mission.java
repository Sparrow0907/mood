package com.motionmood.content;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "missions")
public class Mission {
    @Id
    private UUID id;
    private String code;
    private String name;

    @Column(name = "duration_minutes")
    private int durationMinutes;

    private String type;

    @Column(name = "has_music")
    private boolean hasMusic;

    // Геттеры
    public UUID getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public int getDurationMinutes() { return durationMinutes; }
    public String getType() { return type; }
    public boolean isHasMusic() { return hasMusic; }

    // Сеттеры
    public void setId(UUID id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setName(String name) { this.name = name; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
    public void setType(String type) { this.type = type; }
    public void setHasMusic(boolean hasMusic) { this.hasMusic = hasMusic; }
}