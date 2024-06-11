package com.example.algamoney.api01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.algamoney.api01.model.Lancamento;
import com.example.algamoney.api01.repository.lancamento.LancamentoRepositoryQuery;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>,LancamentoRepositoryQuery {

}
