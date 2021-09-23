package com.atradius.cibt.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.atradius.cibt.daoImpl.UserDaoImpl;
import com.atradius.cibt.model.User;
import com.atradius.cibt.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserDaoSpec {
	
	@InjectMocks
	private UserDaoImpl userDaoImpl;
	
	@Mock
	private UserRepository userRepository;
	
	@Test
	@DisplayName("Should save user data")
	public void saveUserDaoTest() {
		User user =new User();
		user.setId(1);
		user.setFirstName("Vinod");
		user.setLastName("Tumsare");
		user.setEmail("vt@gmail.com");
		when(userRepository.save(user)).thenReturn(user);
		Integer id=userDaoImpl.saveUser(user);
		assertThat(user.getId()).isEqualTo(id);
	}
	
	@Test
	@DisplayName("Should get user data")
	public void getUserdetailsTest() {
		List<User> userList=new ArrayList<User>();
		User user =new User();
		user.setId(1);
		user.setFirstName("Vinod");
		user.setLastName("Tumsare");
		user.setEmail("vt@gmail.com");
		userList.add(user);
		when(userRepository.findAll()).thenReturn(userList);
		List<User>expectedResult=userDaoImpl.getAllUser();
		//assertThat(userList).isEqualTo(expectedResult);
	    assertEquals(1, userList.size());
	    assertEquals(user.getId(), expectedResult.get(0).getId());
		
	}

	@Test
	@DisplayName("Should update user data")
	public void updateUserDetailsTest() {
		when(userRepository.existsById(1)).thenReturn(true);
		boolean expectedResult=userDaoImpl.isExist(1);
		assertThat(expectedResult).isTrue();
		
	}
	
	@Test
	@DisplayName("Should delete user data")
	public void deleteUserDetailsTest() {
		doNothing().when(userRepository).deleteById(1);
		userDaoImpl.deleteUser(1);
		verify(userRepository, times(1)).deleteById(1);
	}
}
