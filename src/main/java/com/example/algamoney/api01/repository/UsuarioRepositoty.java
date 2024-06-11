package com.example.algamoney.api01.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.api01.model.Usuario;

public interface UsuarioRepositoty extends JpaRepository<Usuario, Long> {
	
	public Optional<Usuario> findByEmail(String email);

}
