package com.example.demo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;
@Service
public class UserService {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	UserRepo userRepo;
	@Autowired
	PasswordEncoder encoder;
private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;
	
	public Boolean existsByUsername(String username) {
		return userRepo.existsByUsername(username);
	}
	
	public void saveUser(UserDTO userDTO) {
		User user=modelMapper.map(userDTO, User.class);
		user.setPassword(encoder.encode(user.getPassword()));
		userRepo.save(user);
	}
	
	
	


}
