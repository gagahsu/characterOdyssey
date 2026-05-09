package com.characterodyssey.backend.service;

import com.characterodyssey.backend.entity.LearningRecord;
import com.characterodyssey.backend.entity.LearningRecord.Language;
import com.characterodyssey.backend.entity.Profile;
import com.characterodyssey.backend.repository.LearningRecordRepository;
import com.characterodyssey.backend.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private static final long UNLOCK_THRESHOLD = 10L;

    private final LearningRecordRepository recordRepository;
    private final ProfileRepository profileRepository;

    public boolean isLevelUnlocked(Long profileId, Long userId, String levelId) {
        profileRepository.findByIdAndUserId(profileId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        Language lang = parseLevelLanguage(levelId);
        long correctCount = recordRepository.countByProfileIdAndLanguageAndCorrectTrue(profileId, lang);
        return correctCount >= UNLOCK_THRESHOLD;
    }

    public void saveRecord(Long profileId, Long userId, String character, Language language, boolean correct) {
        Profile profile = profileRepository.findByIdAndUserId(profileId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        var record = new LearningRecord();
        record.setProfile(profile);
        record.setCharacter(character);
        record.setLanguage(language);
        record.setCorrect(correct);
        recordRepository.save(record);
    }

    private Language parseLevelLanguage(String levelId) {
        if (levelId.startsWith("zhuyin")) return Language.ZHUYIN;
        if (levelId.startsWith("en")) return Language.EN;
        if (levelId.startsWith("jp")) return Language.JP;
        if (levelId.startsWith("kr")) return Language.KR;
        throw new IllegalArgumentException("Unknown level: " + levelId);
    }
}
