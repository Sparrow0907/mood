package com.motionmood.content;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AiMissionService {

    private final MissionRepository missionRepository;
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.url}")
    private String apiUrl;

    public AiMissionService(MissionRepository missionRepository, WebClient.Builder webClientBuilder) {
        this.missionRepository = missionRepository;
        this.webClient = webClientBuilder.build();
    }

    public MissionDto getOrCreateMission(String mood, String energy) {
        // 1. Ищем в БД
        Optional<Mission> existingMission = missionRepository.findBestMatch(mood, energy);
        if (existingMission.isPresent()) {
            Mission m = existingMission.get();
            return new MissionDto(m.getCode(), m.getName(), m.getDurationMinutes(), m.getType());
        }

        // 2. Генерируем через ИИ
        try {
            String aiTitle = generateWithAi(mood, energy);

            Mission newMission = new Mission();
            newMission.setId(UUID.randomUUID());
            newMission.setCode("ai_" + UUID.randomUUID().toString().substring(0, 8));
            newMission.setName(aiTitle);
            newMission.setDurationMinutes(5);
            newMission.setType("audio");

            missionRepository.save(newMission);
            return new MissionDto(newMission.getCode(), newMission.getName(), newMission.getDurationMinutes(), newMission.getType());

        } catch (Exception e) {
            System.err.println("Ошибка ИИ: " + e.getMessage());
            return new MissionDto("fallback", "Подыши 2 минуты", 2, "audio");
        }
    }

    private String generateWithAi(String mood, String energy) throws Exception {
        String prompt = String.format(
                "Придумай короткую миссию для приложения ментального здоровья. " +
                        "Пользователь чувствует: настроение '%s', энергия '%s'. " +
                        "Напиши только одно короткое название миссии на русском (до 5 слов). " +
                        "Например: 'Заземлиться', 'Выпить воды'.",
                mood, energy
        );

        Map<String, Object> requestBody = Map.of(
                "model", "google/gemma-4-31b-it:free", // Для OpenRouter формат модели такой
                "messages", List.of(Map.of("role", "user", "content", prompt)),
                "max_tokens", 30
        );

        String response = webClient.post()
                .uri(apiUrl)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .header("HTTP-Referer", "http://localhost:8080") // Требуется для OpenRouter
                .header("X-Title", "MotionMood")                  // Требуется для OpenRouter
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Парсим JSON-ответ
        JsonNode root = objectMapper.readTree(response);
        return root.path("choices").get(0).path("message").path("content").asText().trim();
    }
}