package com.motionmood.content;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface MoodHistoryRepository extends JpaRepository<MoodHistory, UUID> {
    List<MoodHistory> findByUserIdOrderByRecordedAtDesc(UUID userId);
    long countByUserId(UUID userId); // <--- ДОБАВИТЬ
}