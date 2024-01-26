package com.example.demo.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Service
public class UserDetailsImpl implements UserDetails{
private static final long serialVersionUID = 1L;
	
	private Integer userid;
	private String username;
	private Collection<? extends GrantedAuthority> authorities;
	@JsonIgnore
	private String password;
	 public UserDetailsImpl( Integer id,String username, String password) {
		 	this.userid=id;
		    this.username = username;
		    this.password = password;
	 }
	 public static UserDetailsImpl build(User user) {
		 return new UserDetailsImpl(user.getUserid(),user.getUsername(),user.getPassword());
	 }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() { 
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	public Integer getUserid() {
		return this.userid;
	}
	

}
