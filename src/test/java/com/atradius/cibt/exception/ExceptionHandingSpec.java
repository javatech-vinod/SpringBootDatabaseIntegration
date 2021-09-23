package com.atradius.cibt.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

@ExtendWith(MockitoExtension.class)
public class ExceptionHandingSpec {
	
	@InjectMocks
	GlobalExceptionHandler globalExceptionHandler;
	
	@Mock
	WebRequest request;
	
	@Test
	@DisplayName("Should handle specific exception")
	public void exceptionHandlingTestCase() {
		ResourceNotFoundException exception=new ResourceNotFoundException("Unable to find user");
		when(request.getDescription(false)).thenReturn("Unable to handle exception");
		ResponseEntity<ErrorDetails> resourceNotFoundException=globalExceptionHandler.resourceNotFoundHandling(exception, request);
		assertThat(resourceNotFoundException.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Should handle globle level exception")
	public void globleExceptionTestCase() {
		
		ResourceNotFoundException exception=new ResourceNotFoundException("Unable to find user");
		when(request.getDescription(false)).thenReturn("handling globle exception");
		ResponseEntity<ErrorDetails>expectedOutput=globalExceptionHandler.globalExceptionHandling(exception, request);
		assertThat(expectedOutput.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	@DisplayName("Should return error details")
	public void errorDetails() {
		String hi="hi";
		ErrorDetails error=new ErrorDetails();
		error.setTimestamp(new Date());
		error.setMessage(hi);
		error.setDetails("hello");
		System.out.println(error.toString());
		assertThat(error.toString()).isNotNull();
		assertThat(error.getDetails()).isEqualTo(error.getDetails());
		assertThat(error.getMessage()).isEqualTo(hi);
		assertThat(error.getTimestamp()).isNotNull();
	}
}
