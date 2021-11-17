package com.example.myplan.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public String MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception){
//        ObjectError objectError = exception.getBindingResult().getAllErrors().get(0);
//        return objectError.getDefaultMessage();
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> argumentExceptionHandle(MethodArgumentNotValidException e){
        String message = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("."));
        ErrorResult errorResult = ErrorResult.builder()
                .message(message).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ErrorResult> TodoNotFoundException(TodoNotFoundException e){
        ErrorResult apiError = ErrorResult.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

}
