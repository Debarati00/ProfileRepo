package com.pack.Profileservice.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Profile List Empty")
public class ProfileListEmptyException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ProfileListEmptyException() {
		super("Profile List Empty");
	}
}
	