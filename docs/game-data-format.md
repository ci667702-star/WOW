# game-data JSON 형식

`src/main/resources/game-data/` 아래의 기본 콘텐츠 형식. 모든 파일은 **최상위가 배열**이다.

- JSON에는 데이터와 효과 설정만 적는다. 판정 방식, 수치 범위 보정(0~100), 효과 실행은 Java가 맡는다.
- `id`는 파일 전체에서 고유하다. 선택지 `id`는 해당 카드·사건 안에서만 고유하면 된다.
- 다른 항목을 가리키는 값(`factionId`, `eventId`, `targetId` 등)은 실제로 존재하는 `id`여야 한다.
- 대문자 값은 모두 Java Enum과 1:1로 대응한다.

## 로딩과 검증

앱이 시작할 때 `GameDataLoader`가 모든 파일을 DTO(record)로 읽고, `GameDataValidator`가 검증한 뒤 `GameData` 빈으로 등록한다. 서비스는 `GameData`를 주입받아 ID로 조회한다.

- 읽기 단계: 정의되지 않은 필드, 잘못된 Enum 값, JSON 문법 오류는 그 자리에서 실패한다.
- 검증 1단계: 필수 필드 누락, 수치 범위 (DTO의 Bean Validation 어노테이션)
- 검증 2단계: 중복 ID, 존재하지 않는 ID 참조, target별 `key`, 효과 `value`, 카드 종류별 규칙

하나라도 실패하면 앱이 뜨지 않고, 오류를 모두 모아 `[파일 #순번 ID] 내용` 형식으로 출력한다.

```text
[endings/endings.json #0 END_A] conditions[0].targetId: 존재하지 않는 ID 'NO_SUCH_FACTION'
[facilities/facilities.json #0 FAC_BROKEN] initialDurability: 100 이하여야 합니다
```

| 파일 | 내용 |
|---|---|
| `characters/executives.json` · `managers.json` · `employees.json` | 인물 (임원 / 관리자·대리 / 직원) |
| `factions/factions.json` | 파벌 |
| `facilities/facilities.json` | 시설 |
| `cards/work-cards.json` | 일반 업무 카드 |
| `cards/decree-cards.json` | 특별지시 카드 (PRESIDENT 카드 포함) |
| `events/events.json` | 사건 |
| `endings/endings.json` | 엔딩 |

---

## 공통 객체

### 조건 (condition)

카드 발생 조건, 특별지시 사용 조건, PRESIDENT 해금 조건, 사건 발생 조건, 엔딩 조건에 쓴다. 배열 안의 조건은 **모두 만족(AND)** 해야 한다.

```json
{ "target": "FACTION", "targetId": "IMPERIAL", "key": "ATTITUDE", "operator": "GTE", "value": 50 }
```

| 필드 | 값 |
|---|---|
| `target` | `COMPANY` · `CHARACTER` · `FACTION` · `FACILITY` · `EVENT` · `REBELLION` · `GAME` |
| `targetId` | `CHARACTER` · `FACTION` · `FACILITY` · `EVENT`일 때 대상 `id` |
| `operator` | `GTE`(≥) · `LTE`(≤) · `GT`(>) · `LT`(<) · `EQ`(=) |
| `value` | 숫자 |

`target`별 `key`:

| target | key |
|---|---|
| `COMPANY` | `IMPERIAL_TRUST` · `STAFF_SUPPORT` · `ORGANIZATION_CONTROL` · `PUBLIC_REPUTATION` · `BUDGET` · `STAFF_FATIGUE` · `ORGANIZATION_DISCONTENT` |
| `CHARACTER` | `LOYALTY` · `INFLUENCE` · `AMBITION` · `DISCONTENT` · `FATIGUE` · `PLAYER_RELATION` · 능력치(`StatType` 12종) |
| `FACTION` | `INFLUENCE` · `ATTITUDE` |
| `FACILITY` | `DURABILITY` |
| `EVENT` | `OCCURRED` (발생했으면 1, 아니면 0) |
| `REBELLION` | `SUCCEEDED` (정변이 성공했으면 1) |
| `GAME` | `TURN` |

### 효과 (effect)

```json
{ "target": "DECREE_TARGET", "key": "LOYALTY", "operation": "ADD", "value": 25 }
```

| 필드 | 값 |
|---|---|
| `target` | `COMPANY` · `CHARACTER` · `FACTION` · `FACILITY` · `DECREE_TARGET`(특별지시 대상 인물) · `DECREE_TARGET_FACTION`(대상 인물의 소속 파벌) |
| `targetId` | `CHARACTER` · `FACTION` · `FACILITY`일 때 대상 `id` |
| `key` | 조건의 `key`와 같음. 추가로 인물은 `POSITION` · `STATUS`, 시설은 `STATUS` |
| `operation` | `ADD`(더하기, 음수면 감소) · `SET`(덮어쓰기) |
| `value` | `ADD`는 숫자. `SET`은 Enum 문자열 (`POSITION` → `Position`, 인물 `STATUS` → `CharacterStatus`, 시설 `STATUS` → `FacilityStatus`) |

### 능력치 판정 (check)

```json
{ "stat": "ADMINISTRATION", "difficulty": 50 }
```

`stat`은 `StatType`, `difficulty`는 0~100. 누구의 능력치로 어떻게 굴릴지는 Java 규칙이 정한다.

### 다음 사건 (nextEvent)

```json
{ "eventId": "EVT_PRESS_REPORT", "delayTurns": 1, "chance": 70 }
```

