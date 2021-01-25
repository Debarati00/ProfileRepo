package com.pack.Profileservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ExceptionResponseTest {

	ExceptionResponse exceptionResponse = new ExceptionResponse();

	@Test
	void testGetSetErrorMessage() {
		exceptionResponse.setErrorMessage("errorMessage");
		assertEquals("errorMessage", exceptionResponse.getErrorMessage());
	}

	@Test
	void testGetSetRequestedURI() {
		exceptionResponse.setRequestedURI("requestedURI");
		assertEquals("requestedURI", exceptionResponse.getRequestedURI());
	}

	@Test
	void testAllArgsConstructor() {
		ExceptionResponse exceptionResponse1 = new ExceptionResponse("errorMessage", "requestedURI");
		assertEquals("errorMessage", exceptionResponse1.getErrorMessage());
	}

}
