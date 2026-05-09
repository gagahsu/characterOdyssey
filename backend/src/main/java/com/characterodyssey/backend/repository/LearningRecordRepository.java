package com.characterodyssey.backend.repository;

import com.characterodyssey.backend.entity.LearningRecord;
import com.characterodyssey.backend.entity.LearningRecord.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LearningRecordRepository extends JpaRepository<LearningRecord, Long> {

    List<LearningRecord> findByProfileId(Long profileId);

    @Query("""
        SELECT r.character, COUNT(r), SUM(CASE WHEN r.correct = true THEN 1 ELSE 0 END)
        FROM LearningRecord r
        WHERE r.profile.id = :profileId AND r.language = :language
        GROUP BY r.character
        ORDER BY SUM(CASE WHEN r.correct = true THEN 1 ELSE 0 END) ASC
        """)
    List<Object[]> findWeakCharactersByProfileAndLanguage(Long profileId, Language language);

    long countByProfileIdAndLanguageAndCorrectTrue(Long profileId, Language language);
}
