package com.motionmood.content;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ReflectionRepository extends JpaRepository<Reflection, UUID> {
    Optional<Reflection> findBySessionId(UUID sessionId);
    long countByUserId(UUID userId); // <--- ДОБАВИТЬ
}
