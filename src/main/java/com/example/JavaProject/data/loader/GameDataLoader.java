package com.example.JavaProject.data.loader;

import com.example.JavaProject.data.dto.GameDataItem;
import com.example.JavaProject.domain.cards.dto.CardData;
import com.example.JavaProject.domain.characters.dto.CharactersData;
import com.example.JavaProject.domain.endings.dto.EndingData;
import com.example.JavaProject.domain.events.dto.EventData;
import com.example.JavaProject.domain.facilities.dto.FacilityData;
import com.example.JavaProject.domain.factions.dto.FactionData;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/** classpath의 game-data JSON 파일을 DTO로 읽는다. 내용 검증은 GameDataValidator가 맡는다. */
@Component
@RequiredArgsConstructor
public class GameDataLoader {

    private static final String BASE_PATH = "game-data/";

    private final ObjectMapper objectMapper;

    public GameDataFiles load() {
        return new GameDataFiles(
                List.of(
                        read("characters/executives.json", CharactersData.class),
                        read("characters/managers.json", CharactersData.class),
                        read("characters/employees.json", CharactersData.class)),
                read("factions/factions.json", FactionData.class),
                read("facilities/facilities.json", FacilityData.class),
                List.of(
                        read("cards/work-cards.json", CardData.class),
                        read("cards/decree-cards.json", CardData.class)),
                read("events/events.json", EventData.class),
                read("endings/endings.json", EndingData.class));
    }

    /** @param path game-data/ 아래의 경로 (예: factions/factions.json) */
    public <T extends GameDataItem> DataFile<T> read(String path, Class<T> type) {
        try (InputStream in = new ClassPathResource(BASE_PATH + path).getInputStream()) {
            return parse(path, in, type);
        } catch (IOException e) {
            throw new GameDataException("[" + path + "] 파일을 열 수 없습니다: " + e.getMessage(), e);
        }
    }

    /** JSON 배열을 읽는다. 정의되지 않은 필드나 잘못된 Enum 값이 있으면 실패한다. */
    public <T extends GameDataItem> DataFile<T> parse(String path, InputStream in, Class<T> type) {
        try {
            List<T> items = objectMapper.readerForListOf(type)
                    .with(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .readValue(in);
            return new DataFile<>(path, items == null ? List.of() : items);
        } catch (JacksonException e) {
            throw new GameDataException("[" + path + "] JSON 형식 오류: " + e.getMessage(), e);
        }
    }
}