`delayTurns` 턴 뒤에 `chance`%(0~100) 확률로 예약된다. 예약된 사건은 발생 턴에 자신의 `conditions`를 다시 검사한다.

### 선택지 (choice)

업무 카드, 특별지시 카드, 사건이 같은 형식을 쓴다.

| 필드 | 필수 | 설명 |
|---|---|---|
| `id` | O | 선택지 ID |
| `text` | O | 화면에 표시할 문구 |
| `cost` | | 필요 자원. 현재는 `{ "budget": 숫자 }`만 사용 |
| `check` | | 능력치 판정. 없으면 항상 `success` |
| `success` | O | `{ "effects": [...], "nextEvents": [...] }` |
| `failure` | `check`가 있으면 O | `success`와 같은 형식 |

---

## 인물 — `characters/*.json`

| 필드 | 설명 |
|---|---|
| `id` | 인물 ID (`EXE_`, `MGR_`, `EMP_` 접두사) |
| `name` · `title` | 이름, 직책 |
| `position` | 직급 `Position`: `STAFF` · `ASSISTANT_MANAGER` · `MANAGER` · `DIRECTOR` · `PRESIDENT` |
| `grade` | 등급: `STONE` · `BRONZE` · `SILVER` · `GOLD` · `PRESIDENT`. 카드의 대상 등급 제한에 쓰인다 |
| `department` | 소속 부서 코드 |
| `factionId` | 초기 소속 파벌 (무소속이면 `null`) |
| `personality` | 성격: `AMBITIOUS`(실적 중시 야심가) · `BUREAUCRAT`(원칙주의 관료) · `ROYALIST`(황실 충성파) · `BENEVOLENT`(직원 복지 중시) · `OPPORTUNIST`(기회주의자) · `HIDDEN_DISSIDENT`(겉으로 충성, 속으로 반감) · `DANGEROUS_TALENT`(유능하지만 위험) |
| `background` · `ambition` · `expertise` | 배경, 야망(설명), 전문 분야 |
| `stats` | 초기 능력치. `StatType` 12종 전부, 각 0~100 |
| `initialState` | 초기 정치적 상태: `loyalty` · `influence` · `ambition` · `discontent` · `fatigue` (0~100), `playerRelation` (-100~100) |

`position`, `factionId`, `stats`, `initialState`는 게임 시작 시 DB(`characters`)로 복사된 뒤 게임 중에 바뀐다.

## 파벌 — `factions/factions.json`

| 필드 | 설명 |
|---|---|
| `id` | `IMPERIAL` · `REFORM` · `CONSERVATIVE` · `PRAGMATIC` · `AMBITIOUS` |
| `name` · `description` · `ideology` · `goal` | 이름, 설명, 정치적 성향, 목표 |
| `initialInfluence` | 초기 영향력 (0~100) |
| `initialAttitude` | 플레이어에 대한 초기 태도 (-100~100) |
| `relations` | 다른 파벌에 대한 초기 관계 `{ "파벌ID": -100~100 }` |

## 시설 — `facilities/facilities.json`

| 필드 | 설명 |
|---|---|
| `type` | `DAM` · `PURIFICATION_PLANT` · `RESERVOIR` |
| `initialDurability` | 초기 내구도 (0~100) |
| `initialStatus` | `OPERATIONAL` · `DAMAGED` · `SHUT_DOWN` |

## 업무 카드 — `cards/work-cards.json`

| 필드 | 설명 |
|---|---|
| `type` | `WORK` |
| `grade` | `STONE` · `BRONZE` · `SILVER` · `GOLD` |
| `conditions` | 덱에서 뽑힐 수 있는 조건 |
| `relatedCharacters` · `relatedFactions` · `relatedFacilities` | 관련 항목 ID 목록 |
| `choices` | 선택지 목록 |

## 특별지시 카드 — `cards/decree-cards.json`

업무 카드 필드에 아래가 추가된다.

| 필드 | 설명 |
|---|---|
| `type` | `DECREE` |
| `decreeType` | `PURGE`(척결) · `APPEASE`(회유) · `CONSCRIPT`(차출) · `SEIZE`(장악) |
| `grade` | 일반 등급 또는 `PRESIDENT` |
| `targetType` | `CHARACTER`(대상 인물 지정) · `NONE` |
| `conditions` | 사용 조건 |
| `unlockConditions` | PRESIDENT 카드의 해금 조건. 일반 카드는 `[]` |

카드 등급별로 어떤 등급의 인물을 대상으로 삼을 수 있는지는 JSON이 아니라 Java에 명시적으로 구현한다.

## 사건 — `events/events.json`

| 필드 | 설명 |
|---|---|
| `type` | `CHAIN`(연쇄) · `POLITICAL`(정치) · `NPC_ACTION`(NPC 행동) · `RANDOM`(무작위) · `REBELLION`(반란) |
| `conditions` | 발생 조건 (예약된 사건은 발생 턴에 검사) |
| `effects` | 발생 즉시 적용되는 효과 |
| `choices` | 선택지 (없으면 `[]`) |
| `nextEvents` | 발생 즉시 예약되는 다음 사건 |

## 엔딩 — `endings/endings.json`

| 필드 | 설명 |
|---|---|
| `type` | `VICTORY` · `DEFEAT` |
| `reason` | 종료 원인: `GOAL_ACHIEVED` · `DISMISSED` · `COUP` · `COLLAPSE` · `DECK_EXHAUSTED` |
| `priority` | 낮을수록 먼저 검사한다. 조건을 모두 만족하는 첫 엔딩이 채택된다 |
| `conditions` | 달성 조건. `[]`이면 항상 만족 (카드 소진 시의 기본 엔딩) |
