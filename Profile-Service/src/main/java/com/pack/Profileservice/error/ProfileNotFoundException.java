package com.pack.Profileservice.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Profile Not Found")
public class ProfileNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ProfileNotFoundException() {
		super("Profile Not Found");
		
	}

}
