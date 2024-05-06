package ru.sverdlov.app.models.util.error;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageReadable(HttpMessageNotReadableException ex){
        return ResponseEntity.badRequest().body("Ошибка парсинга JSON: " + ex.getMessage());
    }
}
