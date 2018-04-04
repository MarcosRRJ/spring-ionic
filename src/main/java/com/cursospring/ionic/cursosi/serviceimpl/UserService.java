package com.cursospring.ionic.cursosi.serviceimpl;

import org.springframework.security.core.context.SecurityContextHolder;

import com.cursospring.ionic.cursosi.security.UserSS;

public class UserService {

	public static UserSS authenticated(){
		
		try {
			
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
		
	}
}

