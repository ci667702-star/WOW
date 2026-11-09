package com.example.JavaProject.domain.factions.entity;

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

/** 파벌 간 관계. from 파벌이 to 파벌을 어떻게 여기는지를 나타낸다(방향 있음). */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"from_faction_id", "to_faction_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FactionRelations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "from_faction_id", nullable = false)
    private Factions from;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_faction_id", nullable = false)
    private Factions to;

    /** 관계 (-100 적대 ~ 100 동맹) */
    @Column(nullable = false)
    private int relation;

    public FactionRelations(Factions from, Factions to, int relation) {
        this.from = from;
        this.to = to;
        this.relation = relation;
    }
}
