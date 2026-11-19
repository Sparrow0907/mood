package com.motionmood.content;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "habits", schema = "motionmood")
public class Habit {
    @Id
    private UUID id;
    private String code;
    private String title;

    public UUID getId() { return id; }
    public String getCode() { return code; }
    public String getTitle() { return title; }

    // Сеттеры для сохранения (хотя мы только читаем, Spring требует их для маппинга)
    public void setId(UUID id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setTitle(String title) { this.title = title; }
}