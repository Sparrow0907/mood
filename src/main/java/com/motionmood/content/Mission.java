package com.motionmood.content;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "missions", schema = "motionmood")
public class Mission {
    @Id
    private UUID id;
    private String code;
    private String name;

    @Column(name = "duration_minutes")
    private int durationMinutes;

    private String type;

    // Геттеры
    public UUID getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public int getDurationMinutes() { return durationMinutes; }
    public String getType() { return type; }

    // Сеттеры (добавлены для сохранения из ИИ)
    public void setId(UUID id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setName(String name) { this.name = name; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
    public void setType(String type) { this.type = type; }
}