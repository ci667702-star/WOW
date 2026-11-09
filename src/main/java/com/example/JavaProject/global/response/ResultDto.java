package com.example.JavaProject.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 모든 API의 공통 응답 형식.
 * 성공: { success: true, code: 200, msg: "OK", data: ... }
 * 실패: { success: false, code: 404, errorCode: "RESOURCE_NOT_FOUND", msg: "..." }
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResultDto<T>(boolean success, int code, String errorCode, String msg, T data) {

    public static <T> ResultDto<T> success(T data) {
        return new ResultDto<>(true, 200, null, "OK", data);
    }

    public static ResultDto<Void> error(int code, String errorCode, String msg) {
        return new ResultDto<>(false, code, errorCode, msg, null);
    }
}
