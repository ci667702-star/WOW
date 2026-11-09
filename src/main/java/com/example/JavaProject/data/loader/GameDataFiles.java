package com.example.JavaProject.data.loader;

import com.example.JavaProject.data.dto.GameDataItem;
import com.example.JavaProject.domain.cards.dto.CardData;
import com.example.JavaProject.domain.characters.dto.CharactersData;
import com.example.JavaProject.domain.endings.dto.EndingData;
import com.example.JavaProject.domain.events.dto.EventData;
import com.example.JavaProject.domain.facilities.dto.FacilityData;
import com.example.JavaProject.domain.factions.dto.FactionData;

import java.util.ArrayList;
import java.util.List;

/** 읽어 들인 game-data 파일 전체 (검증 전 원본) */
public record GameDataFiles(
        List<DataFile<CharactersData>> characters,
        DataFile<FactionData> factions,
        DataFile<FacilityData> facilities,
        List<DataFile<CardData>> cards,
        DataFile<EventData> events,
        DataFile<EndingData> endings
) {
    public List<DataFile<? extends GameDataItem>> all() {
        List<DataFile<? extends GameDataItem>> all = new ArrayList<>(characters);
        all.add(factions);
        all.add(facilities);
        all.addAll(cards);
        all.add(events);
        all.add(endings);
        return all;
    }
}
