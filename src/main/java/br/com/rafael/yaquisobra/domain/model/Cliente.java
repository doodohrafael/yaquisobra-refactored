package br.com.rafael.yaquisobra.domain.model;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Cliente extends PessoaFisica {
	
	private static final long serialVersionUID = 1L;
	
	@Convert(converter = MapeadorStatus.class)
	private Status status;

	@Transient
	private Contato contato;
	
	@Transient
	private Endereco endereco;
}
