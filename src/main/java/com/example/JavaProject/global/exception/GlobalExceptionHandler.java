package com.example.JavaProject.global.exception;

import com.example.JavaProject.global.dto.ResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultDto> handleExceptions(Exception ex){
        logger.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        ResultDto resultDto = ResultDto.builder()
                .success(false)
                .msg("에러 발생: " + ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(resultDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<ResultDto> handleUnauthorizedAction(HttpClientErrorException.Unauthorized exception){
        logger.error("Unauthorized Action: {}", exception.getMessage());
        ResultDto resultDto = ResultDto.builder()
                .success(false)
                .msg(exception.getMessage())
                .code(HttpStatus.FORBIDDEN.value())
                .build();
        return new ResponseEntity<>(resultDto, HttpStatus.FORBIDDEN);
    }
}
