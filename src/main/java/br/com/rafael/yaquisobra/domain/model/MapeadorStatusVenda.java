package br.com.rafael.yaquisobra.domain.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MapeadorStatusVenda implements AttributeConverter<StatusVenda, String> {
	// Converter para coluna de banco de dados
	@Override
	public String convertToDatabaseColumn(StatusVenda x) {
		if (x != null) {
			return x.name();
		} else {
			return null;
		}
	}

	// Converter em atributo de entidade
	@Override
	public StatusVenda convertToEntityAttribute(String y) {
		if (y == null)
			return null;
		if ("CRIADO".equals(y))
			return StatusVenda.CRIADO;
		if ("CONFIRMADO".equals(y))
			return StatusVenda.CONFIRMADO;
		if ("ENTREGUE".equals(y))
			return StatusVenda.ENTREGUE;
		if ("CANCELADO".equals(y))
			return StatusVenda.CANCELADO;
		throw new IllegalStateException("Valor inválido: " + y);
	}
}