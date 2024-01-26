package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepo extends CrudRepository<User,Integer>,JpaRepository<User,Integer> {
User findByUsername(String username);
	
	Boolean existsByUsername(String username);
}
