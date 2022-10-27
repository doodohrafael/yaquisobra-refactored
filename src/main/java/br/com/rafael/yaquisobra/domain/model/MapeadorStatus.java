package br.com.rafael.yaquisobra.domain.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MapeadorStatus implements AttributeConverter<Status, String> {
	// Converter para coluna de banco de dados
	@Override
	public String convertToDatabaseColumn(Status x) {
		if (x != null) {
			return x.name();
		} else {
			return null;
		}
	}

	// Converter em atributo de entidade
	@Override
	public Status convertToEntityAttribute(String y) {
		if (y == null)
			return null;
		if ("CONECTADO".equals(y))
			return Status.CONECTADO;
		if ("DESCONECTADO".equals(y))
			return Status.DESCONECTADO;
		throw new IllegalStateException("Valor inválido: " + y);
	}
}