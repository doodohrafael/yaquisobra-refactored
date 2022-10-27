package br.com.rafael.yaquisobra.domain.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public enum Funcao {

	ADMINISTRADOR,
	GERENTE,
	BALCONISTA;
	
	/*
	 * Especifica que a classe anotada é um conversor. autoApply = true: Aplicar o
	 * conversor a todos os atributos mapeados do especificado tipo de destino para
	 * todas as entidades na unidade de persistência.
	 */
	@Converter(autoApply = true)
	public static class Mapeador implements AttributeConverter<Funcao, String> {
		// Converter para coluna de banco de dados
		@Override
		public String convertToDatabaseColumn(Funcao x) {
			if (x != null) {
				return x.name();
			} else {
				return null;
			}
		}

		// Converter em atributo de entidade
		@Override
		public Funcao convertToEntityAttribute(String y) {
			if (y == null)
				return null;
			if ("ADMINISTRADOR".equals(y))
				return ADMINISTRADOR;
			if ("GERENTE".equals(y))
				return GERENTE;
			if ("BALCONISTA".equals(y))
				return BALCONISTA;
			throw new IllegalStateException("Valor inválido: " + y);
		}
	}
}
