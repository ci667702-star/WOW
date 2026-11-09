package com.example.JavaProject.data.loader;

import com.example.JavaProject.data.validator.GameDataValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** 앱 시작 시 game-data를 읽고 검증해 GameData 빈으로 등록한다. 검증에 실패하면 앱이 뜨지 않는다. */
@Slf4j
@Configuration
public class GameDataConfig {

    @Bean
    public GameData gameData(GameDataLoader loader, GameDataValidator validator) {
        GameDataFiles files = loader.load();
        validator.validate(files);
        GameData data = GameData.from(files);
        log.info("game-data 로드 완료: 인물 {}, 파벌 {}, 시설 {}, 카드 {}, 사건 {}, 엔딩 {}",
                data.characters().size(), data.factions().size(), data.facilities().size(),
                data.cards().size(), data.events().size(), data.endings().size());
        return data;
    }
}
