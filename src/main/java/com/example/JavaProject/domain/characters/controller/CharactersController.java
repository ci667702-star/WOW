package com.example.JavaProject.domain.characters.controller;

import com.example.JavaProject.global.exception.BusinessException;
import com.example.JavaProject.global.exception.ErrorCode;
import com.example.JavaProject.global.response.ResultDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CharactersController {

    // TODO(2단계): 인물 조회 API 구현. 그전까지는 501을 반환한다.
    @GetMapping("/api/v1/characters/{id}")
    public ResponseEntity<ResultDto<Void>> characters(@PathVariable Long id) {
        throw new BusinessException(ErrorCode.NOT_IMPLEMENTED, "인물 조회 API는 2단계에서 구현됩니다.");
    }
}
