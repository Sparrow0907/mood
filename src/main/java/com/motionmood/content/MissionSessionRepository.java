package com.motionmood.content;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface MissionSessionRepository extends JpaRepository<MissionSession, UUID> {
    long countByUserId(UUID userId); // Считает количество сессий пользователя
}