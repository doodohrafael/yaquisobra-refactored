package br.com.rafael.yaquisobra.domain.model;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Estado {

	/*
	 * Você poderia fazer isso quando a responsabilidade de criar/atualizar a coluna
	 * referenciada não estivesse na entidade atual, mas em outra entidade.
	 * 
	 * You would do that when the responsibility of creating/updating the referenced
	 * column isn't in the current entity, but in another entity.
	 */

	private String nome;

}
