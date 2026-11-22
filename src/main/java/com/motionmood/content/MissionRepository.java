package com.motionmood.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import java.util.UUID;

public interface MissionRepository extends JpaRepository<Mission, UUID> {

    @Query(value = """
        SELECT m.* FROM motionmood.missions m
        WHERE m.is_active = true
        ORDER BY RANDOM() LIMIT 1
        """, nativeQuery = true)
    Optional<Mission> findRandomMission();
}