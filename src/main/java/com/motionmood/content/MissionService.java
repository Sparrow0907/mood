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
        Optional<Mission> existingMission = missionRepository.findBestMatch(mood, energy);

        if (existingMission.isPresent()) {
            Mission m = existingMission.get();
            return new MissionDto(m.getCode(), m.getName(), m.getDurationMinutes(), m.getType());
        }

        return new MissionDto("fallback", "Подыши 2 минуты", 2, "audio");
    }
}