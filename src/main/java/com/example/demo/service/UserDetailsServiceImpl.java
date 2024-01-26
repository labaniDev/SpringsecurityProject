package com.example.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepo userRepo;
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user=userRepo.findByUsername(username);
		if(user==null) 
			
			throw new UsernameNotFoundException("User not found");
		
		
		return UserDetailsImpl.build(user);
	}

}
