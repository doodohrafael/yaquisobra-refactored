package br.com.rafael.yaquisobra.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Ingrediente {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String descricao;
	
	private String nome;
	
	private String marca;

	@Column(name = "preco_unitario")
	private BigDecimal precoUnitario;
	
	private MedidaIngrediente medida;
	
	@Column(name = "data_validade")
	private LocalDate dataValidade;
	
	private Integer quantidade;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Fornecedor fornecedor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Estoque estoque;
	
}
