package com.example.JavaProject.domain.characters.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** 인물 간 관계. from 인물이 to 인물을 어떻게 여기는지를 나타낸다(방향 있음). */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"from_character_id", "to_character_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CharacterRelations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "from_character_id", nullable = false)
    private Characters from;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_character_id", nullable = false)
    private Characters to;

    /** 호감도 (-100 적대 ~ 100 우호) */
    @Column(nullable = false)
    private int affinity;

    public CharacterRelations(Characters from, Characters to, int affinity) {
        this.from = from;
        this.to = to;
        this.affinity = affinity;
    }
}
