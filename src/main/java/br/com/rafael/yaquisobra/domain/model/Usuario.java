package br.com.rafael.yaquisobra.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Usuario extends PessoaFisica implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(length = 40)
	private String senha;
	
	@Convert(converter = Funcao.Mapeador.class)
	private Funcao funcao;
	
}
