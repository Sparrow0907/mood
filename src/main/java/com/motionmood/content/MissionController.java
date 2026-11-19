package com.motionmood.content;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/missions")
public class MissionController {

    private final AiMissionService aiMissionService;

    public MissionController(AiMissionService aiMissionService) {
        this.aiMissionService = aiMissionService;
    }

    @PostMapping("/match")
    public MissionDto matchMission(@RequestBody MatchRequest request) {
        // Вот здесь мы передаем то, что пришло в запросе!
        return aiMissionService.getOrCreateMission(request.mood(), request.energy());
    }
}

record MatchRequest(String mood, String energy) {}
record MissionDto(String id, String title, int durationMinutes, String type) {}