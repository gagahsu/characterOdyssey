package com.characterodyssey.backend.controller;

import com.characterodyssey.backend.entity.LearningRecord.Language;
import com.characterodyssey.backend.service.ProgressService;
import com.characterodyssey.backend.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @GetMapping("/{profileId}/unlocked/{levelId}")
    public Map<String, Boolean> isUnlocked(
            @PathVariable Long profileId,
            @PathVariable String levelId,
            @AuthenticationPrincipal UserPrincipal principal) {
        boolean unlocked = progressService.isLevelUnlocked(profileId, principal.getId(), levelId);
        return Map.of("unlocked", unlocked);
    }

    @PostMapping("/{profileId}/record")
    public void record(
            @PathVariable Long profileId,
            @RequestBody RecordRequest req,
            @AuthenticationPrincipal UserPrincipal principal) {
        progressService.saveRecord(profileId, principal.getId(), req.character(), req.language(), req.correct());
    }

    public record RecordRequest(String character, Language language, boolean correct) {}
}
