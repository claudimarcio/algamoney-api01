package com.example.algamoney.api01.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.algamoney.api01.model.Usuario;

public class UserAuthenticad implements UserDetails {


	private static final long serialVersionUID = 1L;
	
	
	private final Usuario user;
	
	private UserAuthenticad (Usuario user) {
		this.user = user;
	}
	
	
      
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getPermissoes().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));
		return authorities;
	}

	@Override
	public String getPassword() {
		
		return user.getSenha();
	}

	@Override
	public String getUsername() {
		
		return user.getEmail();
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


	
	

}
