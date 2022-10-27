package br.com.rafael.yaquisobra.domain.model;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class PessoaJuridica extends Pessoa {
	
	private static final long serialVersionUID = 1L;
	
	@Getter @Setter
	private String cnpj;
	
}
