package com.pack.Profileservice.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Profile Specifications Missing ")
public class ProfileSpecificationsMissingException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	public ProfileSpecificationsMissingException() {
		super("Profile Specifications Missing ");
	}

}
