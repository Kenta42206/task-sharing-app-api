package com.example.todo.exception;

import org.springframework.web.bind.ServletRequestBindingException;

public class MethodArgumentNotValidException extends ServletRequestBindingException {
    public MethodArgumentNotValidException(String message) {
        super(message);
    } 
}
