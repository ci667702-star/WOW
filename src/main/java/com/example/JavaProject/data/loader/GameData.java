package com.example.JavaProject.data.loader;

import com.example.JavaProject.data.dto.GameDataItem;
import com.example.JavaProject.domain.cards.dto.CardData;
import com.example.JavaProject.domain.characters.dto.CharactersData;
import com.example.JavaProject.domain.endings.dto.EndingData;
import com.example.JavaProject.domain.events.dto.EventData;
import com.example.JavaProject.domain.facilities.dto.FacilityData;
import com.example.JavaProject.domain.factions.dto.FactionData;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 검증을 마친 game-data (기본 콘텐츠). 서비스는 이 빈을 주입받아 ID로 조회한다.
 * 맵은 JSON에 적힌 순서를 유지하고 수정할 수 없다. endings는 priority 오름차순이다.
 */
public record GameData(
        Map<String, CharactersData> characters,
        Map<String, FactionData> factions,
        Map<String, FacilityData> facilities,
        Map<String, CardData> cards,
        Map<String, EventData> events,
        List<EndingData> endings
) {
    public static GameData from(GameDataFiles files) {
        return new GameData(
                index(files.characters()),
                index(List.of(files.factions())),
                index(List.of(files.facilities())),
                index(files.cards()),
                index(List.of(files.events())),
                files.endings().items().stream()
                        .sorted(Comparator.comparing(EndingData::priority))
                        .toList());
    }

    private static <T extends GameDataItem> Map<String, T> index(List<DataFile<T>> files) {
        Map<String, T> map = new LinkedHashMap<>();
        files.forEach(file -> file.items().forEach(item -> map.put(item.id(), item)));
        return Collections.unmodifiableMap(map);
    }
}
