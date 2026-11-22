package com.motionmood.content;

import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MissionService {

    private final MissionRepository missionRepository;

    public MissionService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    public MissionDto getOrCreateMission(String mood, String energy) {
        Optional<Mission> mission = missionRepository.findRandomMission();

        if (mission.isPresent()) {
            Mission m = mission.get();
            return new MissionDto(
                    m.getId().toString(),
                    m.getCode(),
                    m.getName(),
                    m.getDurationMinutes(),
                    m.getType(),
                    m.isHasMusic()
            );
        }

        return new MissionDto(
                "fallback",
                "fallback",
                "Подыши 2 минуты",
                2,
                "audio",
                true
        );
    }
}