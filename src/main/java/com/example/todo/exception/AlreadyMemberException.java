package com.example.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyMemberException extends RuntimeException {
	public AlreadyMemberException(String messageg) {
		super(messageg);
	}
}
