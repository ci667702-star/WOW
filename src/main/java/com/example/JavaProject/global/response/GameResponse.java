package com.example.JavaProject.global.response;

import lombok.Builder;

@Builder
public record GameResponse(
        boolean success,
        int code,
        String message
) {
}
