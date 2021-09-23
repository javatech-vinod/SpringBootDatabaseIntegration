package com.atradius.cibt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atradius.cibt.model.User;



public interface UserRepository extends JpaRepository<User, Integer>{

}
