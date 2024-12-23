package com.example.rest.controller.advice;

import com.example.rest.exception.ErrorResponse;
import com.example.rest.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    //IllegalArgumentExceptionの例外ハンドラー
    //    @ExceptionHandler(IllegalArgumentException.class)
    //    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
    //        Map<String, Object> response = new HashMap<>();
    //        response.put("timestamp", LocalDateTime.now());
    //        response.put("status", HttpStatus.BAD_REQUEST.value());
    //        response.put("error", "Bad Request");
    //        response.put("message", ex.getMessage());
    //        return ResponseEntity.badRequest().body(response);
    //    }

    //IllegalArgumentExceptionの例外ハンドラー
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    //UserNotFoundExceptionの例外ハンドラー
    //    @ExceptionHandler(UserNotFoundException.class)
    //    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex) {
    //        Map<String, Object> response = new HashMap<>();
    //        response.put("timestamp", LocalDateTime.now());
    //        response.put("status", HttpStatus.NOT_FOUND.value());
    //        response.put("error", "Not Found");
    //        response.put("message", ex.getMessage());
    //        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    //    }

    //UserNotFoundExceptionの例外ハンドラー
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
            UserNotFoundException ex, HttpServletRequest request
    ) {
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "User Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
