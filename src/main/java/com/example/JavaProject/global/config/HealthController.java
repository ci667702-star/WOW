package com.example.JavaProject.global.config;

import com.example.JavaProject.global.response.ResultDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** 프론트엔드-백엔드 연결 확인용 */
@RestController
public class HealthController {

    @GetMapping("/api/health")
    public ResultDto<String> health() {
        return ResultDto.success("UP");
    }
}
