package br.com.rafael.yaquisobra.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Contato {
	
	@Column(length = 15)
	private String telefone;
	
	private String email;
	
}
