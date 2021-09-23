package com.atradius.cibt.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.atradius.cibt.client.UserDetailsAPIService;
import com.atradius.cibt.exception.ResourceNotFoundException;
import com.atradius.cibt.model.Message;
import com.atradius.cibt.model.User;
import com.atradius.cibt.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerSpec {

	@InjectMocks
	UserController userController;

	@Mock
	UserService userService;
	
	@Mock
	private UserDetailsAPIService userServiceDetailsAPIService;

	@Test
	@DisplayName("should save current user")
	public void userSave() {
		User inputUser = new User();
		inputUser.setEmail("Hi");
		ResponseEntity<Message> actualResponse = new ResponseEntity<Message>(new Message("SUCCESS", "-saved"),
				HttpStatus.OK);
		when(userService.saveUser(inputUser)).thenReturn(1);
		ResponseEntity<Message> expectedResponse = userController.saveUser(inputUser);
		assertThat(expectedResponse.getBody()).isNotNull();
		assertThat(expectedResponse.getBody().getType()).isEqualTo(actualResponse.getBody().getType());

	}

	@Test
	@DisplayName("Unable to save current user")
	public void userSaveThrowsException() {
		// String msg="Unable to save user";
		User inputUser = new User();
		inputUser.setEmail("Hi");
		IllegalArgumentException exception = new IllegalArgumentException();
		ResponseEntity<Message> actualResponse = new ResponseEntity<Message>(new Message("FAIL", "Unable to Save"),
				HttpStatus.OK);
		when(userService.saveUser(inputUser)).thenThrow(exception);
		ResponseEntity<Message> expectedResponse = userController.saveUser(inputUser);
		assertThat(expectedResponse.getBody()).isNotNull();
		assertThat(expectedResponse.getBody().getType()).isEqualTo(actualResponse.getBody().getType());
	}

	@Test
	@DisplayName("should return all users")
	public void shouldReturnAllUsers() {
		List<User> userList = new ArrayList<User>();  
		User user=new User();
		user.setId(1);
		userList.add(user);
		when(userService.getAllUser()).thenReturn(userList);
		ResponseEntity<List<User>> expectedResponse = userController.getAllUser();
		assertThat(expectedResponse.getBody()).isNotNull();
	}

	@Test

	@DisplayName("should throw resouce not found exception")
	public void GetAllUserThrowsException() { 
		// String msg = "Unable to get user";
		// ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException(msg);
		when(userService.getAllUser()).thenReturn(null);
		Assertions.assertThrows(ResourceNotFoundException.class, () -> userController.getAllUser());
	}
	
	@Test
	@DisplayName("should throw resouce not found exception if list is empty")
	public void getAllUserThrowsException() { 
		when(userService.getAllUser()).thenReturn(Collections.emptyList());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> userController.getAllUser());
	}

	@Test

	@DisplayName("should throw resource not found exception")
	public void shouldThrowExceptionIfRecordNotFound() {
		String msg = "Unable to get user";
		ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException(msg);
		when(userService.getAllUser()).thenThrow(resourceNotFoundException);
		Assertions.assertThrows(ResourceNotFoundException.class, () -> userController.getAllUser());
	}

	@Test
	@DisplayName("should update user")
	public void shouldUpdateUser() {
		User user = new User();
		user.setId(1);
		when(userService.isExist(user.getId())).thenReturn(true);
		when(userService.saveUser(user)).thenReturn(user.getId());
		ResponseEntity<Message> expectedOutput = userController.updateUser(user);
		assertThat(expectedOutput.getBody()).isNotNull();
		assertThat(expectedOutput.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	@DisplayName("should throw exception on update user if user not found")
	public void shouldThrowExceptionWhenUserNotFound() {
		User user = new User();
		user.setId(1);
		when(userService.isExist(user.getId())).thenReturn(false);
		Assertions.assertThrows(ResourceNotFoundException.class, () -> userController.updateUser(user));

	}

	@Test
	@DisplayName("should Delete user")
	public void shouldDeleteUser() {
		Integer id = 1;
		User user = new User();
		user.setId(1);
		when(userService.isExist(id)).thenReturn(true);
		doNothing().when(userService).deleteUser(user.getId());
		ResponseEntity<Message> expectedOutput = userController.deleteUser(id);
		assertThat(expectedOutput.getBody()).isNotNull();
		assertThat(expectedOutput.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	@DisplayName("should throw exception on delete user if user not found")
	public void shouldThrowExceptionDeleteUser() {
		User user = new User();
		user.setId(1);
		when(userService.isExist(user.getId())).thenReturn(false);
		Assertions.assertThrows(ResourceNotFoundException.class, () -> userController.deleteUser(1));

	}
	
	@Test
	@DisplayName("Should return service is working")
	public void getMicroinstanceTest() {
		
		String response="SUCCESS";
		when(userServiceDetailsAPIService.getMicro2Instance()).thenReturn(response);
		ResponseEntity<String> expectedResponse=userController.getMicro2Instance();
		assertThat(expectedResponse.getBody()).isNotNull();
		assertThat(expectedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	@DisplayName("Should return service is down")
	public void getFallbackThrowsException() {
		
		IllegalArgumentException exception=new IllegalArgumentException("Service is down");
		ResponseEntity<String>expectedResponse=userController.getMicro2InstanceFallback(exception);
		assertThat(expectedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		
	}
}
