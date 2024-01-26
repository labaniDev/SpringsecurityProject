package com.example.demo.entity;

import lombok.Data;

@Data
public class UserInfoResponse {
	Integer userid;
	String username;
	String jwtToken;
	
	
	public UserInfoResponse(Integer userid,String username,String jwtToken) {
		this.userid=userid;
		this.username=username;
		this.jwtToken=jwtToken;
	}
}
