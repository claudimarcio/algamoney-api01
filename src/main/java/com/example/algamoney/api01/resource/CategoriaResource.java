package com.example.algamoney.api01.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api01.event.RecursoCriadoEvent;
import com.example.algamoney.api01.model.Categoria;
import com.example.algamoney.api01.repository.CategoriaRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ApplicationEventPublisher publisher;
		
	@GetMapping 
    @PreAuthorize("hasAuthority('SCOPE_ROLE_PESQUISAR_CATEGORIA')")
	public List<Categoria> listar() {
	return categoriaRepository.findAll();
	}

	@PostMapping
	@PreAuthorize("hasAuthority('SCOPE_ROLE_REMOVER_LANCAMENTO')")
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}

	@GetMapping("/{codigo}")
	 @PreAuthorize("hasAuthority('SCOPE_ROLE_PESQUISAR_CATEGORIA')")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
		return this.categoriaRepository.findById(codigo).map(categoria -> ResponseEntity.ok(categoria))
				.orElse(ResponseEntity.notFound().build());
	}
	
	
	


}
