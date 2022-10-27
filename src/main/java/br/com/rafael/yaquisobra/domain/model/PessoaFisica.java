package br.com.rafael.yaquisobra.domain.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class PessoaFisica extends Pessoa {
	
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter
	@Column(name = "cpf", length = 14, unique = true)
	private String cpf;
	
}
