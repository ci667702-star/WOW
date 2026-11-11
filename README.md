# 수문장(水門將) — WARDEN OF WATERS

동양풍 다크 판타지 카드형 전략·정치 시뮬레이션. 게임 규칙과 판정은 모두 Java 백엔드에서 처리하고, React는 화면 표시와 입력만 맡는다.

## 게임 규칙

아래는 현재 정한 게임 진행 규칙이다. 카드·사건 기능은 아직 구현 중이므로, README의 규칙이 코드에 모두 반영된 상태는 아니다.

### 한 판의 진행

1. 게임을 시작하면 회사 지표, 인물, 파벌, 시설, 카드가 준비된다.
2. 게임은 하루씩 진행한다. **7일마다 해결해야 하는 카드가 하나 주어지고**, 카드를 받은 날부터 7일 안에 해결해야 한다.
3. 플레이어는 카드 조건에 맞는 사건을 골라 카드를 사용한다. 사건마다 사용할 수 있는 카드 종류, 등급, 대상 조건이 정해진다.
4. 사건이 카드를 정상적으로 받아들이면 카드는 깨져 소모된다. 이후 사건 판정은 별도로 진행하며, 판정 성공·실패에 따라 각각의 결과가 적용된다.
5. 기한이 끝날 때까지 카드를 해결하지 못하면 게임 실패로 처리한다. 실패 엔딩은 회사의 신뢰도와 조직 상태 등 현재 상황에 따라 정한다.

### 카드와 등급

- 카드에는 종류와 등급이 있다. 현재 카드 종류는 `WORK`(업무)와 `DECREE`(특별지시)다.
- 일반 등급은 `STONE`(석) → `BRONZE`(동) → `SILVER`(은) → `GOLD`(금) 순이다. `PRESIDENT` 카드는 별도 해금 조건을 가진 특별 카드다.
- 모든 사건이 모든 카드를 받을 수 있는 것은 아니다. 사건에 적힌 카드 종류·최소 등급·대상 조건을 모두 만족해야 해당 카드로 해결을 시도할 수 있다.
- 카드를 해결하면 해당 카드 인스턴스는 소모된다. 같은 종류와 등급의 카드가 여러 장 있으면 각 카드는 별도의 인스턴스로 취급한다.

### 고정 사건 5개와 카드 재사용 규칙

- 카드 해결에 사용할 주요 사건은 **항상 5개를 고정으로 둔다**. 사건별로 받을 수 있는 카드와 등급, 대상, 성공·실패 결과가 다르다.
- **같은 사건에서는 같은 카드 정의(`cardCode`)를 한 번만 깰 수 있다.** 예를 들어 `DECREE_APPEASE_SILVER`를 사건 A에서 이미 한 번 깼다면, 같은 사건 A에 같은 정의의 카드 사본을 다시 사용해도 두 번째 카드는 깨지지 않고 돌아온다.
- 두 번째 사용 자체는 가능하다. 다만 카드가 소모되지 않으므로 그 카드로 주간 해결 조건을 완료한 것으로 보지 않는다.
- 같은 카드 정의의 사본은 다른 사건에서 깰 수 있다. 즉, 제한은 카드 전체에 걸리는 것이 아니라 **사건 ID와 카드 `cardCode`의 조합**에 걸린다.

### 간단한 예시

은 등급 회유 카드가 주어졌다고 가정한다.

1. 플레이어가 회유 카드를 받을 수 있는 사건 A를 선택한다.
2. 조건과 판정을 통과하면 카드가 깨지고 사건 결과가 적용된다.
3. 나중에 같은 사건 A에 `DECREE_APPEASE_SILVER` 사본을 다시 사용하면 사건 행동은 할 수 있지만, 카드가 깨지지 않고 손에 돌아온다.
4. 사건 B가 해당 카드를 받을 수 있다면, 남아 있는 카드 사본으로 사건 B에서 해결을 시도할 수 있다.

이 구조는 매주 새 도전 카드를 받고 제한 시간 안에 해결하는 《술탄의 게임》의 진행 방식과, 한 사건에서 같은 카드 조합을 반복해 깨지 못하는 규칙을 참고한다. 수문장은 회사 운영과 수문 정치에 맞게 카드와 사건 내용을 사용한다. [《술탄의 게임》 Steam 소개](https://store.steampowered.com/app/3117820/Sultans_Game/) · [공식 위키: 카드 공략](https://sultansgame.wiki.gg/wiki/Guide%3A_Breaking_All_Sultan_Cards) · [공식 위키: 반복 사건의 카드 처리 예시](https://sultansgame.wiki.gg/wiki/Maggie)

## 구성

| 경로 | 내용 |
|---|---|
| `src/main/java/.../domain/*` | 도메인별 controller / dto / entity / repository / service |
| `src/main/java/.../global` | 공통 설정(CORS), 예외 처리, 공통 응답(`ResultDto`), 공용 Enum |
| `src/main/java/.../data` | game-data JSON 로더·검증기 |
| `src/main/resources/game-data` | 기본 콘텐츠 JSON (형식: `docs/game-data-format.md`) |
| `frontend/` | React + TypeScript + Vite + Tailwind CSS |
| `compose.yaml` | 개발용 PostgreSQL |

## 실행

백엔드 (H2 인메모리, 설치 없이 바로 실행):

```bash
./gradlew bootRun
```

백엔드 (PostgreSQL):

```bash
docker compose up -d
./gradlew bootRun --args='--spring.profiles.active=postgres'
```

프론트엔드 (http://localhost:5173):

```bash
cd frontend && npm install && npm run dev
```

테스트:

```bash
./gradlew test
```

## 프로필

| 프로필 | DB | 용도 |
|---|---|---|
| `local` (기본) | H2 인메모리 | 로컬 개발 |
| `postgres` | PostgreSQL (`compose.yaml`) | 실제 DB로 개발 |
| `test` | H2 인메모리 | `./gradlew test` 시 자동 적용 |

## API 공통 응답

```json
{ "success": true,  "code": 200, "msg": "OK", "data": { } }
{ "success": false, "code": 404, "errorCode": "RESOURCE_NOT_FOUND", "msg": "..." }
```

## 개발 단계

1. 기본 프로젝트 구축 ← 완료
2. 인물 데이터 시스템
3. 카드 시스템
4. 게임 진행 시스템
5. 인물 및 파벌 시스템
6. 사건 및 정치 시스템
7. 엔딩 및 UI 완성
