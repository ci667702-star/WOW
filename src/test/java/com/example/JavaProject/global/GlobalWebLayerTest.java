package com.example.JavaProject.global;

import com.example.JavaProject.global.config.HealthController;
import com.example.JavaProject.global.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** 1단계 공통 설정 검증: 공통 응답 형식, 예외 처리, CORS */
@WebMvcTest(controllers = {HealthController.class, GlobalWebLayerTest.FailingController.class})
@Import(GlobalWebLayerTest.FailingController.class)
class GlobalWebLayerTest {

    @Autowired
    MockMvc mockMvc;

    @RestController
    static class FailingController {
        @GetMapping("/api/test/not-found")
        void notFound() {
            throw new ResourceNotFoundException("인물을 찾을 수 없습니다.");
        }

        @GetMapping("/api/test/crash")
        void crash() {
            throw new IllegalStateException("내부 정보");
        }
    }

    @Test
    void 헬스_API는_공통_성공_형식으로_응답한다() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").value("UP"));
    }

    @Test
    void BusinessException은_ErrorCode의_상태와_메시지로_변환된다() throws Exception {
        mockMvc.perform(get("/api/test/not-found"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value("RESOURCE_NOT_FOUND"))
                .andExpect(jsonPath("$.msg").value("인물을 찾을 수 없습니다."));
    }

    @Test
    void 예상치_못한_예외는_500으로_내부_메시지를_숨긴다() throws Exception {
        mockMvc.perform(get("/api/test/crash"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errorCode").value("INTERNAL_ERROR"))
                .andExpect(jsonPath("$.msg").value("서버 내부 오류가 발생했습니다."));
    }

    @Test
    void 없는_경로는_404_공통_형식으로_응답한다() throws Exception {
        mockMvc.perform(get("/api/no-such-path"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.code").value(404));
    }

    @Test
    void 프론트엔드_개발서버의_CORS_요청을_허용한다() throws Exception {
        mockMvc.perform(options("/api/health")
                        .header("Origin", "http://localhost:5173")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:5173"));
    }

    @Test
    void 허용되지_않은_출처의_CORS_요청은_거부한다() throws Exception {
        mockMvc.perform(options("/api/health")
                        .header("Origin", "http://evil.example")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isForbidden());
    }
}
