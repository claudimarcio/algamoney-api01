package com.example.algamoney.api01.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateService {
    
private JwtService jwtService;
 
public AuthenticateService(JwtService jwtService) {
	 this.jwtService = jwtService;
 }
	
	public String authenticate(Authentication authentication) {
		return jwtService.generateToken(authentication);
	}
}
