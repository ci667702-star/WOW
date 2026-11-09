package com.example.JavaProject.data;

import com.example.JavaProject.data.loader.GameDataException;
import com.example.JavaProject.data.loader.GameDataFiles;
import com.example.JavaProject.data.loader.GameDataLoader;
import com.example.JavaProject.data.validator.GameDataValidator;
import com.example.JavaProject.domain.endings.dto.EndingData;
import com.example.JavaProject.domain.events.dto.EventData;
import com.example.JavaProject.domain.facilities.dto.FacilityData;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** 실제 game-data에서 파일 하나만 잘못된 내용으로 바꿔 검증 결과를 확인한다. */
class GameDataValidatorTest {

    private final GameDataLoader loader = new GameDataLoader(JsonMapper.builder().build());
    private final GameDataValidator validator =
            new GameDataValidator(Validation.buildDefaultValidatorFactory().getValidator());
    private final GameDataFiles real = loader.load();

    @Test
    void 필수_필드_누락과_범위_오류를_파일과_항목으로_알려준다() {
        GameDataFiles files = new GameDataFiles(real.characters(), real.factions(),
                loader.parse("facilities/facilities.json", json("""
                        [{ "id": "FAC_BROKEN", "name": "부서진 둑", "type": "DAM",
                           "initialDurability": 150, "initialStatus": "OPERATIONAL" }]
                        """), FacilityData.class),
                real.cards(), real.events(), real.endings());

        List<String> errors = errorsOf(files);

        assertThat(errors)
                .anyMatch(m -> m.startsWith("[facilities/facilities.json #0 FAC_BROKEN]") && m.contains("description"))
                .anyMatch(m -> m.startsWith("[facilities/facilities.json #0 FAC_BROKEN]") && m.contains("initialDurability"));
    }

    @Test
    void 존재하지_않는_ID_참조와_중복_ID를_잡아낸다() {
        GameDataFiles files = new GameDataFiles(real.characters(), real.factions(), real.facilities(),
                real.cards(), real.events(),
                loader.parse("endings/endings.json", json("""
                        [
                          { "id": "END_A", "name": "a", "type": "VICTORY", "reason": "GOAL_ACHIEVED", "priority": 1,
                            "description": "a",
                            "conditions": [ { "target": "FACTION", "targetId": "NO_SUCH_FACTION",
                                              "key": "INFLUENCE", "operator": "GTE", "value": 10 } ] },
                          { "id": "END_A", "name": "b", "type": "DEFEAT", "reason": "COLLAPSE", "priority": 2,
                            "description": "b", "conditions": [] }
                        ]
                        """), EndingData.class));

        List<String> errors = errorsOf(files);

        assertThat(errors)
                .anyMatch(m -> m.startsWith("[endings/endings.json #0 END_A]") && m.contains("'NO_SUCH_FACTION'"))
                .anyMatch(m -> m.startsWith("[endings/endings.json #1 END_A]") && m.contains("중복 ID"));
    }

    @Test
    void 효과_key와_선택지_규칙_위반을_잡아낸다() {
        GameDataFiles files = new GameDataFiles(real.characters(), real.factions(), real.facilities(),
                real.cards(),
                loader.parse("events/events.json", json("""
                        [{ "id": "EVT_X", "name": "x", "type": "RANDOM", "description": "x",
                           "choices": [ {
                             "id": "C1", "text": "x",
                             "check": { "stat": "WISDOM", "difficulty": 50 },
                             "success": { "effects": [
                               { "target": "COMPANY", "key": "HP", "operation": "ADD", "value": 1 },
                               { "target": "DECREE_TARGET", "key": "LOYALTY", "operation": "ADD", "value": 1 }
                             ] }
                           } ] }]
                        """), EventData.class),
                real.endings());

        List<String> errors = errorsOf(files);

        assertThat(errors)
                .anyMatch(m -> m.contains("[events/events.json #0 EVT_X]") && m.contains("check가 있으면 failure가 필요합니다"))
                .anyMatch(m -> m.contains("[events/events.json #0 EVT_X]") && m.contains("'HP'"))
                .anyMatch(m -> m.contains("[events/events.json #0 EVT_X]") && m.contains("DECREE_TARGET는"));
    }

    private List<String> errorsOf(GameDataFiles files) {
        return assertThrows(GameDataException.class, () -> validator.validate(files)).getErrors();
    }

    private static ByteArrayInputStream json(String text) {
        return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
    }
}
