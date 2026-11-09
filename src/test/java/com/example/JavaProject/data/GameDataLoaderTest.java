package com.example.JavaProject.data;

import com.example.JavaProject.data.dto.GameDataItem;
import com.example.JavaProject.data.loader.DataFile;
import com.example.JavaProject.data.loader.GameData;
import com.example.JavaProject.data.loader.GameDataException;
import com.example.JavaProject.data.loader.GameDataFiles;
import com.example.JavaProject.data.loader.GameDataLoader;
import com.example.JavaProject.data.validator.GameDataValidator;
import com.example.JavaProject.domain.characters.dto.CharactersData;
import com.example.JavaProject.domain.characters.entity.Position;
import com.example.JavaProject.domain.characters.entity.StatType;
import com.example.JavaProject.domain.endings.dto.EndingData;
import com.example.JavaProject.domain.facilities.dto.FacilityData;
import com.example.JavaProject.global.enums.DecreeType;
import com.example.JavaProject.global.enums.Grade;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameDataLoaderTest {

    private final GameDataLoader loader = new GameDataLoader(JsonMapper.builder().build());
    private final GameDataValidator validator =
            new GameDataValidator(Validation.buildDefaultValidatorFactory().getValidator());

    @Test
    void 실제_game_data를_모두_읽고_검증을_통과한다() {
        GameDataFiles files = loader.load();
        validator.validate(files);
        GameData data = GameData.from(files);

        assertThat(data.characters()).hasSize(6);
        assertThat(data.factions()).hasSize(5);
        assertThat(data.facilities()).hasSize(3);
        assertThat(data.cards()).hasSize(8);
        assertThat(data.events()).hasSize(5);
        assertThat(data.endings()).hasSize(7).extracting(EndingData::priority).isSorted();
    }

    @Test
    void JSON_문자열이_Enum으로_변환된다() {
        GameData data = GameData.from(loader.load());

        CharactersData president = data.characters().get("EXE_001");
        assertThat(president.position()).isEqualTo(Position.PRESIDENT);
        assertThat(president.grade()).isEqualTo(Grade.PRESIDENT);
        assertThat(president.stats()).containsOnlyKeys(StatType.values());
        assertThat(data.cards().get("DECREE_PRESIDENT_ORDER").decreeType()).isEqualTo(DecreeType.SEIZE);
    }

    @Test
    void 잘못된_Enum_값은_파일_경로와_함께_실패한다() {
        assertThatThrownBy(() -> parse("facilities/facilities.json", """
                [{ "id": "F1", "name": "성", "type": "CASTLE", "description": "x",
                   "initialDurability": 50, "initialStatus": "OPERATIONAL" }]
                """, FacilityData.class))
                .isInstanceOf(GameDataException.class)
                .hasMessageContaining("[facilities/facilities.json]")
                .hasMessageContaining("CASTLE");
    }

    @Test
    void 정의되지_않은_필드는_실패한다() {
        assertThatThrownBy(() -> parse("facilities/facilities.json", """
                [{ "id": "F1", "name": "댐", "type": "DAM", "description": "x",
                   "initialDurability": 50, "initialStatus": "OPERATIONAL", "durabilty": 3 }]
                """, FacilityData.class))
                .isInstanceOf(GameDataException.class)
                .hasMessageContaining("durabilty");
    }

    private <T extends GameDataItem> DataFile<T> parse(String path, String json, Class<T> type) {
        return loader.parse(path, new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)), type);
    }
}
