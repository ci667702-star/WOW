package com.example.JavaProject.global.response;

import lombok.Builder;

@Builder
public record ApiResponse(
        boolean success,
        int code,
        String message
) {
}
