package com.atradius.cibt.daoImpl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atradius.cibt.dao.UserDao;

import com.atradius.cibt.model.User;
import com.atradius.cibt.repository.UserRepository;


@Component
public class UserDaoImpl implements UserDao {

	@Autowired
	private UserRepository userRepository;

	private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

	@Override
	public Integer saveUser(User user) {
		LOGGER.info("Inside saveAllUsers Dao");
		return userRepository.save(user).getId();
	}

	@Override
	public List<User> getAllUser() {
		LOGGER.info("Inside getAllUsers Dao");
		return userRepository.findAll();
	}

	@Override
	public boolean isExist(Integer id) {
		LOGGER.info("Inside isExist Dao");
		return userRepository.existsById(id);
	}

	@Override
	public void deleteUser(Integer id) {
		LOGGER.info("Inside deleteUser Dao");
			userRepository.deleteById(id);
	}
	
}
