package com.example.algamoney.api01.security;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthemticationController {

	private final AuthenticateService  authenticateService;
	
	public AuthemticationController (AuthenticateService  authenticateService) {
		this.authenticateService = authenticateService;
	}
	
	@PostMapping("/authenticate")
	
	public String authenticate(Authentication authentication) {
		return authenticateService.authenticate(authentication);
	}
}
