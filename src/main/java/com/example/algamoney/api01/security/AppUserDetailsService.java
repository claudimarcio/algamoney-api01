package com.example.algamoney.api01.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api01.model.Usuario;
import com.example.algamoney.api01.repository.UsuarioRepositoty;

@Service
public class AppUserDetailsService implements UserDetailsService{
	

	 @Autowired
	  UsuarioRepositoty usuarioRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {		
	
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
		Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));

		//System.out.println("Detalhes do usuário:");
	   // System.out.println("Nome: " + usuario.getNome());
	   // System.out.println("Email: " + usuario.getEmail());
	    //System.out.println("Permissões:");
	   // usuario.getPermissoes().forEach(p -> System.out.println(p.getDescricao()));
		
		
		
		return new User(email, usuario.getSenha(),getPermissoes(usuario));
	}
	
	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
	    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
	    usuario.getPermissoes().forEach(p -> {
	        if (p.getDescricao().startsWith("ROLE_")) { // Verifica se a descrição começa com ROLE_
	            authorities.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase()));
	        } else {
	            // Se não começar com ROLE_, você pode adicionar o prefixo ROLE_ manualmente
	            authorities.add(new SimpleGrantedAuthority("ROLE_" + p.getDescricao().toUpperCase()));
	        }
	    });
	    return authorities;
	}

	
	
	
	
	/**
	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		usuario.getPermissoes().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));
		return authorities;
	}
**/
}
