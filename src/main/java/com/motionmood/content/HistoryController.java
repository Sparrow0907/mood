package com.motionmood.content;

import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    private final MoodHistoryRepository historyRepository;

    public HistoryController(MoodHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @GetMapping("/{userId}")
    public List<HistoryDto> getHistory(@PathVariable UUID userId) {
        List<MoodHistory> allHistory = historyRepository.findByUserIdOrderByRecordedAtDesc(userId);

        // Группируем по дате и берем первую (самую свежую) запись за день
        Map<LocalDate, MoodHistory> latestByDate = new LinkedHashMap<>();
        for (MoodHistory h : allHistory) {
            LocalDate date = h.getRecordedAt().toLocalDate();
            if (!latestByDate.containsKey(date)) {
                latestByDate.put(date, h);
            }
        }

        return latestByDate.values().stream()
                .map(h -> new HistoryDto(
                        h.getRecordedAt().toString(),
                        h.getMoodRu(),
                        h.getEnergyRu()
                ))
                .collect(Collectors.toList());
    }
}

record HistoryDto(String recordedAt, String mood, String energy) {}