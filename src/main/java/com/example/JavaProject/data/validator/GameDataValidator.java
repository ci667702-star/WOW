package com.example.JavaProject.data.validator;

import com.example.JavaProject.data.dto.ChoiceData;
import com.example.JavaProject.data.dto.ConditionData;
import com.example.JavaProject.data.dto.EffectData;
import com.example.JavaProject.data.dto.GameDataItem;
import com.example.JavaProject.data.dto.NextEventData;
import com.example.JavaProject.data.loader.DataFile;
import com.example.JavaProject.data.loader.GameDataException;
import com.example.JavaProject.data.loader.GameDataFiles;
import com.example.JavaProject.domain.cards.dto.CardData;
import com.example.JavaProject.domain.characters.dto.CharactersData;
import com.example.JavaProject.domain.characters.entity.CharacterStatus;
import com.example.JavaProject.domain.characters.entity.Position;
import com.example.JavaProject.domain.characters.entity.StatType;
import com.example.JavaProject.domain.events.dto.EventData;
import com.example.JavaProject.domain.facilities.entity.FacilityStatus;
import com.example.JavaProject.domain.factions.dto.FactionData;
import com.example.JavaProject.global.enums.CardType;
import com.example.JavaProject.global.enums.ConditionTarget;
import com.example.JavaProject.global.enums.DecreeTargetType;
import com.example.JavaProject.global.enums.EffectOperation;
import com.example.JavaProject.global.enums.EffectTarget;
import com.example.JavaProject.global.enums.Grade;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * game-data 검증.
 * 1단계: 필수 필드 누락, 수치 범위 (DTO의 Bean Validation 어노테이션)
 * 2단계: 중복 ID, 존재하지 않는 ID 참조, target별 key, 효과 값, 카드 종류별 규칙
 * 오류는 모두 모아 "[파일 #순번 ID] 내용" 형식으로 한 번에 던진다.
 */
@Component
@RequiredArgsConstructor
public class GameDataValidator {

    private static final Set<String> COMPANY_KEYS = Set.of(
            "IMPERIAL_TRUST", "STAFF_SUPPORT", "ORGANIZATION_CONTROL", "PUBLIC_REPUTATION",
            "BUDGET", "STAFF_FATIGUE", "ORGANIZATION_DISCONTENT");
    private static final Set<String> CHARACTER_KEYS = union(
            Set.of("LOYALTY", "INFLUENCE", "AMBITION", "DISCONTENT", "FATIGUE", "PLAYER_RELATION"),
            enumNames(StatType.class));
    private static final Set<String> FACTION_KEYS = Set.of("INFLUENCE", "ATTITUDE");
    private static final Set<String> FACILITY_KEYS = Set.of("DURABILITY");

    private static final Map<ConditionTarget, Set<String>> CONDITION_KEYS = Map.of(
            ConditionTarget.COMPANY, COMPANY_KEYS,
            ConditionTarget.CHARACTER, CHARACTER_KEYS,
            ConditionTarget.FACTION, FACTION_KEYS,
            ConditionTarget.FACILITY, FACILITY_KEYS,
            ConditionTarget.EVENT, Set.of("OCCURRED"),
            ConditionTarget.REBELLION, Set.of("SUCCEEDED"),
            ConditionTarget.GAME, Set.of("TURN"));

    private static final Map<EffectTarget, Set<String>> EFFECT_KEYS = Map.of(
            EffectTarget.COMPANY, COMPANY_KEYS,
            EffectTarget.CHARACTER, union(CHARACTER_KEYS, Set.of("POSITION", "STATUS")),
            EffectTarget.DECREE_TARGET, union(CHARACTER_KEYS, Set.of("POSITION", "STATUS")),
            EffectTarget.FACTION, FACTION_KEYS,
            EffectTarget.DECREE_TARGET_FACTION, FACTION_KEYS,
            EffectTarget.FACILITY, union(FACILITY_KEYS, Set.of("STATUS")));

    private final Validator beanValidator;

    public void validate(GameDataFiles files) {
        List<String> errors = new ArrayList<>();
        checkConstraints(files, errors);
        // 구조가 깨진 상태에서는 참조 검사가 의미 없으므로 1단계를 통과했을 때만 진행한다.
        if (errors.isEmpty()) {
            new ReferenceCheck(files, errors).run();
        }
        if (!errors.isEmpty()) {
            throw new GameDataException(errors);
        }
    }

    private void checkConstraints(GameDataFiles files, List<String> errors) {
        for (DataFile<? extends GameDataItem> file : files.all()) {
            for (int i = 0; i < file.items().size(); i++) {
                GameDataItem item = file.items().get(i);
                if (item == null) {
                    errors.add(location(file.path(), i, null) + "빈 항목(null)입니다");
                    continue;
                }
                for (ConstraintViolation<GameDataItem> v : beanValidator.validate(item)) {
                    errors.add(location(file.path(), i, item.id()) + v.getPropertyPath() + ": " + v.getMessage());
                }
            }
        }
    }

