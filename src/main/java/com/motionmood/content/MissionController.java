package com.motionmood.content;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/missions")
public class MissionController {

    private final MissionService missionService;

    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    @PostMapping("/match")
    public MissionDto matchMission(@RequestBody MatchRequest request) {
        return missionService.getOrCreateMission(request.mood(), request.energy());
    }
}

record MatchRequest(String mood, String energy) {}
record MissionDto(String id, String title, int durationMinutes, String type) {}