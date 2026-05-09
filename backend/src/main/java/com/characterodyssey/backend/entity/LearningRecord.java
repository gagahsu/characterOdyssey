package com.characterodyssey.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "learning_records")
@Getter @Setter @NoArgsConstructor
public class LearningRecord {

    public enum Language { ZHUYIN, EN, JP, KR }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Column(nullable = false)
    private String character;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    @Column(nullable = false)
    private boolean correct;

    @Column(nullable = false)
    private Instant answeredAt = Instant.now();
}
