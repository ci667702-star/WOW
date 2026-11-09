package com.example.JavaProject.data.loader;

import lombok.Getter;

import java.util.List;

/** game-data를 읽거나 검증하다 실패했을 때 던진다. 앱 기동이 멈춘다. */
@Getter
public class GameDataException extends RuntimeException {

    private final List<String> errors;

    public GameDataException(String message, Throwable cause) {
        super(message, cause);
        this.errors = List.of(message);
    }

    public GameDataException(List<String> errors) {
        super("game-data 검증 실패 (" + errors.size() + "건)\n - " + String.join("\n - ", errors));
        this.errors = List.copyOf(errors);
    }
}
