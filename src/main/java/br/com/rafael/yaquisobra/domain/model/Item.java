package br.com.rafael.yaquisobra.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Item {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer quantidade;

	@Column(name = "valor_parcial", precision = 7, scale = 2)
	private BigDecimal valorParcial;

	@ManyToOne(fetch = FetchType.LAZY)
	private Venda venda;

	@OneToOne(fetch = FetchType.LAZY)
	private Produto produto;

}