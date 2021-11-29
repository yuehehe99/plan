package com.example.myplan.exception;

public class RepeatRegisterException extends RuntimeException {
    public RepeatRegisterException(String message) {
        super(message);
    }
}
