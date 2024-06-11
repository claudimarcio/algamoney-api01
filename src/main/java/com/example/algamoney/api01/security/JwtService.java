package com.example.algamoney.api01.security;


import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
	
private JwtEncoder encoder;

public JwtService(JwtEncoder encoder) {
	this.encoder=encoder;
}

public String generateToken(Authentication authentication) {
    return generateToken(authentication, 1800L); // 30 min expiry
}

public String generateRefreshToken(Authentication authentication) {
    return generateToken(authentication, 3600L); // 1 hor expiry
}

private String generateToken(Authentication authentication, long expiry) {
    Instant now = Instant.now();
  
   
    
    String scopes = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));
        var claims = JwtClaimsSet.builder()
            .issuer("spring-security-algamoneyapi")
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiry))
            .subject(authentication.getName())
            .claim("scope", scopes )
            .build();
            return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
}
	
}


/**
public String generateToken(Authentication authentication ) {
	
	Instant now = Instant.now();
	long expiry = 3600L;
	
	String scopes = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(" "));
	
	var claims = JwtClaimsSet.builder()
			.issuer("spring-security-algamoneyapi")
			.issuedAt(now)
			.expiresAt(now.plusSeconds(expiry)  )
			.subject(authentication.getName())
			.claim("scope",scopes)
			.build();
	return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	
			
	
}
**/