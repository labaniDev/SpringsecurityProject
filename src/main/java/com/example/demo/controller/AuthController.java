package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.MessageResponse;
import com.example.demo.entity.UserInfoResponse;
import com.example.demo.security.config.JwtUtils;
import com.example.demo.service.UserDetailsImpl;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserService userService;
	@Autowired
	JwtUtils jwtUtils;
	@PostMapping("/signup")
	public ResponseEntity<MessageResponse> saveUser(@RequestBody UserDTO userDTO) {
		if(userService.existsByUsername(userDTO.getUsername())) {
			 return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		userService.saveUser(userDTO);
		return ResponseEntity.ok(new MessageResponse("User Registered Successfully"));
	}
	
	@PostMapping("/signin")
	public ResponseEntity<UserInfoResponse> authenticateUser(@Valid@RequestBody UserDTO userDTO) {
		
		Authentication authentication = authenticationManager
		        .authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));	
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String jwt = jwtUtils.generateJwtCookie(userDetails);		
		return ResponseEntity.ok().body( new UserInfoResponse(userDetails.getUserid(),
                userDetails.getUsername(),jwt));
	}

}
