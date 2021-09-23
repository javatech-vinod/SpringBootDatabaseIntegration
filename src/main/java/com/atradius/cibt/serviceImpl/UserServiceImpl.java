package com.atradius.cibt.serviceImpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atradius.cibt.dao.UserDao;
import com.atradius.cibt.model.User;
import com.atradius.cibt.service.UserService;

import lombok.extern.log4j.Log4j2;



/**
 * 
 * @author 002A4W744
 *
 */
@Component
@Log4j2
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public Integer saveUser(User user) {
		log.info("inside saveUser() method");
		return userDao.saveUser(user);

	}

	@Override
	public List<User> getAllUser() {
		log.info("inside getAllUser() method");
		return userDao.getAllUser();
	}

	@Override
	public boolean isExist(Integer id) {
		log.info("inside updateUser() method");
		return userDao.isExist(id);

	}

	@Override
	public void deleteUser(Integer id) {
		log.info("inside deleteUser() method");
		userDao.deleteUser(id);
	}

}