package com.example.algamoney.api01.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoney.api01.model.Lancamento;
import com.example.algamoney.api01.model.Pessoa;
import com.example.algamoney.api01.repository.LancamentoRepository;
import com.example.algamoney.api01.repository.PessoaRepository;
import com.example.algamoney.api01.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired 
	private LancamentoRepository lancamentoRepository;
	

	
	
	public Lancamento salvar(Lancamento lancamento) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());
		if (pessoa.isEmpty() || pessoa.get().isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		return lancamentoRepository.save(lancamento);
	}

}
