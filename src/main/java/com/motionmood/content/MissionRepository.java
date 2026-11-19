package com.motionmood.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.UUID;

public interface MissionRepository extends JpaRepository<Mission, UUID> {
    @Query(value = """
        SELECT m.* FROM motionmood.mission_rules r
        JOIN motionmood.missions m ON m.id = r.mission_id AND m.is_active = true
        WHERE r.mood = :mood AND r.energy_level = :energy AND r.is_active = true
        ORDER BY RANDOM() LIMIT 1
        """, nativeQuery = true)
    Optional<Mission> findBestMatch(@Param("mood") String mood, @Param("energy") String energy);
}