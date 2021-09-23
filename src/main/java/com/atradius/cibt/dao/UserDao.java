package com.atradius.cibt.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.atradius.cibt.model.User;

@Repository
public interface UserDao {
	public Integer saveUser(User user);
	public List<User> getAllUser();
	public boolean isExist(Integer id);
	public void deleteUser(Integer id);
	
}
