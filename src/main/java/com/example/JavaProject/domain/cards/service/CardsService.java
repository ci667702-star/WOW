package com.example.JavaProject.domain.cards.service;

import com.example.JavaProject.domain.cards.dto.CardActionRequest;
import com.example.JavaProject.domain.cards.dto.CardData;
import com.example.JavaProject.domain.cards.repository.CardsRepository;
import com.example.JavaProject.global.exception.BusinessException;
import com.example.JavaProject.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 카드와 관련된 게임 규칙을 처리하는 서비스다.
 * 카드의 현재 상태는 데이터베이스에서, 카드 이름·설명·선택지·효과 같은 고정 정의는 game-data JSON에서 가져온다.
 * 두 데이터의 연결과 유효성 검사를 이 계층에서 수행하며, HTTP 요청이나 응답 형식에는 의존하지 않는다.
 */
@Service
@RequiredArgsConstructor
public class CardsService {

    /** 게임 안의 카드 인스턴스와 그 상태를 조회·저장한다. JSON에 있는 카드 정의 자체를 저장하는 저장소는 아니다. */
    private final CardsRepository cardsRepository;

    /**
     * 현재 진행 중인 게임의 카드 목록을 화면 응답용 데이터로 준비한다.
     * 구현할 때는 현재 게임의 카드 인스턴스를 찾고, 각 인스턴스의 {@code cardCode}로 JSON 카드 정의를 조회한 뒤,
     * 화면에 필요한 카드 정보와 게임 중 상태를 함께 반환해야 한다. 게임 ID를 외부에서 받지 않는 정책도 유지한다.
     *
     * @return 현재 게임에서 조회할 수 있는 카드 목록
     */
    public List<CardData> getCards() {
        throw new BusinessException(ErrorCode.NOT_IMPLEMENTED, "카드 목록 조회는 아직 구현되지 않았습니다.");
    }

    /**
     * 카드 선택 요청을 검증하고 선택 결과를 게임 상태에 반영한다.
     * 구현할 때는 다음 순서로 처리한다.
     * <ol>
     *     <li>요청의 카드 식별자로 현재 게임에 속한 카드 인스턴스를 찾는다.</li>
     *     <li>카드가 잠금 상태가 아니며 현재 선택할 수 있는 상태인지 확인한다.</li>
     *     <li>카드의 JSON 정의에서 요청한 선택지를 찾고, 조건·비용·대상 입력을 검증한다.</li>
     *     <li>판정 결과에 맞는 효과와 후속 사건을 적용한다.</li>
     *     <li>선택 결과와 카드의 사용 상태를 저장한다.</li>
     * </ol>
     * 한 단계라도 유효하지 않으면 상태를 일부만 변경한 채 끝내지 않도록 전체 처리를 하나의 트랜잭션으로 묶어야 한다.
     *
     * @param request 플레이어가 선택한 카드와 그 카드 안의 선택지
     */
    public void chooseCard(CardActionRequest request) {
        throw new BusinessException(ErrorCode.NOT_IMPLEMENTED, "카드 선택 처리는 아직 구현되지 않았습니다.");
    }
}
