package com.example.algamoney.api01.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "permissao")
@Data
public class Permissao {
	
	@Id
	private Long codigo;
	private String descricao;

}