    private static String location(String path, int index, String id) {
        return "[%s #%d %s] ".formatted(path, index, id == null ? "(id 없음)" : id);
    }

    private static <T extends GameDataItem> Set<String> ids(List<DataFile<T>> files) {
        return files.stream()
                .flatMap(file -> file.items().stream())
                .map(GameDataItem::id)
                .collect(Collectors.toSet());
    }

    private static Set<String> enumNames(Class<? extends Enum<?>> type) {
        return Arrays.stream(type.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @SafeVarargs
    private static Set<String> union(Set<String>... sets) {
        return Stream.of(sets).flatMap(Set::stream).collect(Collectors.toUnmodifiableSet());
    }

    /** 2단계 검사. 현재 검사 중인 위치(파일, 순번, ID)를 기억해 오류 메시지에 붙인다. */
    private static final class ReferenceCheck {

        private final GameDataFiles files;
        private final List<String> errors;
        private final Set<String> characterIds;
        private final Set<String> factionIds;
        private final Set<String> facilityIds;
        private final Set<String> eventIds;
        private String current = "";

        ReferenceCheck(GameDataFiles files, List<String> errors) {
            this.files = files;
            this.errors = errors;
            this.characterIds = ids(files.characters());
            this.factionIds = ids(List.of(files.factions()));
            this.facilityIds = ids(List.of(files.facilities()));
            this.eventIds = ids(List.of(files.events()));
        }

        void run() {
            checkDuplicates(files.characters());
            checkDuplicates(List.of(files.factions()));
            checkDuplicates(List.of(files.facilities()));
            checkDuplicates(files.cards());
            checkDuplicates(List.of(files.events()));
            checkDuplicates(List.of(files.endings()));

            each(files.characters(), this::checkCharacter);
            each(List.of(files.factions()), this::checkFaction);
            each(files.cards(), this::checkCard);
            each(List.of(files.events()), this::checkEvent);
            each(List.of(files.endings()), ending -> checkConditions(ending.conditions(), "conditions"));
        }

        private <T extends GameDataItem> void each(List<DataFile<T>> fileList, Consumer<T> check) {
            for (DataFile<T> file : fileList) {
                for (int i = 0; i < file.items().size(); i++) {
                    T item = file.items().get(i);
                    current = location(file.path(), i, item.id());
                    check.accept(item);
                }
            }
        }

        private void error(String message) {
            errors.add(current + message);
        }

        private void ref(Set<String> ids, String id, String field) {
            if (id == null) {
                error(field + ": 대상 ID가 필요합니다");
            } else if (!ids.contains(id)) {
                error(field + ": 존재하지 않는 ID '" + id + "'");
            }
        }

        /** 같은 종류 안에서 ID 중복 (인물 3개 파일, 카드 2개 파일은 합쳐서 검사) */
        private <T extends GameDataItem> void checkDuplicates(List<DataFile<T>> fileList) {
            Map<String, String> firstSeen = new HashMap<>();
            each(fileList, item -> {
                String previous = firstSeen.putIfAbsent(item.id(), current.strip());
                if (previous != null) {
                    error("중복 ID (처음 정의된 곳: " + previous + ")");
                }
            });
        }

        private void checkCharacter(CharactersData character) {
            if (character.factionId() != null) {
                ref(factionIds, character.factionId(), "factionId");
            }
            EnumSet<StatType> missing = EnumSet.allOf(StatType.class);
            missing.removeAll(character.stats().keySet());
            if (!missing.isEmpty()) {
                error("stats: 누락된 능력치 " + missing);
            }
        }

        private void checkFaction(FactionData faction) {
            for (String other : faction.relations().keySet()) {
                ref(factionIds, other, "relations");
                if (other.equals(faction.id())) {
                    error("relations: 자기 자신과의 관계는 쓸 수 없습니다");
                }
            }
        }

        private void checkCard(CardData card) {
            checkConditions(card.conditions(), "conditions");
            card.relatedCharacters().forEach(id -> ref(characterIds, id, "relatedCharacters"));
            card.relatedFactions().forEach(id -> ref(factionIds, id, "relatedFactions"));
            card.relatedFacilities().forEach(id -> ref(facilityIds, id, "relatedFacilities"));

            boolean decreeTargetAllowed = false;
            if (card.type() == CardType.WORK) {
                if (card.grade() == Grade.PRESIDENT) {
                    error("grade: 업무 카드는 PRESIDENT 등급일 수 없습니다");
                }
                if (card.decreeType() != null || card.targetType() != null || !card.unlockConditions().isEmpty()) {
                    error("업무 카드에는 decreeType, targetType, unlockConditions를 쓸 수 없습니다");
                }
            } else {
                if (card.decreeType() == null) {
                    error("decreeType: 특별지시 카드에 필요합니다");
                }
                if (card.targetType() == null) {
                    error("targetType: 특별지시 카드에 필요합니다");
                }
                boolean president = card.grade() == Grade.PRESIDENT;
                if (president && card.unlockConditions().isEmpty()) {
                    error("unlockConditions: PRESIDENT 카드에는 해금 조건이 필요합니다");
                }
                if (!president && !card.unlockConditions().isEmpty()) {
                    error("unlockConditions: PRESIDENT 카드에만 쓸 수 있습니다");
                }
                checkConditions(card.unlockConditions(), "unlockConditions");
                decreeTargetAllowed = card.targetType() == DecreeTargetType.CHARACTER;
            }
            checkChoices(card.choices(), decreeTargetAllowed);
        }

        private void checkEvent(EventData event) {
            checkConditions(event.conditions(), "conditions");
            checkOutcome(event.effects(), event.nextEvents(), "", false);
            checkChoices(event.choices(), false);
        }

        private void checkConditions(List<ConditionData> conditions, String field) {
            for (int i = 0; i < conditions.size(); i++) {
                ConditionData condition = conditions.get(i);
                String f = field + "[" + i + "]";
                if (!CONDITION_KEYS.get(condition.target()).contains(condition.key())) {
                    error(f + ": " + condition.target() + "에 쓸 수 없는 key '" + condition.key() + "'");
                }
                Set<String> ids = switch (condition.target()) {
                    case CHARACTER -> characterIds;
                    case FACTION -> factionIds;
                    case FACILITY -> facilityIds;
                    case EVENT -> eventIds;
                    default -> null;
                };
                if (ids != null) {
                    ref(ids, condition.targetId(), f + ".targetId");
                }
            }
        }

        private void checkChoices(List<ChoiceData> choices, boolean decreeTargetAllowed) {
            Set<String> seen = new HashSet<>();
            for (ChoiceData choice : choices) {
                String f = "choices[" + choice.id() + "]";
                if (!seen.add(choice.id())) {
                    error(f + ": 중복 선택지 ID");
                }
                if (choice.check() != null && choice.failure() == null) {
                    error(f + ": check가 있으면 failure가 필요합니다");
                }
                if (choice.check() == null && choice.failure() != null) {
                    error(f + ": check가 없으면 failure는 쓰이지 않습니다");
                }
                checkOutcome(choice.success().effects(), choice.success().nextEvents(), f + ".success.", decreeTargetAllowed);
                if (choice.failure() != null) {
                    checkOutcome(choice.failure().effects(), choice.failure().nextEvents(), f + ".failure.", decreeTargetAllowed);
                }
            }
        }

        private void checkOutcome(List<EffectData> effects, List<NextEventData> nextEvents, String prefix,
                                  boolean decreeTargetAllowed) {
            for (int i = 0; i < effects.size(); i++) {
                checkEffect(effects.get(i), prefix + "effects[" + i + "]", decreeTargetAllowed);
            }
            for (int i = 0; i < nextEvents.size(); i++) {
                ref(eventIds, nextEvents.get(i).eventId(), prefix + "nextEvents[" + i + "].eventId");
            }
        }

        private void checkEffect(EffectData effect, String f, boolean decreeTargetAllowed) {
            EffectTarget target = effect.target();
            if ((target == EffectTarget.DECREE_TARGET || target == EffectTarget.DECREE_TARGET_FACTION)
                    && !decreeTargetAllowed) {
                error(f + ": " + target + "는 대상 인물을 지정하는 특별지시 카드에서만 쓸 수 있습니다");
            }
            if (!EFFECT_KEYS.get(target).contains(effect.key())) {
                error(f + ": " + target + "에 쓸 수 없는 key '" + effect.key() + "'");
                return;
            }
            switch (target) {
                case CHARACTER -> ref(characterIds, effect.targetId(), f + ".targetId");
                case FACTION -> ref(factionIds, effect.targetId(), f + ".targetId");
                case FACILITY -> ref(facilityIds, effect.targetId(), f + ".targetId");
                default -> {
                }
            }

            Class<? extends Enum<?>> enumType = switch (effect.key()) {
                case "POSITION" -> Position.class;
                case "STATUS" -> target == EffectTarget.FACILITY ? FacilityStatus.class : CharacterStatus.class;
                default -> null;
            };
            if (enumType == null) {
                if (!(effect.value() instanceof Number)) {
                    error(f + ".value: 숫자여야 합니다");
                }
            } else if (effect.operation() != EffectOperation.SET) {
                error(f + ": " + effect.key() + "는 SET만 쓸 수 있습니다");
            } else if (!(effect.value() instanceof String name) || !enumNames(enumType).contains(name)) {
                error(f + ".value: " + enumType.getSimpleName() + " 값이어야 합니다 " + enumNames(enumType));
            }
        }
    }
}
