package com.example.algamoney.api01.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "pessoa")
@Data
public class Pessoa {	

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long codigo;

		@NotNull
		private String nome;

		@Embedded
		private Endereco endereco;

		@NotNull
		private Boolean ativo;
		
		
		@JsonIgnore
		@Transient
		public boolean isInativo() {
			return !this.ativo;
		}


}
