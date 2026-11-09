package com.example.JavaProject.global.exception;

import com.example.JavaProject.global.response.ResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 모든 예외를 ResultDto 형식으로 변환한다.
 * Spring MVC 표준 예외(404, 405, 400 등)는 부모 클래스가 상태 코드를 정하고 handleExceptionInternal에서 형식만 맞춘다.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResultDto<Void>> handleBusiness(BusinessException e) {
        ErrorCode code = e.getErrorCode();
        log.warn("[{}] {}", code, e.getMessage());
        return ResponseEntity.status(code.getStatus())
                .body(ResultDto.error(code.getStatus().value(), code.name(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultDto<Void>> handleUnexpected(Exception e) {
        log.error("처리되지 않은 예외", e);
        ErrorCode code = ErrorCode.INTERNAL_ERROR;
        return ResponseEntity.status(code.getStatus())
                .body(ResultDto.error(code.getStatus().value(), code.name(), code.getDefaultMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        log.warn("요청 처리 실패 ({}): {}", statusCode.value(), ex.getMessage());
        HttpStatus status = HttpStatus.resolve(statusCode.value());
        String errorCode = status != null ? status.name() : "HTTP_" + statusCode.value();
        String msg = status != null ? status.getReasonPhrase() : ex.getMessage();
        return ResponseEntity.status(statusCode).headers(headers)
                .body(ResultDto.error(statusCode.value(), errorCode, msg));
    }
}
