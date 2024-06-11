package com.example.algamoney.api01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.api01.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
