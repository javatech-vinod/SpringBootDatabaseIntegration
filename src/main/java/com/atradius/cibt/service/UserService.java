package com.atradius.cibt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atradius.cibt.model.User;
/**
 * 
 * @author 002A4W744
 *
 */
@Service
public interface UserService {

	public Integer saveUser(User user);
	public List<User> getAllUser();
	public boolean isExist(Integer id);
	public void deleteUser(Integer id);

}
