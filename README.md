# 수문장(水門將) — WARDEN OF WATERS

동양풍 다크 판타지 카드형 전략·정치 시뮬레이션. 게임 규칙과 판정은 모두 Java 백엔드에서 처리하고, React는 화면 표시와 입력만 맡는다.

## 구성

| 경로 | 내용 |
|---|---|
| `src/main/java/.../domain/*` | 도메인별 controller / dto / entity / repository / service |
| `src/main/java/.../global` | 공통 설정(CORS), 예외 처리, 공통 응답(`ResultDto`), 공용 Enum |
| `src/main/java/.../data` | game-data JSON 로더·검증기 |
| `src/main/resources/gamedata` | 기본 콘텐츠 JSON |
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
