package com.atradius.cibt.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import com.atradius.cibt.dao.UserDao;
import com.atradius.cibt.model.User;
import com.atradius.cibt.repository.UserRepository;
import com.atradius.cibt.serviceImpl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceSpec {
	
	@InjectMocks
	UserServiceImpl userServiceImpl;
	
	@Mock
	private UserDao userDao;
	
	@Test
	@DisplayName("Should save user")
	public void userServiceSaveUser() {
		Integer id=1;
		User user=new User();
		user.setId(1);
		user.setFirstName("Vinod");
		user.setEmail("tumsare@gmail.com");
		when(userDao.saveUser(user)).thenReturn(id);
		Integer result=userServiceImpl.saveUser(user);
		assertThat(user.getId()).isEqualTo(result);
		
	}
	
	@Test
	@DisplayName("Should return list of user")
	public void userServiceGetAllUser() {
		List<User>userList=new ArrayList<User>();
		User user=new User();
		userList.add(user);
		when(userDao.getAllUser()).thenReturn(userList);
		List<User>expectedResult=userServiceImpl.getAllUser();
		assertThat(userList).isEqualTo(expectedResult);
	}

	
	@Test
	@DisplayName("Should update existing user")
	public void userServiceUpdateUser() {
		User user=new User();
		user.setId(1);
		when(userDao.isExist(user.getId())).thenReturn(true);
		boolean expectedResult=userServiceImpl.isExist(1);
		assertThat(expectedResult).isTrue();
	}
	
	@Test
	@DisplayName("Should delete user")
	public void userServiceDeleteUser() {
		Integer id=1;
		doNothing().when(userDao).deleteUser(id);
		userServiceImpl.deleteUser(id);
		verify(userDao, times(1)).deleteUser(id);
	}
}
