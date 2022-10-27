package br.com.rafael.yaquisobra.domain.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MapeadorTipoVenda implements AttributeConverter<TipoVenda, String> {
	// Converter para coluna de banco de dados
	@Override
	public String convertToDatabaseColumn(TipoVenda x) {
		if (x != null) {
			return x.name();
		} else {
			return null;
		}
	}

	// Converter em atributo de entidade
	@Override
	public TipoVenda convertToEntityAttribute(String y) {
		if (y == null)
			return null;
		if ("RETIRADA".equals(y))
			return TipoVenda.RETIRADA;
		if ("ENTREGA".equals(y))
			return TipoVenda.ENTREGA;
		throw new IllegalStateException("Valor inválido: " + y);
	}
}